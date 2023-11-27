package com.company.service.impl;

import com.company.dto.request.LoginRequest;
import com.company.dto.request.SignupRequest;
import com.company.dto.response.MessageResponse;
import com.company.entity.ConfirmationToken;
import com.company.entity.PasswordResetToken;
import com.company.entity.User;
import com.company.enums.EmailStatus;
import com.company.exception.UserNotFoundException;
import com.company.mail.EmailService;
import com.company.repository.ConfirmationTokenRepository;
import com.company.repository.PasswordResetRepository;
import com.company.repository.UserRepository;
import com.company.service.UserService;
import com.company.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private static final String USERNAME_PATTERN = "^[a-zA-Z0-9_]{3,30}$";
    private static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{8,}$";
    private static final long EXPIRE_TOKEN_AFTER_MINUTES = 30;
    private static final Pattern patternUsername = Pattern.compile(USERNAME_PATTERN);
    private static final Pattern patternPass = Pattern.compile(PASSWORD_PATTERN);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final PasswordResetRepository passwordResetRepository;
    private final EmailService emailService;

    @Override
    public User registerUser(SignupRequest signupRequest) {
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new BadCredentialsException(signupRequest.getEmail() + " Email is already in use!");
        }
        if (!isValidUsername(signupRequest.getUsername())) {
            throw new BadCredentialsException(signupRequest.getUsername() + " username is not valid!");
        }
        if (!isValidPass(signupRequest.getPassword())) {
            throw new BadCredentialsException(signupRequest.getPassword() + " password must contain \n" +
                    "Min 1 uppercase letter.\n" +
                    "Min 1 lowercase letter.\n" +
                    "Min 1 special character as #$@!%&*?.\n" +
                    "Min 1 number.");
        }

        // Create new user's account
        User user = User.builder()
                .role("USER")
                .email(signupRequest.getEmail())
                .username(signupRequest.getUsername())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .isActive(true)
                .isVerified(false)
                .emailStatus(EmailStatus.NEW)
                .build();

       return userRepository.save(user);
    }

    @Override
    public String login(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.generateAccessToken(authentication);
        return jwt;
    }

    @Override
    public ResponseEntity<?> confirmEmail(String confirmationToken) {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if (token != null) {
            User user = userRepository.findByEmail(token.getUser().getEmail())
                    .orElseThrow(() -> new UserNotFoundException("Not found User"));
            user.setVerified(true);
            userRepository.save(user);
            return ResponseEntity.ok("Email verified successfully!");
        }
        return ResponseEntity.badRequest().body(" Email could not be verified!");
    }

    @Override
    public SimpleMailMessage createConfirmationTokenAndMailMessage(User user) {
        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        confirmationTokenRepository.save(confirmationToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setText("To confirm your account, please click here : "
                + "http://localhost:8087/api/v1/auth/confirm-account?token=" + confirmationToken.getConfirmationToken());


        return ResponseEntity.ok(mailMessage).getBody();
    }

    public String createResetTokenAndSendMailMessage(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Not found User!"));
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setUser(user);
        passwordResetToken.setToken(jwtUtil.generateTokenFromUsername(email));
        passwordResetToken.setTokenCreationDate(LocalDateTime.now());
        passwordResetRepository.save(passwordResetToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Complete Reset Password!");
        mailMessage.setText("To reset your password, please click here : "
                + "http://localhost:8087/api/v1/user/reset-password?token=" + passwordResetToken.getToken());

        emailService.sendEmail(mailMessage);
        return passwordResetToken.getToken();
    }

    @Override
    public List<User> findByEmailStatus(EmailStatus emailStatus) {
        return userRepository.findByEmailStatus(EmailStatus.NEW).get();
    }

    public String resetPassword(String token, String password) {

        PasswordResetToken passwordResetToken = passwordResetRepository.findByToken(token)
                .orElseThrow(() -> new UserNotFoundException("Invalid token"));
        jwtUtil.validateToken(token);

//        LocalDateTime tokenCreationDate = passwordResetToken.getTokenCreationDate();
//        if (isTokenExpired(tokenCreationDate)) {
//            return "Token expired.";
//        }
        if (!isValidPass(password)) {
            throw new BadCredentialsException(password + " password must contain " +
                    "\n Min 1 uppercase letter." +
                    "\n Min 1 lowercase letter." +
                    "\n Min 1 special character as #$@!%&*?." +
                    "\n Min 1 number.");
        }
        String email=jwtUtil.getUserNameFromJwtToken(token);
        User user=userRepository.findByEmail(email).get();
        //User user = passwordResetToken.getUser();
        user.setPassword(passwordEncoder.encode(password));
        passwordResetToken.setToken(null);
        passwordResetToken.setTokenCreationDate(null);

        userRepository.save(user);
        passwordResetRepository.save(passwordResetToken);

        return "Your password successfully updated.";
    }

    public boolean isValidUsername(String username) {
        return patternUsername.matcher(username).matches();
    }
    public boolean isValidPass(String password) {
        return patternPass.matcher(password).matches();
    }

    private String generateToken() {
        StringBuilder token = new StringBuilder();

        return token.append(UUID.randomUUID())
                .append(UUID.randomUUID()).toString();
    }

    private boolean isTokenExpired(final LocalDateTime tokenCreationDate) {

        LocalDateTime now = LocalDateTime.now();
        Duration diff = Duration.between(tokenCreationDate, now);

        return diff.toMinutes() >= EXPIRE_TOKEN_AFTER_MINUTES;
    }
    @Override
    public String createRefreshToken(String username) {
        return jwtUtil.generateRefreshTokenFromUsername(username);
    }


}
