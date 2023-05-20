FROM openjdk:11

COPY target/SearchMicroservice-0.0.1-SNAPSHOT.jar app.jar
COPY /out/artifacts/SearchMicroservice_jar/SearchMicroservice.jar SearchMicroservice.jar

ENV SPARK_MASTER_HOST spark-master
ENV SPARK_MASTER_PORT 7077
ENV DATABASE_MICROSERVICE_HOST database-microservice
ENV DATABASE_MICROSERVICE_PORT 23050

ENTRYPOINT ["java", "-jar", "/app.jar"]