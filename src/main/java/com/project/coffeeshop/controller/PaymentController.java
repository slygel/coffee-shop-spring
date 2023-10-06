package com.project.coffeeshop.controller;

import com.project.coffeeshop.config.Config;
import com.project.coffeeshop.dto.PaymentRequestDto;
import com.project.coffeeshop.dto.TransactionStatusDto;
import com.project.coffeeshop.entity.Bill;
import com.project.coffeeshop.entity.User;
import com.project.coffeeshop.model.BillModel;
import com.project.coffeeshop.repo.UserRepository;
import com.project.coffeeshop.service.BillService;
import com.project.coffeeshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    private CategoryService categoryService;

    @Autowired
    private BillService billService;

    @GetMapping("/create_payment")
    public ResponseEntity<?> createPayment(HttpServletResponse response) throws IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){

            String currentName = authentication.getName();
            User user = userRepository.getUserByUsername(currentName).get(0);

            List<BillModel> billModels = billService.getBillOfUser();

            long amount = billModels.get(0).getAmount() * 100; // x  100

            String vnp_TxnRef = Config.getRandomNumber(8);
            String vnp_IpAddr = "1";

            Map<String, String> vnp_Params = new HashMap<>();
            vnp_Params.put("vnp_Version", Config.vnp_Version);
            vnp_Params.put("vnp_Command", Config.vnp_Command);
            vnp_Params.put("vnp_TmnCode", Config.vnp_TmnCode);
            vnp_Params.put("vnp_Amount", String.valueOf(amount));// tiền trong bill
            vnp_Params.put("vnp_CurrCode", "VND");
            vnp_Params.put("vnp_BankCode", "VNBANK");
            vnp_Params.put("vnp_Locale", "vn");
            vnp_Params.put("vnp_TxnRef", vnp_TxnRef);// Mã bill
            vnp_Params.put("vnp_IpAddr", String.valueOf(user.getId()));// Mã user
            vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
            vnp_Params.put("vnp_ReturnUrl",Config.vnp_ReturnUrl);
            vnp_Params.put("vnp_OrderType", "CloudFee"); // Tên category

            Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            String vnp_CreateDate = formatter.format(cld.getTime());
            vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

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
                String fieldValue = (String) vnp_Params.get(fieldName);
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

        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated.");
        }
    }

    @GetMapping("/payment_info")
    public ResponseEntity<?> transaction(
            @RequestParam(value = "vnp_Amount") String amount,
            @RequestParam(value = "vnp_BankCode") String bankCode,
            @RequestParam(value = "vnp_OrderInfo") String order,
            @RequestParam(value = "vnp_ResponseCode") String responseCode

    ){
        TransactionStatusDto transactionStatusDto = new TransactionStatusDto();
        if(responseCode.equals("00")){
            transactionStatusDto.setStatus("OK");
            transactionStatusDto.setMessage("Successfully");
            transactionStatusDto.setData("");
        }else{
            transactionStatusDto.setStatus("No");
            transactionStatusDto.setMessage("Failed");
            transactionStatusDto.setData("");
        }
        return ResponseEntity.status(HttpStatus.OK).body(transactionStatusDto);
    }
}
