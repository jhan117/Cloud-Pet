# 1단계: 빌드
FROM openjdk:24-jdk-bullseye AS builder
WORKDIR /app
COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew clean bootJar -x test

# 2단계: 런타임
FROM openjdk:24-jdk-bullseye
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]