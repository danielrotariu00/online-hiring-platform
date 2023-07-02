FROM openjdk:11

COPY target/JobApplicationMicroservice-0.0.1-SNAPSHOT.jar app.jar
COPY src/main/resources src/main/resources

ENV KAFKA_BROKER_HOST kafka
ENV KAFKA_BROKER_PORT 9092
ENV DATABASE_MICROSERVICE_HOST database-microservice
ENV DATABASE_MICROSERVICE_PORT 23050

ENTRYPOINT ["java", "-jar", "/app.jar"]