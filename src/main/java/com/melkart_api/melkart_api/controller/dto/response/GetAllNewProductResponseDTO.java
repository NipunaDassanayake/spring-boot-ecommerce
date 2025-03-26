package com.melkart_api.melkart_api.controller.dto.response;

import com.melkart_api.melkart_api.model.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class GetAllNewProductResponseDTO {

    private Long id;
    private String name;
    private String description;
    private BigDecimal expectedPrice;
    private String currency;
    private String sourceCountry;
    private String destinationCountry;
    private String destinationCity;
    private String websiteUrl;
    private Integer quantity;
    private Boolean withBox;
    private LocalDateTime createdAt;
    private LocalDate expectedDeliveryDate;
    private RequestStatus status;
    private Long userId;  // The user who requested the product

}
