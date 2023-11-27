package com.company.security;


import com.company.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final JwtUtil jwtUtil;

    public String createRefreshToken(String username) {
        return jwtUtil.generateRefreshTokenFromUsername(username);
    }

}
