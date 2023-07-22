package com.project.coffeeshop.service;

import com.project.coffeeshop.entity.DeliveryInfo;
import com.project.coffeeshop.entity.User;
import com.project.coffeeshop.exception.CatchException;
import com.project.coffeeshop.model.DeliveryInfoModel;
import com.project.coffeeshop.repo.DeliveryInfoRepository;
import com.project.coffeeshop.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeliveryInfoService {

    @Autowired
    private DeliveryInfoRepository deliveryInfoRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public boolean addDeliveryInfo(DeliveryInfoModel deliveryInfoModel){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String currentUserName = authentication.getName();

            User user = userRepository.getUserByUsername(currentUserName).get(0);

            DeliveryInfo deliveryInfo = new DeliveryInfo(deliveryInfoModel);
            deliveryInfo.setUser(user);

            DeliveryInfo deliveryInfoSave = deliveryInfoRepository.save(deliveryInfo);
            return true;
        }
        return false;
    }

    public List<DeliveryInfoModel> getDeliveryInfoOfUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String currentUserName = authentication.getName();

            List<DeliveryInfoModel> deliveryInfoModels = new ArrayList<>();
            List<DeliveryInfo> deliveryInfos = deliveryInfoRepository.getDeliveryInfoByUsername(currentUserName);

            for (DeliveryInfo deliveryInfo : deliveryInfos){
                DeliveryInfoModel deliveryInfoModel = new DeliveryInfoModel(deliveryInfo);
                deliveryInfoModels.add(deliveryInfoModel);
            }

            return deliveryInfoModels;
        }
        return new ArrayList<>();
    }

    public void deleteDeliveryInfoOfUser(Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String currentUsername = authentication.getName();

            User user = userRepository.getUserByUsername(currentUsername).get(0);

            DeliveryInfo deliveryInfo = deliveryInfoRepository.getByDeliveryInfoId(id);

            try{
                if(deliveryInfo.getUser().equals(user)){
                    deliveryInfoRepository.deleteById(id);
                }
            }catch (Exception e){
                String message = "You are not authorized to delete this DeliveryInfo";
                throw new CatchException(message);
            }
        }
    }

    public boolean deleteDeliveryInfo(DeliveryInfoModel deliveryInfoModel){
        boolean result = false;
        int delete = deliveryInfoRepository.deleteDeliveryInfo(deliveryInfoModel.getId());
        if(delete != 0){
            result = true;
        }
        return result;
    }
}
