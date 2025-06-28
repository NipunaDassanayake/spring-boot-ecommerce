package com.melkart_api.melkart_api.controller.dto.response;
import com.melkart_api.melkart_api.model.Payment;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentResponseDTO {
    private String transactionId;
    private BigDecimal amount;
    private String currency;
    private Payment.PaymentStatus status;
    private Payment.PaymentMethod method;
    private LocalDateTime createdAt;
    private LocalDateTime processedAt;
    private Long systemProductRequestId;
    private Long newProductRequestId;
}
