package com.example.lms.controller;

import com.example.lms.dto.BookRequestDTO;
import com.example.lms.entity.Book;
import com.example.lms.entity.BookRequest;
import com.example.lms.entity.RequestStatus;
import com.example.lms.service.BookRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book-request")
@RequiredArgsConstructor
public class BookRequestController {

    private final BookRequestService requestService; // Service for handling book requests

    // Create a new book request by the logged-in user
    @PostMapping("/request")
    public ResponseEntity<BookRequest> requestBook(@RequestBody BookRequestDTO dto,
                                                   @AuthenticationPrincipal UserDetails userDetails) {
        // Call service to create new request for the current user
        BookRequest request = requestService.requestNewBook(userDetails.getUsername(), dto);
        return ResponseEntity.ok(request); // Return created book request
    }

    // Get all pending book requests (for admin review)
    @GetMapping("/pending")
    public ResponseEntity<List<BookRequest>> viewPendingRequests() {
        // Fetch requests with PENDING status
        List<BookRequest> pending = requestService.getRequestsByStatus(RequestStatus.PENDING);
        return ResponseEntity.ok(pending); // Return list of pending requests
    }

    // Reject a book request by ID
    @PutMapping("/{id}/reject")
    public ResponseEntity<String> rejectRequest(@PathVariable Long id) {
        requestService.rejectRequest(id); // Reject the request
        return ResponseEntity.ok("Book request rejected"); // Return success message
    }

    // Approve a book request and add it to the library
    @PostMapping("/{id}/approve")
    public ResponseEntity<Book> approveRequest(@PathVariable Long id) {
        Book book = requestService.approveRequest(id); // Approve and add book
        return ResponseEntity.ok(book); // Return the added book details
    }
}
