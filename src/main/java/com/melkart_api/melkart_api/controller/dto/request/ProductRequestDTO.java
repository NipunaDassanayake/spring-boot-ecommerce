package com.melkart_api.melkart_api.controller.dto.request;

import com.melkart_api.melkart_api.model.Status;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductRequestDTO {
    private String name;
    private String category;
    private String brand;
    private String model;
    private String description;
    private Double price;
    private String currency;
    private String websiteUrl;
    private String imageUrl;
    private String sourceCountry;
    private Status status;
    private Long adminId;
    private LocalDateTime createdAt;
}
