package com.melkart_api.melkart_api.controller.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateUserRequestDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private BigDecimal walletBalance;
    private Integer loyaltyPoints;

}
