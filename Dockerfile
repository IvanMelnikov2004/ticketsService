FROM eclipse-temurin:21
WORKDIR /app
COPY build/libs/ptmplace-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "ptmplace-0.0.1-SNAPSHOT.jar"]