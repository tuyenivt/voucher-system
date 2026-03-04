FROM eclipse-temurin:25-jdk-jammy AS builder
WORKDIR /develop
COPY . .
RUN chmod +x gradlew
RUN ./gradlew :voucher-service:bootJar -x test

FROM eclipse-temurin:25-jre-jammy
WORKDIR /release
COPY --from=builder /develop/voucher-service/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
