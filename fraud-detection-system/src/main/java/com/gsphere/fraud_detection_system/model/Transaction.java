package com.gsphere.fraud_detection_system.model;

public record Transaction(String transactionId,
                          String userId,
                          double amount,
                          String timestamp) {
}
