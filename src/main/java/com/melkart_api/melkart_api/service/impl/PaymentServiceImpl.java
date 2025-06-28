package com.melkart_api.melkart_api.service.impl;

import com.melkart_api.melkart_api.controller.dto.request.PaymentRequestDTO;
import com.melkart_api.melkart_api.controller.dto.response.PaymentResponseDTO;
import com.melkart_api.melkart_api.exceptions.InsufficientFundsException;
import com.melkart_api.melkart_api.exceptions.PaymentProcessingException;
import com.melkart_api.melkart_api.exceptions.ResourceNotFoundException;
import com.melkart_api.melkart_api.model.*;
import com.melkart_api.melkart_api.repository.NewProductRequestRepository;
import com.melkart_api.melkart_api.repository.PaymentRepository;
import com.melkart_api.melkart_api.repository.SystemProductRequestRepository;
import com.melkart_api.melkart_api.repository.UserRepository;
import com.melkart_api.melkart_api.service.PaymentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final SystemProductRequestRepository systemProductRequestRepository;
    private final NewProductRequestRepository newProductRequestRepository;

    @Override
    @Transactional
    public PaymentResponseDTO processPayment(PaymentRequestDTO paymentRequestDTO) {
        log.info("Processing payment for user ID: {}", paymentRequestDTO.getUserId());

        User user = userRepository.findById(paymentRequestDTO.getUserId())
                .orElseThrow(() -> {
                    log.error("User not found with ID: {}", paymentRequestDTO.getUserId());
                    return new ResourceNotFoundException("User not found");
                });

        Payment payment = new Payment();
        payment.setAmount(paymentRequestDTO.getAmount());
        payment.setCurrency(paymentRequestDTO.getCurrency());
        payment.setMethod(paymentRequestDTO.getMethod());
        payment.setUser(user);
        payment.setTransactionId(generateTransactionId());

        // Associate with request if applicable
        if (paymentRequestDTO.getSystemProductRequestId() != null) {
            SystemProductRequest systemProductRequest = systemProductRequestRepository
                    .findById(paymentRequestDTO.getSystemProductRequestId())
                    .orElseThrow(() -> {
                        log.error("System product request not found with ID: {}", paymentRequestDTO.getSystemProductRequestId());
                        return new ResourceNotFoundException("System product request not found");
                    });
            payment.setSystemProductRequest(systemProductRequest);
        } else if (paymentRequestDTO.getNewProductRequestId() != null) {
            NewProductRequest newProductRequest = newProductRequestRepository
                    .findById(paymentRequestDTO.getNewProductRequestId())
                    .orElseThrow(() -> {
                        log.error("New product request not found with ID: {}", paymentRequestDTO.getNewProductRequestId());
                        return new ResourceNotFoundException("New product request not found");
                    });
            payment.setNewProductRequest(newProductRequest);
        }

        try {
            // Process payment based on method
            switch (paymentRequestDTO.getMethod()) {
                case WALLET:
                    processWalletPayment(user, paymentRequestDTO.getAmount());
                    break;
                case CREDIT_CARD:
                case DEBIT_CARD:
                case BANK_TRANSFER:
                case PAYPAL:
                    processExternalPayment(paymentRequestDTO);
                    break;
                default:
                    throw new PaymentProcessingException("Unsupported payment method");
            }

            // Update payment status
            payment.setStatus(Payment.PaymentStatus.COMPLETED);
            payment.setProcessedAt(LocalDateTime.now());

            // Save payment record
            Payment savedPayment = paymentRepository.save(payment);
            log.info("Payment processed successfully with transaction ID: {}", savedPayment.getTransactionId());

            return mapToPaymentResponseDTO(savedPayment);
        } catch (Exception e) {
            log.error("Payment processing failed: {}", e.getMessage());
            payment.setStatus(Payment.PaymentStatus.FAILED);
            paymentRepository.save(payment);
            throw new PaymentProcessingException("Payment processing failed: " + e.getMessage(), e);
        }
    }

    private void processWalletPayment(User user, BigDecimal amount) {
        log.debug("Processing wallet payment for user ID: {}", user.getId());
        if (user.getWalletBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Insufficient funds in wallet");
        }
        user.setWalletBalance(user.getWalletBalance().subtract(amount));
        userRepository.save(user);
        log.debug("Wallet payment processed successfully. New balance: {}", user.getWalletBalance());
    }

    private void processExternalPayment(PaymentRequestDTO paymentRequestDTO) {
        log.debug("Processing external payment with method: {}", paymentRequestDTO.getMethod());
        // In a real implementation, this would integrate with a payment gateway
        // For now, we'll just simulate a successful payment
        try {
            // Simulate API call delay
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        log.debug("External payment processed successfully");
    }

    private String generateTransactionId() {
        // Get the current timestamp in the format "yyyyMMddHHmmss"
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        // Generate a UUID and take the first 8 characters of it
        String uuidPart = UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        // Combine the timestamp with the UUID
        return "TXN-" + timestamp + "-" + uuidPart;
    }


    private PaymentResponseDTO mapToPaymentResponseDTO(Payment payment) {
        PaymentResponseDTO responseDTO = new PaymentResponseDTO();
        responseDTO.setTransactionId(payment.getTransactionId());
        responseDTO.setAmount(payment.getAmount());
        responseDTO.setCurrency(payment.getCurrency());
        responseDTO.setStatus(payment.getStatus());
        responseDTO.setMethod(payment.getMethod());
        responseDTO.setCreatedAt(payment.getCreatedAt());
        responseDTO.setProcessedAt(payment.getProcessedAt());

        if (payment.getSystemProductRequest() != null) {
            responseDTO.setSystemProductRequestId(payment.getSystemProductRequest().getId());
        }
        if (payment.getNewProductRequest() != null) {
            responseDTO.setNewProductRequestId(payment.getNewProductRequest().getId());
        }

        return responseDTO;
    }

    @Override
    public PaymentResponseDTO getPaymentByTransactionId(String transactionId) {
        log.info("Fetching payment with transaction ID: {}", transactionId);
        Payment payment = paymentRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> {
                    log.error("Payment not found with transaction ID: {}", transactionId);
                    return new ResourceNotFoundException("Payment not found");
                });
        return mapToPaymentResponseDTO(payment);
    }

    @Override
    @Transactional
    public PaymentResponseDTO refundPayment(String transactionId) {
        log.info("Processing refund for transaction ID: {}", transactionId);
        Payment payment = paymentRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> {
                    log.error("Payment not found with transaction ID: {}", transactionId);
                    return new ResourceNotFoundException("Payment not found");
                });

        if (payment.getStatus() != Payment.PaymentStatus.COMPLETED) {
            log.warn("Cannot refund payment with status: {}", payment.getStatus());
            throw new PaymentProcessingException("Only completed payments can be refunded");
        }

        try {
            // Process refund based on original payment method
            switch (payment.getMethod()) {
                case WALLET:
                    refundToWallet(payment);
                    break;
                case CREDIT_CARD:
                case DEBIT_CARD:
                case BANK_TRANSFER:
                case PAYPAL:
                    processExternalRefund(payment);
                    break;
                default:
                    throw new PaymentProcessingException("Unsupported refund method");
            }

            payment.setStatus(Payment.PaymentStatus.REFUNDED);
            Payment refundedPayment = paymentRepository.save(payment);
            log.info("Payment refunded successfully for transaction ID: {}", transactionId);

            return mapToPaymentResponseDTO(refundedPayment);
        } catch (Exception e) {
            log.error("Refund processing failed: {}", e.getMessage());
            throw new PaymentProcessingException("Refund processing failed: " + e.getMessage(), e);
        }
    }

    private void refundToWallet(Payment payment) {
        log.debug("Processing wallet refund for user ID: {}", payment.getUser().getId());
        User user = payment.getUser();
        user.setWalletBalance(user.getWalletBalance().add(payment.getAmount()));
        userRepository.save(user);
        log.debug("Wallet refund processed successfully. New balance: {}", user.getWalletBalance());
    }

    private void processExternalRefund(Payment payment) {
        log.debug("Processing external refund for transaction ID: {}", payment.getTransactionId());
        // In a real implementation, this would integrate with a payment gateway
        // For now, we'll just simulate a successful refund
        try {
            // Simulate API call delay
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        log.debug("External refund processed successfully");
    }
}