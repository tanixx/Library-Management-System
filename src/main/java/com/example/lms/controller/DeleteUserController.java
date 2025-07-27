package com.example.lms.controller;

import com.example.lms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/delete-user")
@RequiredArgsConstructor
public class DeleteUserController {

    private final UserRepository userRepository;

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        return userRepository.findById(id).map(user -> {
            user.getRoles().clear();
            userRepository.save(user);
            userRepository.delete(user);
            return ResponseEntity.ok("User deleted.");
        }).orElse(ResponseEntity.notFound().build());
    }

}
