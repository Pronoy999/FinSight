FROM eclipse-temurin:17-jdk AS build

WORKDIR /app

COPY pom.xml .
COPY fin-sight-common ./fin-sight-common
COPY fin-sight-database ./fin-sight-database
COPY fin-sight-api ./fin-sight-api

RUN ls -l /app && ls -l /app/

RUN apt-get update && apt-get install -y maven

RUN mvn clean install

FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=build /app/fin-sight-api/target/fin-sight-api-1.0-SNAPSHOT.jar .
COPY --from=build /app/fin-sight-common/target/fin-sight-common-1.0-SNAPSHOT.jar .
COPY --from=build /app/fin-sight-database/target/fin-sight-database-1.0-SNAPSHOT.jar .
RUN ls -l /app && ls -l /app/

EXPOSE 8080

ENTRYPOINT ["java","-jar","fin-sight-api-1.0-SNAPSHOT.jar"]