package com.example.lms.controller;

import org.aspectj.lang.annotation.Around;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    @GetMapping("/updateTransactions")
    public ResponseEntity<String> greeting() {


        return ResponseEntity.ok("Hello, authenticated user!");
    }

    @GetMapping("/public")
    public ResponseEntity<String> publicGreeting() {
        return ResponseEntity.ok("Hello, public user!");
    }
}
