package com.company.security;

import com.company.dto.request.TokenRefreshRequest;
import com.company.entity.RefreshToken;
import com.company.entity.User;
import com.company.exception.TokenRefreshException;
import com.company.repository.UserRepository;
import com.company.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final JwtUtil jwtUtil;
    public String createRefreshToken(String username) {
        return jwtUtil.generateRefreshTokenFromUsername(username);
    }

}
