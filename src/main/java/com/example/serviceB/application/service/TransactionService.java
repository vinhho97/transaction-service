package com.example.serviceB.application.service;

import com.example.serviceB.domain.entity.TransactionDetail;
import com.example.serviceB.domain.model.TransactionEventModel;
import com.example.serviceB.infrastructure.kafka.KafkaService;
import com.example.serviceB.infrastructure.redis.RedisService;
import com.example.serviceB.infrastructure.repository.TransactionRepository;
import com.example.serviceB.shared.dto.TransactionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final KafkaService kafkaService;
    private final RedisService redisService;
    private final TransactionRepository transactionRepository;

    public void publishTransactionEvent(TransactionRequest transactionRequest) {
        TransactionEventModel transactionEventModel = new TransactionEventModel();
        transactionEventModel.setTransactionId(transactionRequest.getTransactionId());
        transactionEventModel.setAmount(transactionRequest.getAmount());
        transactionEventModel.setCreatedAt(transactionRequest.getCreatedAt());
        transactionEventModel.setCurrency(transactionRequest.getCurrency());
        transactionEventModel.setAccountId(transactionRequest.getAccountId());

        kafkaService.sendEventTransactionMessage(transactionEventModel);
    }


    public boolean checkIdempotency(TransactionEventModel transactionEventModel) {
        // Use Redis SETNX to check idempotency
        String key = "transaction:" + transactionEventModel.getTransactionId();
        return redisService.setIfAbsent(key, "processed", 3600); // TTL of 1 hour
    }

    public void storeTransaction(TransactionEventModel transactionEventModel) {
        TransactionDetail transactionDetail = new TransactionDetail();
        transactionDetail.setId(UUID.randomUUID());
        transactionDetail.setAccountId(transactionEventModel.getAccountId());
        transactionDetail.setTransactionId(transactionEventModel.getTransactionId());
        transactionDetail.setAmount(transactionEventModel.getAmount());
        transactionDetail.setCurrency(transactionEventModel.getCurrency());
        transactionDetail.setCreatedAt(transactionEventModel.getCreatedAt());
        // Store transaction in the database
        transactionRepository.save(transactionDetail);
    }
}
