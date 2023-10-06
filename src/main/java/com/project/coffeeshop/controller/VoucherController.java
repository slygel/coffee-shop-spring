package com.project.coffeeshop.controller;

import com.project.coffeeshop.entity.Voucher;
import com.project.coffeeshop.exception.CatchException;
import com.project.coffeeshop.exception.DeleteResponse;
import com.project.coffeeshop.model.VoucherModel;
import com.project.coffeeshop.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1")
@RestController
public class VoucherController {

    @Autowired
    private VoucherService voucherService;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/vouchers")
    public ResponseEntity<List<VoucherModel>> getAllVouchers(){
        List<VoucherModel> voucherModels = voucherService.getAllVouchers();
        return new ResponseEntity<>(voucherModels, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/voucher/{id}")
    public ResponseEntity<?> getVoucherById(@PathVariable("id") Long id){
        try{
            VoucherModel voucherModel = voucherService.getVoucherById(id);
            return new ResponseEntity<>(voucherModel,HttpStatus.OK);
        }catch (CatchException e){
            String message = e.getMessage();
            DeleteResponse deleteResponse = new DeleteResponse(message);
            return new ResponseEntity<>(deleteResponse,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/post/voucher")
    public void addVoucher(@RequestBody Voucher voucher){
        voucherService.addVoucher(voucher);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/voucher/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        try{
            voucherService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (CatchException e){
            String message = e.getMessage();
            DeleteResponse deleteResponse = new DeleteResponse(message);
            return new ResponseEntity<>(deleteResponse,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update/voucher/{id}")
    public void update(@PathVariable("id") Long id,
                       @RequestBody Voucher voucher){
        voucherService.update(id,voucher);
    }
}
