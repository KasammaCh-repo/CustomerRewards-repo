package com.example.rewards.repository;

import com.example.rewards.entity.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, Long> {

    //List<Transactions> findByCustomerIdAndDateBetween(String customerId, Date startDate, Date endDate);

    @Query(value = "SELECT * FROM transactions WHERE customerid = :customerId AND sysdate BETWEEN :startDate AND " +
            ":endDate ", nativeQuery = true)
    List<Transactions> findByCustomerIdAndDateBetween(@Param("customerId") String customerId,
                                                      @Param("startDate") String startDate, @Param("endDate") String endDate);
}
