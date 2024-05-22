# Rate Your Books

## Table of Contents

- [Executive Summary](#executive-summary)
- [Contact](#contact)
- [Prerequisites](#prerequisites)
- [Setup & Installation Instructions](#setup--installation-instructions)
- [Testing Instructions](#testing-instructions)
- [Deployment Instructions](#deployment-instructions)

## Executive Summary

Rate Your Books is a simple demo application used for keeping track of someone's favorite books. Users can give reviews and ratings to the books, as well as
create and share tier lists of books.

## Contact

For any inquiries, please contact:

Niklas Witzel - [niklas.witzel@cimt-ag.de](mailto:niklas.witzel@cimt-ag.de)

## Prerequisites

Before you begin, ensure you have met the following requirements:

- Java 21 or later
- Maven 3.9.6 or later
- Git (optional)

## Setup & Installation Instructions

1. Clone the repository:
    ```sh
    git clone https://github.com/yourusername/rate-your-books.git
    cd rate-your-books
    ```

2. Build the project:
    ```sh
    mvn clean install
    ```

3. Run the application:
    ```sh
    mvn spring-boot:run
    ```

The application will start on `http://localhost:8080`.

## Testing Instructions

To run tests, use the following command:

```sh
mvn test
```

## Deployment Instructions

For deploying the application, you can package it as a Docker container, deploy to a cloud service, or use any other method suitable for your environment.

### Docker

1. Build the Docker image:

```sh
   docker build -t rate-your-books .
```

2. Run the Docker container:

```sh
   docker run -p 8080:8080 rate-your-books
```
