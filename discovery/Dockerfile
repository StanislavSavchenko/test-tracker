FROM maven:3.6.3-jdk-11 as build
COPY ./src /discovery/src
COPY ./pom.xml /discovery

WORKDIR /discovery
RUN mvn -f pom.xml clean install -DskipTests

FROM maven:3.6.3-jdk-11
COPY --from=build /discovery/target/*.jar discovery.jar
ENTRYPOINT ["java","-jar","discovery.jar"]