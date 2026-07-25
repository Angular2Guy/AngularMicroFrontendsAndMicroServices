FROM eclipse-temurin:25-jdk-alpine
VOLUME /tmp
RUN sh -c 'touch /app.jar'
ARG JAR_FILE
ADD build/libs/${JAR_FILE} /app.jar
ENV JAVA_OPTS="-XX:+UseSerialGC -XX:MaxRAMPercentage=60.0 -XX:MaxMetaspaceSize=160m -XX:MaxDirectMemorySize=64m -XX:+ExitOnOutOfMemoryError"
ENTRYPOINT exec java $JAVA_OPTS -Dliquibase.duplicateFileMode=WARN -Djava.security.egd=file:/dev/./urandom -jar /app.jar