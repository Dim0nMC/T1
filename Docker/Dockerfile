
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .

ARG MODULE
RUN mvn -pl $MODULE -am clean package -DskipTests


FROM eclipse-temurin:17-jdk
WORKDIR /app
ARG MODULE
COPY --from=build /app/$MODULE/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
