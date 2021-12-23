package com.city.message.service.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UserDetails {
    private String userUUID;
    private String contactName;
    private Byte[] contactPhoto;
}
