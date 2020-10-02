package com.vnpt.polling.payload;

import lombok.Getter;
import lombok.Setter;

public class JwtAuthenticationResponse {

    @Setter @Getter
    private String accessToken;

    @Setter @Getter
    private String tokenType = "Bearer";

    public JwtAuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
