FROM openjdk:24-jdk-oracle

WORKDIR /app

COPY build/libs/app.jar app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]