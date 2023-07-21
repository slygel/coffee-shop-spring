package com.project.coffeeshop.service;

import com.project.coffeeshop.entity.RefreshToken;
import com.project.coffeeshop.entity.User;
import com.project.coffeeshop.repo.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private long expireSeconds = 1000 * 60 * 60 * 24 * 30;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    public String createRefreshToken(User user){
        RefreshToken token = refreshTokenRepository.findByUserId(user.getId());
        if(token == null){
            token = new RefreshToken();
            token.setUser(user);
        }
        token.setToken(UUID.randomUUID().toString());
        token.setExpireDate(Date.from(Instant.now().plusSeconds(expireSeconds)));
        refreshTokenRepository.save(token);
        return token.getToken();
    }

    public boolean isRefreshExpired(RefreshToken token){
        return token.getExpireDate().before(new Date());
    }

    public RefreshToken getByUser(Long userId){
        return refreshTokenRepository.findByUserId(userId);
    }
}
