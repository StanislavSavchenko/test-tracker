FROM maven:3.6.3-jdk-11 as build-utils
COPY ./util/src /util/src
COPY ./util/pom.xml /util

WORKDIR /util
RUN mvn -f pom.xml clean install -DskipTests

FROM build-utils as build-core
COPY ./core/src /core/src
COPY ./core/pom.xml /core

WORKDIR /core
RUN mvn -f pom.xml clean install -DskipTests

FROM maven:3.6.3-jdk-11
COPY ./wait-for-it.sh /wait-for-it.sh
RUN chmod a+x /wait-for-it.sh
COPY --from=build-core /core/target/*.jar core.jar
ENTRYPOINT [ "/bin/bash", "-c" ]
CMD ["./wait-for-it.sh gateway:8888 --strict --timeout=300 -- java -jar core.jar"]