FROM openjdk:17-jdk

ARG JAR_FILE=build/libs/zipgo-backend-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} zipgo-backend-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
