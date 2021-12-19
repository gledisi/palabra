package com.palabra.user.dto;

import lombok.Data;

import java.util.List;

@Data
public class LoginResponse {
    private String mobile;
    private String token;
    private List<String> role;
}
