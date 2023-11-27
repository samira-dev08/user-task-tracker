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
        String resetToken=userService.createResetTokenAndSendMailMessage(email);
        return new ResponseEntity<>("successfully sent reset mail to: "+email+"\n reset token: "+resetToken,
                HttpStatus.OK);
    }

    @PostMapping( "/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String token,
                                @RequestParam String newPassword) {

        return new ResponseEntity<>(userService.resetPassword(token, newPassword),HttpStatus.OK);
    }
}
