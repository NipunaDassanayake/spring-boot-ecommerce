package com.melkart_api.melkart_api.controller.dto.response;
import com.melkart_api.melkart_api.model.RequestStatus;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class SystemProductResponseDTO {
    private Long id;
    private BigDecimal expectedPrice;
    private String sourceCountry;
    private String destinationCountry;
    private String destinationCity;
    private Boolean withBox;
    private LocalDate expectedDeliveryDate;
    private Integer quantity;
    private LocalDateTime createdAt;
    private RequestStatus status;
    private Long productId;
    private Long userId;
}
