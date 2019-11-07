FROM openjdk:8-jre-alpine

WORKDIR micro689/
COPY target/micro689-*.jar micro689.jar

EXPOSE 8080

CMD ["java", "-jar", "micro689.jar"]
