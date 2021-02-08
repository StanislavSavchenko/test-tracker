FROM maven:3.6.3-jdk-11 as build
COPY ./src /core/src
COPY ./pom.xml /core

WORKDIR /core
RUN mvn -f pom.xml clean install -DskipTests

FROM maven:3.6.3-jdk-11
COPY ./wait-for-it.sh /wait-for-it.sh
RUN chmod a+x /wait-for-it.sh
COPY --from=build /core/target/*.jar core.jar
ENTRYPOINT [ "/bin/bash", "-c" ]
CMD ["./wait-for-it.sh gateway:8888 --strict --timeout=300 -- java -jar core.jar"]