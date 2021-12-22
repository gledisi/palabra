package com.city.user.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ContactResponse {
    private Long contactId;
    private String name;
    private LocalDateTime createdOn;
    private UserResponse user;
}
