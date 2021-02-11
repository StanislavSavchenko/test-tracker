FROM maven:3.6.3-jdk-11 as build
COPY ./src /flyway/src
COPY ./pom.xml /flyway

WORKDIR /flyway
RUN mvn -f pom.xml clean install -DskipTests

FROM maven:3.6.3-jdk-11
COPY ./wait-for-it.sh /wait-for-it.sh
RUN chmod a+x /wait-for-it.sh
COPY --from=build /flyway/target/*.jar flyway.jar
ENTRYPOINT [ "/bin/bash", "-c" ]
CMD ["./wait-for-it.sh discovery:8761 --strict --timeout=300 -- java -jar flyway.jar"]