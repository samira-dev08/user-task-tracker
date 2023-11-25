package com.company.schedule;

import com.company.entity.User;
import com.company.enums.EmailStatus;
import com.company.exception.UserNotFoundException;
import com.company.repository.UserRepository;
import com.company.mail.EmailService;
import com.company.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EmailScheduler {
    private final EmailService emailService;
    private final UserService userService;
    private final UserRepository userRepository;

    @Scheduled(fixedDelay = 10000, initialDelay = 1000)
    public void sendEmailByThread() {
        List<User> listUsers =userService.findByEmailStatus(EmailStatus.NEW);
        for (User user : listUsers) {
            SimpleMailMessage mailMessage = userService.createConfirmationTokenAndMailMessage(user);
            emailService.sendEmail(mailMessage);
            user.setEmailStatus(EmailStatus.SENT);
            userRepository.save(user);
        }
    }
}
