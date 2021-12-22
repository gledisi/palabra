package com.city.message.service.dto;

import lombok.Data;

@Data
public class UserDetails {
    private String userId;
    private String contactName;
    private Byte[] contactPhoto;
}
