FROM maven:3-amazoncorretto-17 AS builder

COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package -DskipTests

FROM openjdk:17-jdk
COPY --from=builder /usr/src/app/target/*.jar /usr/app/app.jar
EXPOSE 8080

ENTRYPOINT ["java","-jar","/usr/app/app.jar"]
