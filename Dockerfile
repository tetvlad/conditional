FROM eclipse-temurin:21-jre-alpine
EXPOSE 8888
ADD build/libs/conditional-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]