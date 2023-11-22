package com.company.controller;

import com.company.dto.request.LoginRequest;
import com.company.dto.request.SignupRequest;
import com.company.dto.response.JwtResponse;
import com.company.entity.User;
import com.company.service.UserService;
import com.company.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        return userService.registerUser(signupRequest);
    }

    @PostMapping("/sign-in")
    public String login(@Valid @RequestBody LoginRequest loginRequest) {
        String token = userService.login(loginRequest);
        return token;
    }

    @PostMapping("/confirm-account")
    public ResponseEntity<?> confirmUserAccount(@RequestParam("token") String confirmationToken) {
        return userService.confirmEmail(confirmationToken);
    }
}
