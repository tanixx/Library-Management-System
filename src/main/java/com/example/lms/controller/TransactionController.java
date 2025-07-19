package com.example.lms.controller;

import com.example.lms.entity.Transaction;
import com.example.lms.service.TransactionService;
import com.example.lms.dto.IssueRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/issue")
    public ResponseEntity<Transaction> issueBook(@RequestBody IssueRequest request) {
        return ResponseEntity.ok(transactionService.issueBook(request.getMemberId(), request.getBookId()));
    }

    @PutMapping("/return/{transactionId}")
    public ResponseEntity<Transaction> returnBook(@PathVariable Long transactionId) {
        return ResponseEntity.ok(transactionService.returnBook(transactionId));
    }
}
