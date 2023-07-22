package com.project.coffeeshop.service;

import com.project.coffeeshop.entity.Order;
import com.project.coffeeshop.entity.User;
import com.project.coffeeshop.entity.Voucher;
import com.project.coffeeshop.exception.CatchException;
import com.project.coffeeshop.model.VoucherModel;
import com.project.coffeeshop.repo.UserRepository;
import com.project.coffeeshop.repo.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VoucherService {

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private UserRepository userRepository;

    public List<VoucherModel> getAllVouchers(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String username = authentication.getName();
            User user = userRepository.getUserByUsername(username).get(0);
            if(user.getPoint() >= 8 || user.getUsername().equals("admin")){
                List<Voucher> vouchers = voucherRepository.findAll();
                List<VoucherModel> voucherModels = new ArrayList<>();
                for(Voucher voucher : vouchers){
                    voucherModels.add(new VoucherModel(voucher));
                }
                return voucherModels;
            }
        }
        return null;
    }

    public VoucherModel getVoucherById(Long id){
        try{
            Voucher voucher = voucherRepository.getById(id);
            return new VoucherModel(voucher);
        }catch (Exception e){
            String message = "Not founded voucher with id "+ id;
            throw new CatchException(message);
        }
    }

    public void addVoucher(Voucher voucher){
        voucherRepository.save(voucher);
    }

    public void deleteById (Long id){
        try{
            voucherRepository.deleteById(id);
        }catch (Exception e){
            String message = "Can not delete voucher with id "+ id;
            throw new CatchException(message);
        }
    }

    public VoucherModel update(Long id , Voucher oldVoucher){
        Voucher voucher = voucherRepository.getById(id);

        voucher.setCode(oldVoucher.getCode());
        voucher.setDescription(oldVoucher.getDescription());
        voucher.setStartDate(oldVoucher.getStartDate());
        voucher.setExpiryDate(oldVoucher.getExpiryDate());
        voucher.setValue(oldVoucher.getValue());

        voucherRepository.save(voucher);

        return new VoucherModel(voucher);
    }
}
