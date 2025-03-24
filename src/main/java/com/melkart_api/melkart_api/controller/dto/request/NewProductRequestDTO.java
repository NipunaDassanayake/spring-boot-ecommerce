package com.melkart_api.melkart_api.controller.dto.request;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class NewProductRequestDTO {
    private String name;
    private String category;
    private String description;
    private BigDecimal expectedPrice;
    private String currency;
    private String sourceCountry;
    private Long userId;
}