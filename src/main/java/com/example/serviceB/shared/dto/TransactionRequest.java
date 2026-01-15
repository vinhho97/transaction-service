package com.example.serviceB.shared.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionRequest {
    private String transactionId;
    private String accountId;
    private BigDecimal amount;
    private String currency;
    private LocalDateTime createdAt;
}
