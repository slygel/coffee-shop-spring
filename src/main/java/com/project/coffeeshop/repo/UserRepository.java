package com.project.coffeeshop.repo;

import com.project.coffeeshop.dto.UserDto;
import com.project.coffeeshop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.*;

public interface UserRepository extends JpaRepository<User,Long> {

    List<User> findByUsername(String username);

    Optional<User> findById(Long id);

    @Query("SELECT u FROM User u WHERE u.username = :username")
    List<User> getUserByUsername(@Param("username") String username);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    List<User> getUserByEmail(@Param("email") String email);

    @Modifying
    @Query(value = "UPDATE User u SET u.password = :password WHERE u.username = :username")
    int updateUserPassword(@Param("username") String username,
                           @Param("password") String password);

    @Modifying
    @Query("UPDATE User u SET u.point = :point WHERE u.username = :username")
    int updatePoint(@Param("username") String username ,
                    @Param("point") Long point);

    @Modifying
    @Query("UPDATE User u SET u.reward = :reward WHERE u.username = :username")
    int updateReward(@Param("username") String username,
                     @Param("reward") String reward);

}
