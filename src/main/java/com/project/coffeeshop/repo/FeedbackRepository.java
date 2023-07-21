package com.project.coffeeshop.repo;

import com.project.coffeeshop.entity.Feedback;
import com.project.coffeeshop.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback,Long> {

    List<Feedback> findByOrder(Order order);
}
