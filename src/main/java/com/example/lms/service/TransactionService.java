package com.example.lms.service;

import com.example.lms.entity.*;
import com.example.lms.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BookRepository bookRepository;

    public Transaction issueBook(Long memberId, Long bookId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (book.getAvailableCopies() <= 0) {
            throw new RuntimeException("Book not available");
        }

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        Transaction txn = new Transaction();
        txn.setMember(member);
        txn.setBook(book);
        txn.setBorrowDate(LocalDate.now());
        txn.setDueDate(LocalDate.now().plusDays(14));
        txn.setStatus(TransactionStatus.ISSUED);

        return transactionRepository.save(txn);
    }

    public Transaction returnBook(Long transactionId) {
        Transaction txn = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        if (txn.getStatus() == TransactionStatus.RETURNED) {
            throw new RuntimeException("Book already returned");
        }

        LocalDate today = LocalDate.now();
        txn.setReturnDate(today);
        txn.setStatus(TransactionStatus.RETURNED);

        long daysLate = DAYS.between(txn.getDueDate(), today);
        if (daysLate > 0) {
            txn.setFine((int) (daysLate * 10));
        } else {
            txn.setFine(0);
        }

        Book book = txn.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);

        return transactionRepository.save(txn);
    }
}
