package com.melkart_api.melkart_api.controller.dto.request;

import com.melkart_api.melkart_api.model.Status;
import lombok.Data;

@Data
public class ProductUpdateRequestDTO {
    private String name;
    private String category;
    private String description;
    private String brand;
    private String model;
    private String sourceCountry;
    private Double price;
    private String currency;
    private String websiteUrl;
    private String imageUrl;
    private Status status;
}
