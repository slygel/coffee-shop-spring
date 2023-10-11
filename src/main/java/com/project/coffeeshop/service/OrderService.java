package com.project.coffeeshop.service;

import com.project.coffeeshop.dto.OrderDto;
import com.project.coffeeshop.dto.TransactionDetails;
import com.project.coffeeshop.entity.*;
import com.project.coffeeshop.exception.CatchException;
import com.project.coffeeshop.model.ItemModel;
import com.project.coffeeshop.model.OrderModel;
import com.project.coffeeshop.model.ProductModel;
import com.project.coffeeshop.repo.*;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.*;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private DeliveryInfoRepository deliveryInfoRepository;

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private AuthService authService;

    private String KEY = "rzp_test_DM9skgmLpQV9kh";

    private String KEY_SECRET = "HQrsxE2azWhcB5PzpY2LAqja";

    private String CURRENCY = "VND";

    @Transactional
    public boolean createOrder(OrderDto orderDto){
        long amount = 0;
        Order order = new Order();

        order.setOrderDate(new Date(System.currentTimeMillis()));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String currentUserName = authentication.getName();
            User user = userRepository.getUserByUsername(currentUserName).get(0);
            order.setUser(user);

            if(orderDto.getCodeVoucher() != null){
                Voucher voucher = voucherRepository.getCodeVoucher(orderDto.getCodeVoucher());
                try{
                    if(voucher.getCode().equals(orderDto.getCodeVoucher())){
                        order.setVoucher(voucher);
                    }
                }catch (Exception e){
                    String message = "Code not match";
                    throw new CatchException(message);
                }
                amount -= voucher.getValue();
            }

            if(orderDto.getPaymentId() != null){
                Payment payment = paymentRepository.getById(orderDto.getPaymentId());
                try{
                    if(payment.getId().equals(orderDto.getPaymentId())){
                        order.setPayment(payment);
                    }
                }catch (Exception e){
                    String message = "Payment methods not match";
                    throw new CatchException(message);
                }
            }

            Order orderSave = orderRepository.save(order);

            ProductModel productModel = new ProductModel();
            // chuyen item model ve item
            for(ItemModel itemModel : orderDto.getItems()){
                Item item = new Item(itemModel);
                item.setOrder(orderSave);
                itemRepository.save(item);

                amount += itemModel.getPriceIn() * itemModel.getQuantity();

            }

            // Create shipment
            Shipment shipment = new Shipment();

            DeliveryInfo deliveryInfo = deliveryInfoRepository.getByDeliveryInfoId(orderDto.getDeliveryId());

            shipment.setOrder(orderSave);
            shipment.setDeliveryInfo(deliveryInfo);
            shipment.setIsCompleted("false");
            shipment.setShipperId("HN001");
            shipment.setShipperName("Nguyen Tai Tue");
            shipment.setShipperPhone("0383291503");
            shipmentRepository.save(shipment);


            Bill bill = new Bill();
            bill.setOrder(orderSave);
            bill.setAmount(amount);

            billRepository.save(bill);


            authService.updatePoint(Long.valueOf(String.valueOf(orderDto.getItems().size())));
            System.out.println(orderDto.getItems().size());
            return true;
        }
        return false;
    }

    public List<OrderModel> getAllOrders(){
        List<OrderModel> orderModels = new ArrayList<>();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String currentUserName = authentication.getName();
            User user = userRepository.getUserByUsername(currentUserName).get(0);
            List<Order> orders = orderRepository.findByUser(user);

            for (Order order : orders){
                OrderModel orderModel = new OrderModel(order);
                orderModels.add(orderModel);
            }
        }
        return orderModels;
    }

    public List<OrderModel> getOrdersByAdmin(){
        List<OrderModel> orderModels = new ArrayList<>();
        List<Order> orders = orderRepository.findAll();

        for (Order order : orders){
            orderModels.add(new OrderModel(order));
        }
        return orderModels;
    }

    // Tra lai tong
    public long getTotalAmount(Long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            List<Item> items = order.getItems();
            long totalAmount = 0;
            for (Item item : items) {
                totalAmount += item.getPriceIn() * item.getQuantity();
            }
            return totalAmount;
        }
        // Xử lý khi không tìm thấy đơn hàng
        return 0;
    }

    // Lấy ra đơn đã giao
    public List<OrderModel> getCompleteOrder(){
        List<OrderModel> orderModels = new ArrayList<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String username = authentication.getName();

            List<Order> orders = orderRepository.getCompleteOrder(username);
            Set<Order> uniqueOrders = new LinkedHashSet<>(orders);

            List<Order> sortOrder = new ArrayList<>(uniqueOrders);
            Collections.sort(sortOrder,Comparator.comparing(Order::getId));

            for (Order order : sortOrder){
                orderModels.add(new OrderModel(order));
            }
        }
        return orderModels;
    }

    // Lấy ra đơn đang giao
    public List<OrderModel> getIsGoingOrder(){
        List<OrderModel> orderModels = new ArrayList<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String username = authentication.getName();

            List<Order> orders = orderRepository.getIsGoingOrder(username);
            Set<Order> uniqueOrders = new LinkedHashSet<>(orders);

            List<Order> sortOrder = new ArrayList<>(uniqueOrders);
            Collections.sort(sortOrder,Comparator.comparing(Order::getId));

            for (Order order : sortOrder){
                orderModels.add(new OrderModel(order));
            }
        }
        return orderModels;
    }

    @Transactional
    public int completeOrder(Long orderId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String currentUserName = authentication.getName();
            User user = userRepository.getUserByUsername(currentUserName).get(0);

            Optional<Order> optionalOrder = orderRepository.findById(orderId);
            if(optionalOrder.isPresent()){
                Order completeOrder = optionalOrder.get();
                if(Objects.equals(completeOrder.getUser().getId(), user.getId())){
                    return orderRepository.completeOrder(orderId);
                }
            }
        }
        return 0;
    }

    public void deleteOrderById(Long id){
        try {
            orderRepository.deleteById(id);
        }catch (Exception e){
            String message = "Can not delete product with id "+ id;
            throw new CatchException(message);
        }
    }

    public TransactionDetails createTransaction(Double amount){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("amount",amount);
            jsonObject.put("currency",CURRENCY);

            RazorpayClient razorpayClient = new RazorpayClient(KEY,KEY_SECRET);

            com.razorpay.Order order = razorpayClient.orders.create(jsonObject);

            TransactionDetails transactionDetails = prepareTransactionDetails(order);

            return transactionDetails;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    private TransactionDetails prepareTransactionDetails(com.razorpay.Order order){
        Long orderId = order.get("id");
        String currency = order.get("currency");
        Long amount = order.get("amount");

        TransactionDetails transactionDetails = new TransactionDetails(orderId,currency,amount);
        return transactionDetails;
    }
}
