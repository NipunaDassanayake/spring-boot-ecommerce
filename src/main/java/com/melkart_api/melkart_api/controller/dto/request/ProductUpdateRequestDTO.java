package com.melkart_api.melkart_api.controller.dto.request;

import com.melkart_api.melkart_api.model.Status;
import lombok.Data;

import java.util.List;

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
    private List<String> imageUrls;  // Change this to handle multiple images
    private Status status;
    private Long subCategoryId; // <-- Add this to support updating subcategory
}
