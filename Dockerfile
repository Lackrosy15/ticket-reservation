FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . /app
RUN mvn clean package

FROM tomcat:9.0-jdk17-openjdk

COPY --from=build /app/target/ticket-reservation*.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080

CMD ["catalina.sh", "run"] 