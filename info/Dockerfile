FROM maven:3.6.3-jdk-11 as build
COPY ./src /info/src
COPY ./pom.xml /info

WORKDIR /info
RUN mvn -f pom.xml clean install -DskipTests

FROM maven:3.6.3-jdk-11
COPY --from=build /info/target/*.jar info.jar
ENTRYPOINT ["java", "-jar", "info.jar"]