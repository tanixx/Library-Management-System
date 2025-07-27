package com.example.lms.controller;

import com.example.lms.entity.Role;
import com.example.lms.entity.User;
import com.example.lms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Map;
@RequiredArgsConstructor

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/api/user")
public class UserController {

    private final UserRepository userRepository;
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Principal principal) {

        System.out.println(principal.getName());
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<String> roleNames = user.getRoles().stream()
                .map(Role::getName)
                .toList();

        Map<String, Object> userInfo = Map.of(
                "username", user.getUsername(),
                "roles", roleNames
        );

        return ResponseEntity.ok(userInfo);
    }
}
