package com.example.serviceB.application.controller;

import com.example.serviceB.application.service.TransactionService;
import com.example.serviceB.shared.dto.TransactionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("")
    public ResponseEntity<String> receiveTransaction(@RequestBody TransactionRequest transactionRequest) {
        // Publish the transaction event to Kafka
        transactionService.publishTransactionEvent(transactionRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body("Transaction received and event published.");
    }
}
