package com.company.entity;

import lombok.Data;

import java.time.Instant;
@Data
public class RefreshToken {
    private User user;

    private String token;

    private Instant expiryDate;
}
