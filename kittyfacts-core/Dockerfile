FROM maven:3.8-amazoncorretto-11 AS MAVEN_TOOL_CHAIN
COPY ../pom.xml /tmp/
COPY src /tmp/src/
WORKDIR /tmp/
RUN mvn package

FROM adoptopenjdk:11-jre-hotspot
ARG JAR_FILE=*.jar
COPY --from=MAVEN_TOOL_CHAIN /tmp/target/${JAR_FILE} application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]
