package com.example.lms.dto;

import com.example.lms.entity.Member;
import lombok.Data;

@Data
public class MemberDTO {
    private Long id;
    private String email;
    private String name;
    private String phone;
    private String studentId;
    private Long userId;
    private String username;


    public static MemberDTO from(Member member) {
        MemberDTO dto = new MemberDTO();
        dto.setId(member.getId());
        dto.setEmail(member.getEmail());
        dto.setName(member.getName());
        dto.setPhone(member.getPhone());
        dto.setStudentId(member.getStudentId());
        dto.setUserId(member.getUser().getId());
        dto.setUsername(member.getUser().getUsername());

        return dto;
    }
}
