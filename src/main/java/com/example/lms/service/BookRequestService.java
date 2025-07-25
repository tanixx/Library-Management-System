package com.example.lms.service;

import com.example.lms.dto.BookRequestDTO;
import com.example.lms.entity.*;
import com.example.lms.repository.BookRepository;
import com.example.lms.repository.BookRequestRepository;
import com.example.lms.repository.MemberRepository;
import com.example.lms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookRequestService {
    private final MemberRepository memberRepository;
    private final UserRepository userRepo;
    private final BookRequestRepository bookRequestRepo;
    private final BookRepository bookRepo;

    public List<BookRequest> getRequestsByUsername(String username) {
        return bookRequestRepo.findByMember_User_Username(username);
    }

    public BookRequest requestNewBook(String username, BookRequestDTO dto) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Member member=memberRepository.findByUserUsername(username).orElseThrow(
                ()-> new RuntimeException(("Member not found")));
        // Check if the book already exists in the library
        boolean exists = bookRepo.existsByTitleIgnoreCaseAndAuthorIgnoreCase(dto.getTitle(), dto.getAuthor());

        if (exists) {
            throw new IllegalArgumentException("This book is already available in the library.");
        }

        // Optional: Also check if the user has already requested this book
        boolean alreadyRequested = bookRequestRepo.existsByTitleIgnoreCaseAndAuthorIgnoreCaseAndMember_User_UsernameAndStatus(
                dto.getTitle(), dto.getAuthor(), username, RequestStatus.PENDING);

        if (alreadyRequested) {
            throw new IllegalArgumentException("You have already requested this book and it's pending review.");
        }

        BookRequest req = new BookRequest();
        req.setMember(member);
        req.setTitle(dto.getTitle());
        req.setAuthor(dto.getAuthor());
        req.setCategory(dto.getCategory());
        req.setIsbn(dto.getIsbn());
        req.setRequestedDate(LocalDateTime.now());
        req.setStatus(RequestStatus.PENDING);

        return bookRequestRepo.save(req);
    }

    public List<BookRequest> getRequestsByStatus(RequestStatus requestStatus) {
        return bookRequestRepo.findByStatus(RequestStatus.PENDING);
    }

    public void rejectRequest(Long id) {
        BookRequest request= bookRequestRepo.findById(id).orElseThrow(()->
                new RuntimeException("Book request not found"));
        if(request.getStatus()!=RequestStatus.PENDING){
            throw new IllegalStateException("Request has already been processed");
        }
        request.setStatus(RequestStatus.REJECTED);
        bookRequestRepo.save(request);
    }
    public Book approveRequest(Long id){
        BookRequest request=bookRequestRepo.findById(id).orElseThrow(()->
                new RuntimeException(("Book request not found")));
        Book book =new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        book.setCategory(request.getCategory());
        book.setTotalCopies(1);
        book.setAvailableCopies(1);
        request.setStatus(RequestStatus.APPROVED);
        bookRequestRepo.save(request);
        return bookRepo.save(book);
    }
}
