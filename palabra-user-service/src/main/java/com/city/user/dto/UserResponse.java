package com.city.user.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class UserResponse {
    private Long id;
    private String name;
    private String mobile;
    private Byte[] photo;
}
