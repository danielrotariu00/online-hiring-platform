package com.licenta.notificationmicroservice.business.constants;

public class KafkaConstants {
    public static final String KAFKA_BROKER = String.format("%s:%s", System.getenv("KAFKA_BROKER_HOST"), System.getenv("KAFKA_BROKER_PORT"));
    public static final String GROUP_ID = "notification-microservice";
    public static final String NOTIFICATION_TOPIC = "notification-topic";
}