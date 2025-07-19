package com.example.lms.mapper;

import com.example.lms.dto.MemberDTO;
import com.example.lms.entity.Member;

public class MemberMapper {

    public static MemberDTO toDTO(Member member) {
        MemberDTO dto = new MemberDTO();
        dto.setId(member.getId());
        dto.setName(member.getName());
        dto.setEmail(member.getEmail());
        dto.setPhone(member.getPhone());
        dto.setStudentId(member.getStudentId());
        if (member.getUser() != null) {
            dto.setName(member.getUser().getUsername());
        }
        return dto;
    }
}
