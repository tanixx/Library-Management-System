package com.example.lms.controller;

import com.example.lms.dto.*;
import com.example.lms.entity.Role;
import com.example.lms.entity.User;
import com.example.lms.repository.RoleRepository;
import com.example.lms.repository.UserRepository;
import com.example.lms.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    // Dependencies for authentication and user management
    private final AuthenticationManager authManager;
    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;
    private final RoleRepository roleRepo;

    /**
     * Register a new user (Admin only)
     * Default role assigned: ROLE_MEMBER
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest req) {
        // Print current authenticated user's roles
        System.out.println("Authenticated user: " + SecurityContextHolder.getContext().getAuthentication().getAuthorities());

        // Check if the username already exists
        if (userRepo.existsByUsername(req.username())) {
            return ResponseEntity.badRequest().build();
        }

        // Create new user and encode password
        User user = new User();
        user.setUsername(req.username());
        user.setPassword(encoder.encode(req.password()));

        // Assign default role: ROLE_MEMBER
        Role role = roleRepo.findByName("ROLE_MEMBER")
                .orElseThrow(() -> new RuntimeException("ROLE_MEMBER not found"));

        user.getRoles().add(role);
        userRepo.save(user);

        // Generate JWT token for the new user
        String token = jwtUtil.generateToken(user.getUsername());
        return ResponseEntity.ok(new AuthResponse(token));
    }

    // Authenticate user and return JWT token
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest req) {
        // Authenticate using username and password
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(req.username(), req.password());
        authManager.authenticate(authToken);

        // Generate JWT token on successful authentication
        String token = jwtUtil.generateToken(req.username());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
