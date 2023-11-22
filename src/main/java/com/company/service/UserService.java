package com.company.service;

import com.company.dto.request.LoginRequest;
import com.company.dto.request.SignupRequest;
import com.company.dto.response.MessageResponse;
import com.company.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;

public interface UserService {
    ResponseEntity<?> registerUser(SignupRequest signupRequest);
   String login(LoginRequest request);

    ResponseEntity<?> confirmEmail(String confirmationToken);
    SimpleMailMessage createConfirmationTokenAndMailMessage(User user);
    String createResetTokenAndSendMailMessage(String email);
    String resetPassword(String token, String password);


}
