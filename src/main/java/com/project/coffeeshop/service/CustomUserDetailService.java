package com.project.coffeeshop.service;

import com.project.coffeeshop.dto.CustomUserDetail;
import com.project.coffeeshop.entity.User;
import com.project.coffeeshop.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        List<User> users = userRepository.getUserByUsername(username);
        User user;
        if(!users.isEmpty()){
            user = users.get(0);
        }else{
            throw new UsernameNotFoundException(username);
        }
        return new CustomUserDetail(user);
    }

    public UserDetails loadUserById(Long userId){
        User user = userRepository.getById(userId);
        return new CustomUserDetail(user);
    }
}
