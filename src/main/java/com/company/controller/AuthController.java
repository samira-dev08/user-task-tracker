package com.company.controller;

import com.company.dto.request.LoginRequest;
import com.company.dto.request.SignupRequest;
import com.company.dto.request.TokenRefreshRequest;
import com.company.dto.response.JwtResponse;
import com.company.dto.response.MessageResponse;
import com.company.entity.User;
import com.company.service.UserService;
import com.company.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserService userService;
    private final JwtUtil jwtUtil;


    @PostMapping("/sign-up")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
         User user=userService.registerUser(signupRequest);
        return ResponseEntity.ok(user.getUsername()+",you are registered successfully!" +
                "\nPlease check your email address for complete the registration");
    }

    @PostMapping("/sign-in")
    public MessageResponse login(@Valid @RequestBody LoginRequest loginRequest) {
       final String token = userService.login(loginRequest);
       final String refreshToken= userService.createRefreshToken(loginRequest.getEmail());
        return new MessageResponse( token,refreshToken);
    }

    @PostMapping( "/confirm-account")
    public ResponseEntity<?> confirmUserAccount(@Valid @RequestParam("token") String confirmationToken) {
        return userService.confirmEmail(confirmationToken);
    }
    @PostMapping("/refresh-token")
    public JwtResponse refreshToken(@RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();
        jwtUtil.validateToken(requestRefreshToken);

        String username=jwtUtil.extractClaims(request.getRefreshToken()).getSubject();
        String token = jwtUtil.generateTokenFromUsername(username);

        return new JwtResponse(token, requestRefreshToken);
    }
}
