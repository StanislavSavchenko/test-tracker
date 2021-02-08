FROM maven:3.6.3-jdk-11 as build
COPY ./src /gateway/src
COPY ./pom.xml /gateway

WORKDIR /gateway
RUN mvn -f pom.xml clean install -DskipTests

FROM maven:3.6.3-jdk-11
COPY ./wait-for-it.sh /wait-for-it.sh
RUN chmod a+x /wait-for-it.sh
COPY --from=build /gateway/target/*.jar gateway.jar
ENTRYPOINT [ "/bin/bash", "-c" ]
CMD ["./wait-for-it.sh discovery:8761 --strict --timeout=300 -- java -jar gateway.jar"]