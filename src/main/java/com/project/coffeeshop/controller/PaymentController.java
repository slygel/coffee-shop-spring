package com.project.coffeeshop.controller;

import com.project.coffeeshop.config.Config;
import com.project.coffeeshop.dto.PaymentRequestDto;
import com.project.coffeeshop.dto.TransactionStatusDto;
import com.project.coffeeshop.entity.Category;
import com.project.coffeeshop.entity.Order;
import com.project.coffeeshop.entity.User;
import com.project.coffeeshop.repo.CategoryRepository;
import com.project.coffeeshop.repo.OrderRepository;
import com.project.coffeeshop.repo.UserRepository;
import com.project.coffeeshop.service.BillService;
import com.project.coffeeshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@RequestMapping("/api/v1")
@RestController
public class PaymentController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private BillService billService;

    @GetMapping("/create_payment/{order_id}")
    public ResponseEntity<?> createPayment(@PathVariable("order_id") Long orderId, HttpServletResponse response) throws IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){

            String currentName = authentication.getName();
            User user = userRepository.getUserByUsername(currentName).get(0);

            try {
                Optional<Order> optionalOrder = orderRepository.findById(orderId);

                if (!optionalOrder.isPresent()){
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
                }

                long amount = orderService.getTotalAmount(orderId) * 100;

                Map<String, String> vnp_Params = new HashMap<>();
                vnp_Params.put("vnp_Version", Config.vnp_Version);
                vnp_Params.put("vnp_Command", Config.vnp_Command);
                vnp_Params.put("vnp_TmnCode", Config.vnp_TmnCode);
                vnp_Params.put("vnp_Amount", String.valueOf(amount)) ;// tiền trong bill
                vnp_Params.put("vnp_BankCode", "VNBANK");

                Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
                String vnp_CreateDate = formatter.format(cld.getTime());
                vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

                vnp_Params.put("vnp_CurrCode", "VND");
                vnp_Params.put("vnp_IpAddr", String.valueOf(user.getId()));// Mã user
                vnp_Params.put("vnp_Locale", "vn");
                vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + optionalOrder.get().getId());

                List<Category> categories = categoryRepository.getCategoryByOrderId(orderId);
                List<String> categoryNames = new ArrayList<>();

                for (Category category : categories){
                    String name = category.getName();

                    categoryNames.add(name);
                }
                for (String categoryName : categoryNames){
                    vnp_Params.put("vnp_OrderType",categoryName);// Tên category
                }

                vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl + "?vnp_Amount=" + String.valueOf(amount));

                vnp_Params.put("vnp_TxnRef", String.valueOf(optionalOrder.get().getId()));// Mã order
//                vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl);

                cld.add(Calendar.MINUTE, 15);
                String vnp_ExpireDate = formatter.format(cld.getTime());
                vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

                List fieldNames = new ArrayList(vnp_Params.keySet());
                Collections.sort(fieldNames);
                StringBuilder hashData = new StringBuilder();
                StringBuilder query = new StringBuilder();
                Iterator itr = fieldNames.iterator();
                while (itr.hasNext()) {
                    String fieldName = (String) itr.next();
                    String fieldValue = vnp_Params.get(fieldName);
                    if ((fieldValue != null) && (fieldValue.length() > 0)) {
                        //Build hash data
                        hashData.append(fieldName);
                        hashData.append('=');
                        hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                        //Build query
                        query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                        query.append('=');
                        query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                        if (itr.hasNext()) {
                            query.append('&');
                            hashData.append('&');
                        }
                    }
                }
                String queryUrl = query.toString();
                String vnp_SecureHash = Config.hmacSHA512(Config.secretKey,hashData.toString());
                queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
                String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;

                vnp_Params.put("vnp_SecureHash", vnp_SecureHash);

                PaymentRequestDto paymentRequestDto = new PaymentRequestDto();
                paymentRequestDto.setStatus("OK");
                paymentRequestDto.setMessage("Successfully");
                paymentRequestDto.setURL(paymentUrl);

                return ResponseEntity.status(HttpStatus.OK).body(paymentRequestDto);
            }catch (Exception e){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during payment creation.");
            }

        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated.");
        }
    }

    @GetMapping("/payment_info")
    public ResponseEntity<?> transaction(
            @RequestParam(value = "vnp_Amount", required = false) String amount,
            @RequestParam(value = "vnp_BankCode", required = false) String bankCode,
            @RequestParam(value = "vnp_OrderInfo", required = false) String orderInfo,
            @RequestParam(value = "vnp_ResponseCode", required = false) String responseCode
    ) {
        // Check if any required parameter is missing
        if (amount == null || bankCode == null || orderInfo == null || responseCode == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Required request parameters are missing");
        }

        TransactionStatusDto transactionStatusDto = new TransactionStatusDto();

        if (responseCode.equals("00")) {
            transactionStatusDto.setStatus("OK");
            transactionStatusDto.setMessage("Payment success");
            transactionStatusDto.setData("");
        } else {
            transactionStatusDto.setStatus("No");
            transactionStatusDto.setMessage("Payment failed");
            transactionStatusDto.setData("");
        }

        return ResponseEntity.status(HttpStatus.OK).body(transactionStatusDto);
    }


}
