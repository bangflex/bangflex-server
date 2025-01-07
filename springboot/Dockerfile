FROM --platform=linux/amd64 openjdk:17-alpine
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
