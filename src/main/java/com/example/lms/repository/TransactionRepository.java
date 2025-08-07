package com.example.lms.repository;

import com.example.lms.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Page<Transaction> findAll(Pageable pageable);

    @Query("SELECT t FROM Transaction t " +
            "WHERE CAST(t.book.id AS string) = :query " +
            "   OR CAST(t.member.id AS string) = :query")
    Page<Transaction> searchTransactions(@Param("query") String query, Pageable pageable);


    List<Transaction> findByMemberId(Long memberId);
}
