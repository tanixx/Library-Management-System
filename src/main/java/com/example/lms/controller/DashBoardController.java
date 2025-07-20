package com.example.lms.controller;


import com.example.lms.dto.BookDTO;
import com.example.lms.dto.BookRequestDTO;
import com.example.lms.entity.Book;
import com.example.lms.entity.BookRequest;
import com.example.lms.entity.RequestStatus;
import com.example.lms.entity.Transaction;
import com.example.lms.service.BookRequestService;
import com.example.lms.service.BookService;
import com.example.lms.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashBoardController {

    private final BookService bookService;
    @GetMapping("/books")
    public ResponseEntity<Page<BookDTO>> listBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sortBy,
            @RequestParam(defaultValue = "asc") String dir) {

        Sort sort = dir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(bookService.listBooks(pageable));
    }
    private final BookRequestService requestService;
    @GetMapping("/admin/bookRequest")
    public ResponseEntity<List<BookRequest>> viewPendingRequests() {
        List<BookRequest> pending = requestService.getRequestsByStatus(RequestStatus.PENDING);
        return ResponseEntity.ok(pending);
    }

    @GetMapping("/member/bookRequest")
    public ResponseEntity<List<BookRequest>> getMyRequests(Principal principal) {
        String username = principal.getName();
        return ResponseEntity.ok(requestService.getRequestsByUsername(username));
    }
    private final TransactionService transactionService;
    @GetMapping("/member/issuedBooks")
    public ResponseEntity<List<Transaction>> myTransactions(Principal principal) {
        String username = principal.getName();
        return ResponseEntity.ok(transactionService.getTransactionsByMember(username));
    }

}
