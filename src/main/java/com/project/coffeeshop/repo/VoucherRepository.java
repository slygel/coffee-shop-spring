package com.project.coffeeshop.repo;

import com.project.coffeeshop.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface VoucherRepository extends JpaRepository<Voucher , Long> {


    @Query("SELECT v FROM Voucher v WHERE v.code = :code")
    Voucher getCodeVoucher(@Param("code") String code);
}
