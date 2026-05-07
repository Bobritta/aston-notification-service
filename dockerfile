FROM bellsoft/liberica-openjdk-alpine:23
WORKDIR /app
COPY build/libs/*.jar app.jar
# curl, чтобы Docker мог проверять "здоровье" сервиса
RUN apk add --no-cache curl
ENTRYPOINT ["java", "-jar", "app.jar"]
