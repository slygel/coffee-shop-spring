package com.project.coffeeshop.repo;

import com.project.coffeeshop.entity.Bill;
import com.project.coffeeshop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BillRepository extends JpaRepository<Bill,Long> {

    @Transactional
    @Query("SELECT b FROM Bill b JOIN b.order o JOIN o.user u WHERE u = :user")
    List<Bill> findByUser(@Param("user") User user);

    @Transactional
    @Query(value = "SELECT b FROM Bill b, Order o, User u WHERE b.order.id = o.id AND o.shipment.isCompleted = 'false' AND o.user.username = :username")
    List<Bill> getAllBillOnGoing(@Param("username") String username);

    @Transactional
    @Query(value = "SELECT b FROM Bill b, Order o, User u WHERE b.order.id = o.id AND o.shipment.isCompleted = 'true' AND o.user.username = :username")
    List<Bill> getAllBillComplete(@Param("username") String username);

}
