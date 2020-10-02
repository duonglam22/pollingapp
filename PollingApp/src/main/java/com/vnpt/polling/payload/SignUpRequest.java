package com.vnpt.polling.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SignUpRequest {

    @NotBlank
    @Size(min = 4, max = 40)
    @Setter @Getter
    private String name;

    @NotBlank
    @Size(max = 40)
    @Email
    @Setter @Getter
    private String username;

    @NotBlank
    @Email
    @Setter @Getter
    private String email;

    @NotBlank
    @Size(min = 6, max = 20)
    @Setter @Getter
    private String password;
}
