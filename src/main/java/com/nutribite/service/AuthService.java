package com.nutribite.service;

import com.nutribite.model.User;
import com.nutribite.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthService { // <--- MUST HAVE 'public' KEYWORD

    @Autowired
    private UserRepository userRepository;

    // ✅ SIGNUP logic
    public String signup(String email, String password, String role) {
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            return "User already exists";
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(password); // Plain text for your current testing
        
        // Auto-generate username so database doesn't reject the save
        user.setUsername(email.split("@")[0]);

        // Force proper role formatting
        if (role != null && role.equalsIgnoreCase("Admin")) {
            user.setRole("Admin");
        } else {
            user.setRole("User");
        }

        userRepository.save(user);
        return "User created successfully";
    }

    // ✅ LOGIN logic
    public Object login(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            return "User not found";
        }

        User user = userOpt.get();
        if (!password.equals(user.getPassword())) {
            return "Invalid password";
        }

        // Hide password before sending the user object (with the role!) back to React
        user.setPassword(null); 
        return user;
    }
}