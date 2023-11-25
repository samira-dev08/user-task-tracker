package com.company.service;

import com.company.dto.request.LoginRequest;
import com.company.dto.request.SignupRequest;
import com.company.dto.response.MessageResponse;
import com.company.entity.User;
import com.company.enums.EmailStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;

import java.util.List;

public interface UserService {
   User registerUser(SignupRequest signupRequest);
   String login(LoginRequest request);

    ResponseEntity<?> confirmEmail(String confirmationToken);
    SimpleMailMessage createConfirmationTokenAndMailMessage(User user);
    String resetPassword(String token, String password);
    String createResetTokenAndSendMailMessage(String email);
    String createRefreshToken(String username);

    List<User> findByEmailStatus(EmailStatus emailStatus);
}
