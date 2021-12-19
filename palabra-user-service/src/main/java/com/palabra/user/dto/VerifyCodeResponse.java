package com.palabra.user.dto;

import com.palabra.user.entity.UserRole;
import lombok.Data;

@Data
public class VerifyCodeResponse {
    private String mobile;
    private String token;
    private UserRole role;
}
