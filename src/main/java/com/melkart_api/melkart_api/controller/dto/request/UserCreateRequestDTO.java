package com.melkart_api.melkart_api.controller.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserCreateRequestDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;

}
