package com.city.message.service.dto;

import lombok.Data;

@Data
public class ForwardMessage {
    private Long toUser;
    private String text;
}
