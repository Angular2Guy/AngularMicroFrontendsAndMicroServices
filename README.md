# AngularMicroFrontendsAndMicroServices

Author: Sven Loesekann

Technologies: Angular, Angular-Cli, Angular-Material, Typescript, Spring Boot, NestJs, TypeOrm, Liquibase, Jpa, Gradle, Java, Kotlin, Postgresql, Mqtt, ActiveMQ Artemis

[![CodeQL](https://github.com/Angular2Guy/AngularMicroFrontendsAndMicroServices/actions/workflows/codeql-analysis.yml/badge.svg)](https://github.com/Angular2Guy/AngularMicroFrontendsAndMicroServices/actions/workflows/codeql-analysis.yml)

## Articles
* [Microfrontends in a Microservice Architecture](https://angular2guy.wordpress.com/2024/11/16/microfrontends-in-a-microservice-architecture/)

## Mission Statement
The project shows howto build a collection of Microservices with their own Angular Microfrontends. The Microfrontends are the integrated in the shell application to give the user the impression of a single frontend. That enables the independend development of the Microservices by different teams with a user friendly modular integrated frontend -> no frontend monolith. 

To project has three Microserives that enable the user to book a flight, to book a hotel and pay for the bookings at the checkout. The payment microservice integrates the hotel booking and flight booking Microservices. 

The microservices are build according to the [Self Containted Systems](https://scs-architecture.org/) architecture. This architecture uses the bounded contexts of DDD to create services with distinct functionality. Each service has its own frontend, backend and database. The services should communicate only asynchronous with messaging by the backend. In the SCS architecture the focus of the Microservices is not so much on the size of each service then on the independence of the services. That enables an independent development of the Microservices and could keep the other Microservices working if one fails. The independent Microservices enable a the use of different software stacks for each one. That is used in the project to show 3 microservices: NestJs with Typescript, Spring Boot with Java and Spring Boot with Kotlin. Each microservice has an Angular frontend.

## C4 Architecture Diagrams
The [System Context Diagram](structurizr/diagrams/SystemContext.svg), the [Container Diagram](structurizr/diagrams/Containers.svg) and the [Component Diagram](structurizr/diagrams/Components.svg). The Diagrams have been created with Structurizr. The file runStructurizr.sh contains the commands to use Structurizr and the directory structurizr contains the dsl file.

### Flight Selection
The Flight Selection Microservice enables the user to select and book a flight. The Microservice has an Angular Frontend and a NestJs Typescript backend. The Postgresql  database is used to data storage and ApacheMQ Artemis with Mqtt to send the bookings.

For development was the Visual Studio Code ide used.

### Hotel Selection
The Hotel Selection Microservice enables the user to select and book a hotel. The Microservice has an Angular Frontend and Spring Boot Kotlin backend. The Postgresql  database is used to data storage and ApacheMQ Artemis with Mqtt to send the bookings.

For development was the Visual Studio Code ide for the frontend and Intellij CE for the backend used. 

### Payment 
The Payment Microservice integrates the Hotel Selection and Flight Selection frontends and adds the payment functionality. For the frontend integration iframes are used to have full independence of used Angular versions. Native Federation would be an alternative but then there are dependencies in versioning and the Microfrontends are not fully precompiled. The Microservice has an Angular Frontend and a Spring Boot Java backend. The Postgresql  database is used to data storage and ApacheMQ Artemis with Mqtt to receive the flight and hotel bookings.

For development was the Eclipse ide used.

## Microservice architecture
All Microservices use the Clean Architecture with the Rings Adapter, Usecase, Domain to structure the code. The Dtos for the Rest controllers and the Mqtt clients are decoupled by Mappers to be able to change interfaces and database tables independently. The dependency management is done by injection. The communication between microservices is done with MQTT and Apache Artemis.

## Run the System
- Build the system with Jdk 24 due to missing Kotlin Compiler support for Jdk 25(The bytecode of all Jars can be run on Jdk 25): 

./gradlew clean build -PwithAngular=true -PwithNestJs=true

- Create the databases with: 

docker run --name hotel-selection-postgres -e POSTGRES_PASSWORD=sven1 -e POSTGRES_USER=sven1 -e POSTGRES_DB=hotel_selection -p 5432:5432 -d postgres

docker run --name flight-selection-postgres -e POSTGRES_PASSWORD=sven1 -e POSTGRES_USER=sven1 -e POSTGRES_DB=flight_selection -p 5433:5432 -d postgres

docker run --name payment-postgres -e POSTGRES_PASSWORD=sven1 -e POSTGRES_USER=sven1 -e POSTGRES_DB=payment -p 5434:5432 -d postgres

- Run the database script [initDB.sql](flightSelection-backend/src/nestjs/init_db/initDB.sql) with the tool of you choice on the 'flight_selection' database.

- Create the ActiveMQ Artemis server with: 

docker run --name local-artemis-microservices -p 61616:61616 -p 1883:1883 -p 8161:8161 -e ARTEMIS_USER=artemis1 -e ARTEMIS_PASSWORD=artemis1 apache/activemq-artemis:latest-alpine

- Run the 'buildDocker.sh' scripts in the flightSelection-backend, hotelSelection-backend and the payment-backend. 

- Access the system with a browser with 'http://localhost:8080/'.