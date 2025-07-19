package com.example.lms.service;

import com.example.lms.dto.ChangePasswordDTO;
import com.example.lms.dto.CreateMemberDTO;
import com.example.lms.dto.MemberDTO;
import com.example.lms.entity.Member;
import com.example.lms.entity.User;
import com.example.lms.repository.MemberRepository;
import com.example.lms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberSelfService {

    private final MemberRepository memberRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void changePassword(String username, ChangePasswordDTO dto) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        // Check if current password matches
        if (!passwordEncoder.matches(dto.getCurrentPassword(), user.getPassword())) {
            throw new RuntimeException("Current password is incorrect");
        }

        // Set new encoded password
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);
    }
    public MemberDTO createOrUpdateMember(String username, CreateMemberDTO dto) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        Optional<Member> existing = memberRepository.findByUserUsername(user.getUsername());

        Member member;
        if (existing.isPresent()) {
            // update
            member = existing.get();
        } else {
            // create
            member = new Member();
            member.setUser(user);
        }

        member.setEmail(dto.getEmail());
        member.setName(dto.getName());
        member.setPhone(dto.getPhone());
        member.setStudentId(dto.getStudentId());

        Member saved = memberRepository.save(member);
        return MemberDTO.from(saved);
    }

}
