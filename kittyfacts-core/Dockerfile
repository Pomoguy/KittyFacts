FROM adoptopenjdk:11-jre-hotspot
ARG JAR_FILE=*.jar
COPY kittyfacts-core/target/${JAR_FILE} application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]