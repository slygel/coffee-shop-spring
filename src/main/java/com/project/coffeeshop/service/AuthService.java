package com.project.coffeeshop.service;

import com.project.coffeeshop.constant.Constant;
import com.project.coffeeshop.dto.*;
import com.project.coffeeshop.entity.RefreshToken;
import com.project.coffeeshop.entity.User;
import com.project.coffeeshop.model.UserModel;
import com.project.coffeeshop.repo.UserRepository;
import com.project.coffeeshop.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RefreshTokenService refreshTokenService;

    public ResponseDto authenticateUser(UserLoginDto userLoginDto){
        ResponseDto result = new ResponseDto();
        // Xác thực từ username và password
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(userLoginDto.getUsername(), userLoginDto.getPassword()));

        //Nếu không có exception thì thông tin hợp lệ
        // Set thông tin authentication vào Security Context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userRepository.getUserByUsername(userLoginDto.getUsername()).get(0);

        // Return jwt for user
        String jwt = jwtTokenProvider.generateToken((CustomUserDetail) authentication.getPrincipal());
        result.setStatus("Login successfully");
        result.setData(jwt);
        result.setRefreshToken(refreshTokenService.createRefreshToken(user));
        return result;
    }

    @Transactional
    public String register(UserRegisterDto userRegisterDto){
        List<User> userCheckUsername = userRepository.getUserByUsername(userRegisterDto.getUsername());
        if(!userCheckUsername.isEmpty()){
            return Constant.DUPLICATE_USERNAME;
        }else{
            List<User> userCheckEmail = userRepository.getUserByEmail(userRegisterDto.getEmail());
            if(!userCheckEmail.isEmpty()){
                return Constant.USED_EMAIL;
            }
            User user = new User(userRegisterDto);

            user.setUsername(userRegisterDto.getUsername());
            user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
            userRepository.save(user);

            return Constant.OK;
        }
    }

    @Transactional
    public String adminRegister(AdminRegisterDto adminRegisterDto){
        User user = new User(adminRegisterDto);

        user.setUsername(adminRegisterDto.getUsername());
        user.setPassword(passwordEncoder.encode(adminRegisterDto.getPassword()));

        userRepository.save(user);

        return Constant.OK;
    }

    @Transactional
    public String shipperRegister(ShipperRegisterDto shipperRegisterDto){
        User user = new User(shipperRegisterDto);

        user.setUsername(shipperRegisterDto.getUsername());
        user.setPassword(passwordEncoder.encode(shipperRegisterDto.getPassword()));

        userRepository.save(user);

        return Constant.OK;
    }

    @Transactional
    public String updatePassword(UserUpdatePasswordDto updatePassword) {
        List<User> users = userRepository.getUserByUsername(updatePassword.getUsername());

        if (users.isEmpty()) {
            return Constant.WRONG_USERNAME_PASSWORD;
        } else {
            User user = users.get(0);

            if (passwordEncoder.matches(updatePassword.getOldPassword(), user.getPassword())) {
                int status = userRepository.updateUserPassword(updatePassword.getUsername(), passwordEncoder.encode(updatePassword.getNewPassword()));

                if (status != 0) {
                    return Constant.OK;
                } else {
                    return Constant.OTHER_ERROR;
                }
            } else {
                return Constant.WRONG_USERNAME_PASSWORD;
            }
        }
    }

    public UserDto getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            User user = userRepository.getUserByUsername(currentUserName).get(0);
            UserDto userDto = new UserDto(new UserModel(user));

            return userDto;
        }
        return new UserDto();
    }

    @Transactional
    public boolean updatePoint(Long point){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String currentUserName = authentication.getName();
            User user = userRepository.getUserByUsername(currentUserName).get(0);

            Long updatePoint = user.getPoint() + point;

            int result = userRepository.updatePoint(currentUserName, updatePoint);
            if (result != 0){
                if(updatePoint <= 8 && updatePoint >= 0){
                    userRepository.updateReward(currentUserName,"New");
                } else if(updatePoint > 8 && updatePoint <= 16){
                    userRepository.updateReward(currentUserName, "Bronze");
                }else if(updatePoint > 16 && updatePoint <= 24){
                    userRepository.updateReward(currentUserName, "Silver");
                }else if(updatePoint > 24 && updatePoint <= 32){
                    userRepository.updateReward(currentUserName, "Gold");
                }else if(updatePoint > 32 ){
                    userRepository.updateReward(currentUserName, "Diamond");
                }
                return true;
            }
        }
        return false;
    }

    public ResponseDto refreshToken(RefreshRequest refreshRequest){
        ResponseDto responseDto = new ResponseDto();
        RefreshToken token = refreshTokenService.getByUser(refreshRequest.getUserId());
        User user = token.getUser();
        String jwtToken = jwtTokenProvider.generateJwtTokenByUserId(user.getId());

        responseDto.setStatus("Token successfully refresh");
        responseDto.setData(jwtToken);
        return responseDto;
    }
}
