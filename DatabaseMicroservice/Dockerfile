FROM openjdk:11

COPY target/DatabaseMicroservice-0.0.1-SNAPSHOT.jar app.jar
COPY src/main/resources src/main/resources

ENTRYPOINT ["java", "-jar", "/app.jar"]