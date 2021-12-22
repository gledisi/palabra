package com.city.user.dto;

import lombok.Data;

@Data
public class ChangeContactNameRequest {
    private Long contactId;
    private String newName;
}
