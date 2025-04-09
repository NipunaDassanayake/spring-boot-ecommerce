package com.melkart_api.melkart_api.controller.dto.request;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CreateSystemProductRequestDTO {
    private Long productId;
    private Long userId;
    private BigDecimal expectedPrice;
    private String sourceCountry;
    private String destinationCountry;
    private String destinationCity;
    private Boolean withBox;
    private LocalDate expectedDeliveryDate;
    private Integer quantity;
}

