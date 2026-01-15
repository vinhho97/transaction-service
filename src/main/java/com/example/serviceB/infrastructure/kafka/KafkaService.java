package com.example.serviceB.infrastructure.kafka;

import com.example.serviceB.domain.model.TransactionEventModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaService {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value(value = "${kafka.topic.name.transaction-event}")
    private String TOPIC_NAME_TRANSACTION_EVENT;

    public void sendEventTransactionMessage(TransactionEventModel model) {
        log.info("send {} to {}", model, TOPIC_NAME_TRANSACTION_EVENT);
        kafkaTemplate.send(TOPIC_NAME_TRANSACTION_EVENT, model);
    }
}
