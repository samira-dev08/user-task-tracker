package com.company.controller;

import com.company.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
        userService.createResetTokenAndSendMailMessage(email);
        return new ResponseEntity<>("successfully sent reset mail to: "+email, HttpStatus.OK);
    }

    @PutMapping("/reset-password")
    public String resetPassword(@RequestParam String token,
                                @RequestParam String password) {

        return userService.resetPassword(token, password);
    }

//    @PutMapping("/remember-me")
//    public String rememberMePassword(@RequestParam String token,
//                               @RequestParam String email,
//                                     @RequestParam String series) {
//
//        return userService.rememberMe(token,email,series);
//    }
}
