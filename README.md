# Event Reader System with Spring Boot

## Description

The **Event Reader System** is a Spring Boot application that processes insurance product data from incoming files, stores the data in an in-memory H2 database, and exposes a REST API for querying the processed information. The application provides the following key features:

- **File Processing**: Periodic reading and processing of files containing insurance data.
- **REST API**: Allows querying insurance products by `InsuredId`, grouped by `SourceCompany`.
- **Scheduled Refresh**: The system automatically reads files at a fixed interval (every 10 minutes).

## Features

1. **File Processing**:
    - The system periodically reads files from a specified directory (configured in `application.properties`).
    - After processing each file, it moves it to a backup folder for future reference.
    - Processed data is stored in an H2 in-memory database.

2. **REST API**:
    - Endpoint: `/api/events/products/{insuredId}`
    - **Function**: Fetch products by `InsuredId`, grouped by `SourceCompany`.
    - The API returns a JSON response with products grouped by their respective source companies.
    - Example response:
        ```json
        {
          "Menora": [
            {
              "type": "policy-a",
              "price": 2000,
              "startDate": "2024-05-30",
              "endDate": "2025-04-30"
            },
            {
              "type": "policy-a",
              "price": 2500,
              "startDate": "2024-07-30",
              "endDate": "2025-06-30"
            }
          ]
        }
        ```

3. **Scheduled Data Refresh**:
    - The application is designed to read input files every 10 minutes. This is handled using Spring's `@Scheduled` annotation.
    - The scheduled task is configurable via application properties (e.g., `fixedDelay` for periodic execution).

4. **Validation**:
    - When the API receives a request with an `InsuredId`, the system validates the ID by checking if there is any data associated with it.
    - If no data is found for the given `InsuredId`, a `404 Not Found` response is returned.
    - If an internal error occurs while fetching the data, the system returns a `500 Internal Server Error` with an error message.

## Architecture

The application follows a typical layered architecture:
- **Controller**: The REST API endpoints are defined in the `EventController`.
- **Service**: The logic for fetching and processing insurance product data is encapsulated in the `EventService` class.
- **Repository**: The application uses an in-memory H2 database for storing processed product data.
- **DTO (Data Transfer Objects)**: The `ProductDto` class is used to structure the data returned by the API.

## Technologies Used

- **Spring Boot**: Core framework for building the application.
- **H2 Database**: In-memory database for storing processed data.
- **Lombok**: For reducing boilerplate code in DTOs and service classes.
- **Java 21**: The application uses the latest long-term support version of Java.
- **Postman**: For testing and verifying the API endpoints.

## How to Run the Application

### Prerequisites
- Java 21 
- Maven (for building the project)

### Steps to Run Locally

1. Clone the repository:
   ```bash
   git clone https://github.com/BorisKoba/event-reader-app/tree/master
