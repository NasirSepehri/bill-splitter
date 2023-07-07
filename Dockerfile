# syntax=docker/dockerfile:1.4
FROM  maven:3.8.5-openjdk-17-slim As build
WORKDIR /app
COPY src ./src
COPY pom.xml /app/
RUN --mount=type=cache,target=/root/.m2 mvn -f pom.xml clean package -DskipTests

FROM openjdk:17-alpine
WORKDIR /usr/local/app
COPY --from=build /app/target/bill-splitter-0.0.1-SNAPSHOT.jar ./bill-splitter-0.0.1-SNAPSHOT.jar
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar bill-splitter-0.0.1-SNAPSHOT.jar" ]
