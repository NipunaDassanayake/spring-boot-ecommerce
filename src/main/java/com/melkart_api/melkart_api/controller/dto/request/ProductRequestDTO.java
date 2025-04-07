package com.melkart_api.melkart_api.controller.dto.request;

import com.melkart_api.melkart_api.model.Status;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;



@Data
public class ProductRequestDTO {
    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    private String category;

    @NotNull
    private String brand;

    @NotNull
    private String model;

    @NotNull
    private String description;

    @NotNull
    private Double price;

    @NotNull
    private String currency;

    private String websiteUrl;

    private List<MultipartFile> imageUrls; // List for images

    private String sourceCountry;
    private Status status;
    private Long adminId;
}
