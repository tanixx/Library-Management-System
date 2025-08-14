package com.example.lms.service;

import com.example.lms.AppConfig;
import com.example.lms.entity.*;
import com.example.lms.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
        txn.setDueDate(LocalDate.now().plusDays( Long.parseLong(AppConfig.get("app.daysToReturn"))));
        txn.setStatus(TransactionStatus.ISSUED);

        return transactionRepository.save(txn);
    }
    @Autowired
    private EmailService emailService;
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
        int fineAmount=0;
        if (daysLate > 0) {
            fineAmount = (int) (daysLate * Long.parseLong(AppConfig.get("app.lateFine")));
            txn.setFine(fineAmount);

            // Send email only if this is the first day late
            if (daysLate == 1) {
                Member member = txn.getMember();
                if (member != null && member.getEmail() != null) {
                    String subject = "Book Return Overdue Notice";
                    String body = "Dear " + member.getName() + ",\n\n" +
                            "You have returned the book \"" + txn.getBook().getTitle() + "\" 1 day late.\n" +
                            "A fine of Rs. " + fineAmount + " has been applied to your account.\n\n" +
                            "Please ensure timely returns in the future.\n\n" +
                            "Regards,\nLibrary Management Team";

                    emailService.sendEmail(member.getEmail(), subject, body);
                }
            }
        } else {
            txn.setFine(0);
        }

        Book book = txn.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);

        return transactionRepository.save(txn);
    }


    @EventListener(ApplicationReadyEvent.class)
    public void checkOverdueTransactionsOnStartup() {
        checkOverdueTransactions();
    }

    public void checkOverdueTransactions() {
        List<Transaction> allIssued = transactionRepository.findByStatus(TransactionStatus.ISSUED);
        LocalDate today = LocalDate.now();

        for (Transaction txn : allIssued) {
            long daysLate = DAYS.between(txn.getDueDate(), today);

            if (daysLate > 0 ) {
                int fineAmount = (int) (daysLate * Long.parseLong(AppConfig.get("app.lateFine")));
                txn.setFine(fineAmount);

                txn.setStatus(TransactionStatus.OVERDUE); // mark as overdue
                transactionRepository.save(txn); // save changes
            }
        }
    }


    public List<Transaction> getTransactionsByMemberId(Long memberId) {
        return transactionRepository.findByMemberId(memberId);
    }
    public List<Transaction> getTransactionsByMember(String username) {
        Optional<Member> member=memberRepository.findByUserUsername(username);
        Long id=member.get().getId();
        return transactionRepository.findByMemberId(id);

    }
}
