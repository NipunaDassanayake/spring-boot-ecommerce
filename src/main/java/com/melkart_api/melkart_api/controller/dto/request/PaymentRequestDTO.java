package com.melkart_api.melkart_api.controller.dto.request;
import com.melkart_api.melkart_api.model.Payment;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRequestDTO {
    private Long userId;
    private BigDecimal amount;
    private String currency;
    private Payment.PaymentMethod method;
    private Long systemProductRequestId;
    private Long newProductRequestId;
}