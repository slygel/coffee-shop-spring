package com.project.coffeeshop.repo;

import com.project.coffeeshop.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    @Query("SELECT c FROM Category c WHERE c.name = :name")
    Category getCategoryByName(@Param("name") String name);


    @Query("SELECT c FROM Category c JOIN c.products p JOIN p.items i JOIN i.order o WHERE o.id = :orderId")
    List<Category> getCategoryByOrderId(@Param("orderId") Long orderId);


}
