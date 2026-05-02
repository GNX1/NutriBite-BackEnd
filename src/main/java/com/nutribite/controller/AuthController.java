package com.nutribite.controller;

import com.nutribite.model.User;
import com.nutribite.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public Object signup(@RequestBody User user) {
        return authService.signup(user.getEmail(), user.getPassword(), user.getRole());
    }

    @PostMapping("/login")
    public Object login(@RequestBody User user) {
        return authService.login(user.getEmail(), user.getPassword());
    }
}