package com.example.chatapp.service;

import com.example.chatapp.dto.LoginRequest;
import com.example.chatapp.dto.SignupRequest;
import com.example.chatapp.dto.UserResponseDTO;
import com.example.chatapp.model.User;
import com.example.chatapp.repository.UserRepository;
import com.example.chatapp.security.JwtUtil;
import com.example.chatapp.security.CookieUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(UserRepository userRepository, JwtUtil jwtUtil, CookieUtil cookieUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.cookieUtil = cookieUtil;
    }

    public String signup(SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already in use.");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);
        return "User registered successfully!";
    }

    public String login(LoginRequest request, HttpServletResponse response) {
        Optional<User> userOptional;

        // ✅ Allow login with email or username
        if (request.getEmail() != null && !request.getEmail().isEmpty()) {
            userOptional = userRepository.findByEmail(request.getEmail());
        } else if (request.getUsername() != null && !request.getUsername().isEmpty()) {
            userOptional = userRepository.findByUsername(request.getUsername());
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email or Username is required.");
        }

        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials.");
        }

        User user = userOptional.get();

        // ✅ Validate password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials.");
        }

        // ✅ Generate JWT with email & username from DB
        String token = jwtUtil.generateToken(user.getEmail(), user.getUsername());
        cookieUtil.addTokenToCookie(response, token);

        return "Login successful!";
    }

    public String logout(HttpServletResponse response) {
        cookieUtil.clearCookie(response);
        return "Logout successful!";
    }

    public UserResponseDTO getUserFromCookie(HttpServletRequest request) {
        if (request.getCookies() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No authentication token found.");
        }

        // ✅ Extract JWT token from cookies safely
        String token = Arrays.stream(request.getCookies())
                .filter(cookie -> "jwtToken".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);

        if (token == null || !jwtUtil.validateToken(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or expired token.");
        }

        // ✅ Extract user details from JWT
        String email = jwtUtil.extractEmail(token);
        String username = jwtUtil.extractUsername(token);

        return new UserResponseDTO(username, email);
    }
}
