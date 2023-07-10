FROM openjdk:17-alpine
WORKDIR /app
ARG JAR_FILE=target/bill-splitter-*.jar
COPY ${JAR_FILE} bill-splitter.jar
EXPOSE 8080
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar bill-splitter.jar" ]
