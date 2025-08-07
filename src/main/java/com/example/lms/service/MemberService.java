package com.example.lms.service;

import com.example.lms.dto.MemberDTO;
import com.example.lms.entity.Member;
import com.example.lms.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public List<MemberDTO> getAllMembers() {
        List<Member> members = memberRepository.findAll();
        return members.stream()
                .map(MemberDTO::from)
                .collect(Collectors.toList());
    }


}
