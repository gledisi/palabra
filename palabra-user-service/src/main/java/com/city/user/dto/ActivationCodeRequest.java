package com.city.user.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class ActivationCodeRequest {
    @NotEmpty
    @Size(min = 10, max = 20)
    private String mobile;
}
