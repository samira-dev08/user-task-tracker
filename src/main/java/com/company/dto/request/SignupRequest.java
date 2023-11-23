package com.company.dto.request;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;

@Data
public class SignupRequest {
    @NotNull
    private String username;
    @NotNull
    @Email (message = "invalid email address")
    private String email;
    @NotNull
    private String password;
}
