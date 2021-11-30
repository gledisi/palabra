package com.city.user.dto;

import com.city.user.entity.UserRole;
import lombok.Data;

@Data
public class VerifyCodeResponse {
    private String mobile;
    private String token;
    private UserRole role;
}
