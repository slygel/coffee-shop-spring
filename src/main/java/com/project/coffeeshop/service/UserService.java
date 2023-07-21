package com.project.coffeeshop.service;

import com.project.coffeeshop.entity.User;
import com.project.coffeeshop.exception.CatchException;
import com.project.coffeeshop.model.UserModel;
import com.project.coffeeshop.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserModel> getAllUsers(){
        List<User> users = userRepository.findAll();
        List<UserModel> userModels = new ArrayList<>();

        Set<User> uniqueUsers = new LinkedHashSet<>(users);
        List<User> sortUsers = new ArrayList<>(uniqueUsers);
        Collections.sort(sortUsers,Comparator.comparing(User::getId));

        for(User user : sortUsers){
            userModels.add(new UserModel(user));
        }
        return userModels;
    }

    public UserModel getUserById(Long id){
        try{
            User user = userRepository.getById(id);
            return new UserModel(user);
        }catch (Exception e){
            String message = "Not founded user with id " + id;
            throw new CatchException(message);
        }
    }

    public void deleteUserById(Long id) {
        try{
            userRepository.deleteById(id);
        }catch (Exception e){
            String message = "Can not delete user with id "+ id;
            throw new CatchException(message);
        }
    }

    public UserModel update(Long id , User oldUser){
        User user = userRepository.getById(id);

        user.setEmail(oldUser.getEmail());
        user.setDateOfBirth(oldUser.getDateOfBirth());
        user.setFullName(oldUser.getFullName());

        userRepository.save(user);

        return new UserModel(user);
    }

}
