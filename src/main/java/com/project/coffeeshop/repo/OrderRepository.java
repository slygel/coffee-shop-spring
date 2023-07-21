package com.project.coffeeshop.repo;

import com.project.coffeeshop.entity.Order;
import com.project.coffeeshop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findByUser(User user);

    @Modifying
    @Query(value = "UPDATE Order o SET o.shipment.isCompleted = 'true' WHERE o.id = :orderId")
    int completeOrder(@Param("orderId") Long orderId);

    @Modifying
    @Query("SELECT o FROM Order o , Shipment sh , User u WHERE sh.order.id = o.id AND o.shipment.isCompleted = 'true' AND o.user.username = :username")
    List<Order> getCompleteOrder(@Param("username") String username);

    @Modifying
    @Query("SELECT o FROM Order o , Shipment sh , User u WHERE sh.order.id = o.id AND o.shipment.isCompleted = 'false' AND o.user.username = :username")
    List<Order> getIsGoingOrder(@Param("username") String username);
}
