package com.project.coffeeshop.repo;

import com.project.coffeeshop.entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ShipmentRepository extends JpaRepository<Shipment,Long> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE Shipment sh SET sh.isCompleted = 'true' WHERE sh.order.id = :orderId")
    int orderCompleted(@Param("orderId") Long orderId);

//    @Modifying
//    @Query("SELECT sh FROM Shipment sh , Order o , User u WHERE sh.order.id = o.id AND o.user.username = :username")
//    List<Shipment> getAllShipByUser(@Param("username") String username);

    @Query("SELECT sh FROM Shipment sh , Order o WHERE sh.order.id = o.id")
    List<Shipment> getAllShip();

//    @Modifying
//    @Query("SELECT sh FROM Shipment sh, Order o, User u WHERE sh.order.id = o.id AND o.shipment.isCompleted = 'true' AND o.user.username = :username")
//    List<Shipment> getOrderCompleted(@Param("username") String username);


    @Query("SELECT sh FROM Shipment sh , Order o WHERE sh.order.id = o.id AND o.shipment.isCompleted = 'true' ")
    List<Shipment> getAllOrderCompleted();

//    @Modifying
//    @Query("SELECT sh FROM Shipment sh, Order o, User u WHERE sh.order.id = o.id AND o.shipment.isCompleted = 'false' AND o.user.username = :username")
//    List<Shipment> getOrderIsGoing(@Param("username") String username);


    @Query("SELECT sh FROM Shipment sh , Order o WHERE sh.order.id = o.id AND o.shipment.isCompleted = 'false' ")
    List<Shipment> getAllOrderIsGoing();
}
