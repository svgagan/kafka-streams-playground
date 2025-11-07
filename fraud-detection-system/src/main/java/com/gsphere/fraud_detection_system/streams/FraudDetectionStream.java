package com.gsphere.fraud_detection_system.streams;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gsphere.fraud_detection_system.model.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;

@Slf4j
@Configuration
@EnableKafkaStreams
public class FraudDetectionStream {

    private final ObjectMapper customObjectMapper;

    public FraudDetectionStream(ObjectMapper customObjectMapper) {
        this.customObjectMapper = customObjectMapper;
    }

    @Bean
    public KStream<String, String> fraudDetectorStream(StreamsBuilder builder) {

        //1. Read from a kafka topic
        KStream<String, String> transactionStream = builder.stream("transactions");

        //2. Detect fraud transactions from stream of transactions (Any transaction greater than 10k+ is fraudulent)
        KStream<String, String> fraudTransactionStream = transactionStream.filter((key, value) -> isSuspicious(value))
                .peek((key, value) -> {
                    log.warn("⚠️ FRAUD ALERT - transactionId={} , value={}", key, value);
                });

        //3. Fraudulent transactions are sent to new topic
        fraudTransactionStream.to("fraud-alerts");

        return transactionStream;
    }

    private boolean isSuspicious(String value) {
        try {
            Transaction transaction = customObjectMapper.readValue(value, Transaction.class); // validate JSON
            return transaction.amount() > 10000; // simple fraud rule
        } catch (Exception e) {
            return false;
        }
    }
}
