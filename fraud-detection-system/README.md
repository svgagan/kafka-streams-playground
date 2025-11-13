# Fraud Detection System - Kafka Streams Playground

This project is a Kafka Streams playground built with Java, Gradle, and Spring Boot. It demonstrates stream processing using Apache Kafka and provides a simple interface to produce messages to Kafka topics.

## Getting Started

### Prerequisites

- Docker & Docker Compose
- Java 21
- Gradle

### Running Kafka with Docker Compose

A `docker-compose.yml` file is provided in the root directory to spin up Kafka and Zookeeper.

To start the services, run:

`docker compose -f docker-compose-kafka-ui.yml up`

This will start Kafka and Zookeeper containers in the background.

### Running the Application

Run the Spring Boot application locally using your IDE or with Gradle:


`./gradlew bootRun`


### Accessing the Swagger UI

Once the application is running, you can access the Swagger UI to produce messages to Kafka:

[http://localhost:8091/swagger-ui/index.html#/](http://localhost:8091/swagger-ui/index.html#/)

Use the available endpoints to interact with the Kafka topics.
