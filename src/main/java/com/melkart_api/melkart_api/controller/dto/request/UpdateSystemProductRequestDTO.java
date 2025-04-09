package com.melkart_api.melkart_api.controller.dto.request;
import com.melkart_api.melkart_api.model.RequestStatus;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class UpdateSystemProductRequestDTO {
    private BigDecimal expectedPrice;
    private String sourceCountry;
    private String destinationCountry;
    private String destinationCity;
    private Boolean withBox;
    private LocalDate expectedDeliveryDate;
    private Integer quantity;
    private RequestStatus status;
}

