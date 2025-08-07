package com.example.lms.controller;

import com.example.lms.dto.ChangePasswordDTO;
import com.example.lms.dto.CreateMemberDTO;
import com.example.lms.dto.MemberDTO;
import com.example.lms.entity.Member;
import com.example.lms.service.MemberSelfService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members/me")
@RequiredArgsConstructor
public class MemberSelfController {

    private final MemberSelfService selfService;


    // Add this to check if the member exists for the current user
    @GetMapping("/info")
    public ResponseEntity<Member> getMyMemberInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Member member = selfService.getMyMember(username);
        if (member == null) {
            return ResponseEntity.noContent().build(); // 204 No Content -> used to indicate no member yet
        }
        return ResponseEntity.ok(member);
    }


    // Create or update member for the currently authenticated user
    @PostMapping("/create")
    public ResponseEntity<MemberDTO> createOrUpdateMember(@RequestBody CreateMemberDTO dto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        MemberDTO member = selfService.createOrUpdateMember(username, dto);
        return ResponseEntity.ok(member);
    }
    // authenticated user can change the password
    @PatchMapping("/password")
    public ResponseEntity<Void> changePassword(@RequestBody ChangePasswordDTO dto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        selfService.changePassword(username, dto);
        return ResponseEntity.noContent().build();
    }
}
