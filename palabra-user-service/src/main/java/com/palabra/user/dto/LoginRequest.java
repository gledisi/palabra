package com.palabra.user.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class LoginRequest {
    private String token;
    @NotEmpty
    @Size(min = 6, max = 6)
    private String code;
}
