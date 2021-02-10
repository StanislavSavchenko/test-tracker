FROM maven:3.6.3-jdk-11 as build
COPY ./src /authcenter/src
COPY ./pom.xml /authcenter

WORKDIR /authcenter
RUN mvn -f pom.xml clean install -DskipTests

FROM maven:3.6.3-jdk-11
COPY ./wait-for-it.sh /wait-for-it.sh
RUN chmod a+x /wait-for-it.sh
COPY --from=build /authcenter/target/*.jar authcenter.jar
ENTRYPOINT [ "/bin/bash", "-c" ]
CMD ["./wait-for-it.sh gateway:8888 --strict --timeout=300 -- java -jar authcenter.jar"]