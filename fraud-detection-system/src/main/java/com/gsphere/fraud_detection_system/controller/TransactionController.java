package com.gsphere.fraud_detection_system.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gsphere.fraud_detection_system.model.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Random;

@RestController
@RequestMapping("/api/transactions")
@Slf4j
public class TransactionController {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper customObjectMapper;

    public TransactionController(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper customObjectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.customObjectMapper = customObjectMapper;
    }

    @PostMapping
    public String sendTransaction() throws Exception {
        for (int i = 0; i < 50; i++) {
            String transactionId = "txn-" + System.currentTimeMillis() + "-" + i;
            double amount = 8000 + new Random().nextDouble() * (11000 - 8000);

            Transaction txn = new Transaction(
                    transactionId,
                    "USER_" + i,
                    amount,
                    LocalDateTime.now().toString()
            );

            String txnJson = customObjectMapper.writeValueAsString(txn);

            kafkaTemplate.send("transactions", transactionId, txnJson);
        }
        log.info("✅ Transaction sent to Kafka!");
        return "✅ Transaction sent to Kafka!";
    }
}
