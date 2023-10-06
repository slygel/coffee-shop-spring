package com.project.coffeeshop.repo;

import com.project.coffeeshop.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Long> {

    Payment getById(Long paymentId);
}
