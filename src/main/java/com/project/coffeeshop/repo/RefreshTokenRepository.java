package com.project.coffeeshop.repo;

import com.project.coffeeshop.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    @Query("SELECT r FROM RefreshToken r WHERE r.user.id = :user_id")
    RefreshToken findByUserId(@Param("user_id") Long userId);
}
