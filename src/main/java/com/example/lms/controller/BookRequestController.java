package com.example.lms.controller;


import com.example.lms.dto.BookRequestDTO;
import com.example.lms.entity.Book;
import com.example.lms.entity.BookRequest;
import com.example.lms.entity.RequestStatus;
import com.example.lms.service.BookRequestService;
import jakarta.persistence.PostRemove;
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
    private final BookRequestService requestService;

    @PostMapping("/request")
    public ResponseEntity<BookRequest> requestBook(@RequestBody BookRequestDTO dto,
                                                   @AuthenticationPrincipal UserDetails userDetails) {
        BookRequest request = requestService.requestNewBook(userDetails.getUsername(), dto);
        return ResponseEntity.ok(request);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<BookRequest>> viewPendingRequests() {
        List<BookRequest> pending = requestService.getRequestsByStatus(RequestStatus.PENDING);
        return ResponseEntity.ok(pending);
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<String> rejectRequest(@PathVariable Long id) {
        requestService.rejectRequest(id);
        return ResponseEntity.ok("Book request rejected");
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<Book> approveRequest(@PathVariable Long id){
        Book book=requestService.approveRequest(id);
        return ResponseEntity.ok(book);
    }
}
