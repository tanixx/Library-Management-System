package com.example.lms.controller;

import com.example.lms.dto.TransactionsDTO;
import com.example.lms.entity.Transaction;
import com.example.lms.repository.TransactionRepository;
import com.example.lms.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private TransactionRepository transactionRepository;

    @PostMapping("/issue")
    public ResponseEntity<Transaction> issueBook(@RequestBody TransactionsDTO request) {
        return ResponseEntity.ok(transactionService.issueBook(request.getMemberId(), request.getBookId()));
    }

        @GetMapping("/all")
        public Page<Transaction> getAllTransactions(
                @RequestParam(defaultValue = "0") int page,
                @RequestParam(defaultValue = "10") int size) {
            return transactionRepository.findAll(PageRequest.of(page, size, Sort.by("borrowDate").descending()));
        }

        @GetMapping("/search")
        public Page<Transaction> searchTransactions(
                @RequestParam String query,
                @RequestParam(defaultValue = "0") int page,
                @RequestParam(defaultValue = "10") int size) {
            return transactionRepository.searchTransactions(query, PageRequest.of(page, size, Sort.by("borrowDate").descending()));
        }
    @PutMapping("/return/{transactionId}")
    public ResponseEntity<Transaction> returnBook(@PathVariable Long transactionId) {
        return ResponseEntity.ok(transactionService.returnBook(transactionId));
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<Transaction>> getMemberTransactionHistory(@PathVariable Long memberId) {
        List<Transaction> transactions = transactionService.getTransactionsByMemberId(memberId);
        return ResponseEntity.ok(transactions);
    }
}
