FROM maven AS MAVEN_BUILD
COPY pom.xml /build/
COPY src /build/src/
WORKDIR /build/
RUN mvn package -DskipTests

FROM openjdk:12
WORKDIR /spring-boot/
EXPOSE 8080
COPY --from=MAVEN_BUILD /build/target/skill-ly-spring-boot-0.1.2-SNAPSHOT.jar /spring-boot/
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar",  "skill-ly-spring-boot-0.1.2-SNAPSHOT.jar"]

# PostgreSQL: docker run --name dbpostgresql -e POSTGRES_PASSWORD=123 -p 5432:5432 postgres
# SpringAPI: docker build -t spring-boot-api . && docker run --name spring-boot-api -p 8080:8080 --link dbpostgresql:postgres spring-boot-api
