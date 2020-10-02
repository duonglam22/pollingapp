package com.vnpt.polling.payload;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

public class LoginRequest {

    @NotBlank
    @Setter @Getter
    private String usernameOrEmail;

    @NotBlank
    @Setter @Getter
    private String password;
}
