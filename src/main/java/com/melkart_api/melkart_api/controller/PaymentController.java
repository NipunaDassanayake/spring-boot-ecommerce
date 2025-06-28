package com.melkart_api.melkart_api.controller;

import com.melkart_api.melkart_api.controller.dto.request.PaymentRequestDTO;
import com.melkart_api.melkart_api.controller.dto.response.PaymentResponseDTO;
import com.melkart_api.melkart_api.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponseDTO> processPayment(
            @Valid @RequestBody PaymentRequestDTO paymentRequestDTO) {
        log.info("Received payment request for user ID: {}", paymentRequestDTO.getUserId());
        PaymentResponseDTO response = paymentService.processPayment(paymentRequestDTO);
        log.info("Payment processed successfully with transaction ID: {}", response.getTransactionId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<PaymentResponseDTO> getPayment(
            @PathVariable String transactionId) {
        log.info("Fetching payment details for transaction ID: {}", transactionId);
        PaymentResponseDTO response = paymentService.getPaymentByTransactionId(transactionId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{transactionId}/refund")
    public ResponseEntity<PaymentResponseDTO> refundPayment(
            @PathVariable String transactionId) {
        log.info("Processing refund for transaction ID: {}", transactionId);
        PaymentResponseDTO response = paymentService.refundPayment(transactionId);
        return ResponseEntity.ok(response);
    }

    // Additional endpoints could include:
    // - List payments by user
    // - List payments by status
    // - Cancel pending payment
    // - Verify payment status with external provider
}