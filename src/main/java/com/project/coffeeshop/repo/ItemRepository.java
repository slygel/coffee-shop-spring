package com.project.coffeeshop.repo;

import com.project.coffeeshop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Long> {


//    @Modifying
//    @Query("SELECT i FROM Item i WHERE i.order.id = :orderId")
//    List<Item> getItemsByOrderId(@Param("orderId") Long orderId);

    @Transactional
    @Query("SELECT i FROM Item i JOIN i.order o JOIN o.user u WHERE u.username = :username")
    List<Item> getAllItemsOfUser(@Param("username") String username);
}
