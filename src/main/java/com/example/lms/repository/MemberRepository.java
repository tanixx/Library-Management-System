package com.example.lms.repository;

import com.example.lms.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUserUsername(String username);
    Page<Member> findAll(Pageable pageable);
    Page<Member> findByNameContainingIgnoreCase(String query, Pageable pageable);
}
