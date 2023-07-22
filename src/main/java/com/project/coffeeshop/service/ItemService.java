package com.project.coffeeshop.service;

import com.project.coffeeshop.entity.Item;
import com.project.coffeeshop.model.ItemModel;
import com.project.coffeeshop.repo.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public List<ItemModel> getAllItemUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String currentUserName = authentication.getName();
            List<Item> items = itemRepository.getAllItemsOfUser(currentUserName);

            List<ItemModel> itemModels = new ArrayList<>();
            for(Item item : items){
                itemModels.add(new ItemModel(item));
            }

            return itemModels;
        }
        return new ArrayList<>();
    }
}
