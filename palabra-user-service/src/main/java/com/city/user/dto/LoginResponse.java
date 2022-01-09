package com.city.user.dto;

import lombok.Data;

import java.util.List;

@Data
public class LoginResponse {
    private String userId;
    private String mobile;
    private String token;
    private List<String> role;
}
