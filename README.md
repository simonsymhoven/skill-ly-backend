# ![](logos/logo_small.png) The backend part of Skill-Ly 

![JDK11](https://img.shields.io/badge/jdk-11-green.svg?label=min.%20JDK)
![Maven](https://img.shields.io/maven-central/v/org.apache.maven/apache-maven/3.6.3)
[![GitHub pull requests](https://img.shields.io/github/issues-pr/simonsymhoven/skill-ly-backend.svg)](https://github.com/simonsymhoven/skill-ly-backend/pulls)
![Travis](https://travis-ci.com/simonsymhoven/skill-ly-backend.svg?branch=master)


## Development server

Run `mvn spring-boot:run` or with the provided `Dockerfile`:

First start the postgreSQL database: 

`docker run --name dbpostgresql -e POSTGRES_PASSWORD=123 -p 5432:5432 postgres`

Then bundle the a Docker Image and run the Container for the spring-boot application:

`docker build -t spring-boot-api . && docker run --name spring-boot-api -p 8080:8080 --link dbpostgresql:postgres spring-boot-api`

Instead of do this procedure manuelly each time you can use following [Docker Plugin](https://www.jetbrains.com/help/idea/docker.html) for IntelliJ IDEA.

## Build

Run `mvn package -DskipTests` to build the project. The build artifacts will be stored in the `target/` directory.


## API

### Base URL
The base url depends on environment:
* dev: https://localhost:8080/api/v1
* dev: https://skill-ly-spring-boot.herokuapp.com/api/v1

### Endpoints
* POST /auth/signup
* POST /auth/signin
* GET /employee/{username}