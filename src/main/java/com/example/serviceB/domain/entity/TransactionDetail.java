package com.example.serviceB.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transaction_detail")
public class TransactionDetail {

    @Id
    @Column(name = "id", length = 50, nullable = false)
    private UUID id;

    @Column(name = "transaction_id",length = 50)
    private String transactionId;

    @Column(name = "account_id", length = 50)
    private String accountId;

    @Column(name = "amount", length = 50)
    private BigDecimal amount;

    @Column(name = "currency", length = 50)
    private String currency;

    @Column(name = "created_at", length = 50)
    private LocalDateTime createdAt;
}
