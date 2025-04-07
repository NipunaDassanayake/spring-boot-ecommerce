package com.melkart_api.melkart_api.controller.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class NewProductRequestDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Expected price is required")
    @Min(value = 1, message = "Expected price must be greater than 0")
    private BigDecimal expectedPrice;

    @NotBlank(message = "Currency is required")
    private String currency;

    @NotBlank(message = "Source country is required")
    private String sourceCountry;

    @NotBlank(message = "Destination country is required")
    private String destinationCountry;

    @NotBlank(message = "Destination city is required")
    private String destinationCity;

    private String websiteUrl;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    @NotNull(message = "Box preference (with/without box) is required")
    private Boolean withBox;

    @Future(message = "Expected delivery date must be in the future")
    private LocalDate expectedDeliveryDate;

    @NotNull(message = "User ID is required")
    private Long userId;
}
