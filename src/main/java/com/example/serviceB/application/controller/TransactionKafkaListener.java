package com.example.serviceB.application.controller;

import com.example.serviceB.application.service.TransactionService;
import com.example.serviceB.domain.model.TransactionEventModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;

@RequiredArgsConstructor
@Component
@Slf4j
public class TransactionKafkaListener {

    private final TransactionService transactionService;
    private final ExecutorService transactionExecutor;


    @KafkaListener(topics = "${kafka.topic.name.transaction-event}", groupId = "${kafka.group.id}", containerFactory = "storeTransactionModelConcurrentKafkaListenerContainerFactory")
    public void listenStoreTransaction(TransactionEventModel transactionEventModel) {
        transactionExecutor.submit(() -> {
            try {
                log.info("Processing transaction: {}", transactionEventModel);

                // Check idempotency using Redis
                boolean isNewTransaction = transactionService.checkIdempotency(transactionEventModel);
                if (!isNewTransaction) {
                    log.info("Transaction already processed: {}", transactionEventModel.getTransactionId());
                    return;
                }

                // Business processing: Store transaction in DB
                transactionService.storeTransaction(transactionEventModel);
                log.info("Transaction processed successfully: {}", transactionEventModel.getTransactionId());
            } catch (Exception e) {
                log.error("Error processing transaction: {}", e.getMessage(), e);
            }
        });
    }
}
