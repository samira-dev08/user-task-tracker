package com.company.dto.request;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class SignupRequest {
    @NotNull
//    @Length(min = 5, max = 20)
    private String username;
    @NotNull
    @Email (message = "invalid email address")
    private String email;
    @NotNull
//    @Length(min = 5, max = 10)
    private String password;
}
