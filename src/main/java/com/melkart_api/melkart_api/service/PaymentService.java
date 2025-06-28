package com.melkart_api.melkart_api.service;
import com.melkart_api.melkart_api.controller.dto.request.PaymentRequestDTO;
import com.melkart_api.melkart_api.controller.dto.response.PaymentResponseDTO;

public interface PaymentService {
    PaymentResponseDTO processPayment(PaymentRequestDTO paymentRequestDTO);
    PaymentResponseDTO getPaymentByTransactionId(String transactionId);
    PaymentResponseDTO refundPayment(String transactionId);
}
