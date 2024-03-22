FROM eclipse-temurin:17
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} eureka.jar
ENTRYPOINT ["java", "-jar", "/eureka.jar"]