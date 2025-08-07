package com.example.lms.repository;

import com.example.lms.entity.BookRequest;
import com.example.lms.entity.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRequestRepository extends JpaRepository<BookRequest, Long> {
    List<BookRequest> findByMember_User_Username(String username);

    List<BookRequest> findByMemberId(Long memberId);


    List<BookRequest> findByStatus(RequestStatus status);
    boolean existsByTitleIgnoreCaseAndAuthorIgnoreCaseAndMember_User_UsernameAndStatus(
            String title, String author, String username, RequestStatus status);

}