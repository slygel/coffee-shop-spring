package com.project.coffeeshop.repo;

import com.project.coffeeshop.entity.DeliveryInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DeliveryInfoRepository extends JpaRepository<DeliveryInfo,Long> {
    @Query(value = "SELECT d FROM DeliveryInfo d WHERE d.user.username = :username AND d.isDefault = 'true'")
    List<DeliveryInfo> getDeliveryInfoByUsername(@Param("username") String username);

    @Transactional
    @Modifying
    @Query(value = "UPDATE DeliveryInfo d SET d.isDefault = 'false' "
            + "WHERE d.id = :id ")
    int deleteDeliveryInfo(@Param("id") Long id);

    @Query("SELECT d FROM DeliveryInfo d WHERE d.id = :id")
    DeliveryInfo getByDeliveryInfoId(@Param("id") Long id);
}
