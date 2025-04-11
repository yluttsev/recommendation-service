FROM amazoncorretto:17-alpine
COPY build/libs/recommendation-service.jar .
ENTRYPOINT ["java", "-jar", "recommendation-service.jar"]