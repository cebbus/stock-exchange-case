# Stock Exchange Case Study

There are many stock exchanges and many stocks in the world and this is "the case api" to manage them properly.

## Tech Stack
* Java 11
* Spring Boot 2.4.5
* Maven

## Build & Run
* Import as a maven project and run as Java application.
* You might require enable annotation processing since the project uses Lombok. Please check the [link](https://www.baeldung.com/lombok-ide) to enable on Intellij and Eclipse.
* It uses H2 database but no need to execute any script by manually. Flyway dependency added so it should be handle DB initialization automatically once you run the project.

## Endpoints
All endpoints require basic authentication.

**username:** admin
**password:** welcome

Please check the [link](https://documenter.getpostman.com/view/25440395/2sA3s9D8jQ) to REST API doc.