FROM openjdk:11

COPY target/NotificationMicroservice-0.0.1-SNAPSHOT.jar app.jar

ENV KAFKA_BROKER_HOST kafka
ENV KAFKA_BROKER_PORT 9092

ENTRYPOINT ["java", "-jar", "/app.jar"]