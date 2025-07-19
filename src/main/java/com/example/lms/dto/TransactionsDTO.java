package com.example.lms.dto;


import com.example.lms.entity.Book;
import com.example.lms.entity.Member;
import com.example.lms.entity.TransactionStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TransactionsDTO {
    private Long id;
    private Member member;
    private Book book;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private Integer fine;
    private TransactionStatus status;

}
