package com.example.serviceB.infrastructure.config;

import com.example.serviceB.domain.model.TransactionEventModel;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfiguration {
    @Value("${kafka.group.id}")
    private String GROUP_ID;

    @Value("${kafka.bootstrap.servers}")
    private String BOOTSTRAP_SERVERS;

    @Value("${kafka.sasl.jaas.config:}")
    private String saslJaasConfig;

    @Value("${kafka.security.protocol:}")
    private String securityProtocol;

    @Value("${kafka.sasl.mechanism:}")
    private String saslMechanism;

    @Value("${kafka.properties.ssl.client.auth:}")
    private String sslClientAuth;

    private Map<String, Object> getKafkaConfig(Class classDes) {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        config.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 1800000);
        config.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 10);
        config.put(ConsumerConfig.ALLOW_AUTO_CREATE_TOPICS_CONFIG, true);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, classDes);
        config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        config.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "100");
        config.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        if (!(saslJaasConfig.isBlank() || saslJaasConfig.isEmpty() || saslJaasConfig == null)) {
            // SASL configuration
            config.put("security.protocol", securityProtocol);
            config.put("sasl.mechanism", saslMechanism);
            config.put("sasl.jaas.config", saslJaasConfig);
            config.put("ssl.client.auth", sslClientAuth);
        }
        return config;
    }

    @Bean
    ConsumerFactory<String, TransactionEventModel> storeTransactionModelConsumerFactory() {

        return new DefaultKafkaConsumerFactory<>(getKafkaConfig(JsonDeserializer.class), new StringDeserializer(),
                new JsonDeserializer<>(TransactionEventModel.class, false));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, TransactionEventModel> storeTransactionModelConcurrentKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, TransactionEventModel>
                factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(storeTransactionModelConsumerFactory());
        return factory;
    }
}

