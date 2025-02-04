package com.ws.ps.controller;

import com.ws.ps.entity.Users;
import com.ws.ps.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserController
 *
 * @author Eric at 2025-01-25_20:22
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/api/user/register")
    public ResponseEntity<String> registerUser(@RequestParam String username, @RequestParam String password, @RequestParam String email) {
        Users user = userService.registerUser(username, password, email);
        if (user != null) {
            return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Registration failed", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/api/user/login")
    public ResponseEntity<String> loginUser(@RequestParam String username, @RequestParam String password) {
        Users user = userService.loginUser(username, password);
        if (user != null) {
            return new ResponseEntity<>("Login successful", HttpStatus.OK);
        }
        return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
    }
}
