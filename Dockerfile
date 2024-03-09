FROM gradle:7.1.1-jdk16 as builder
ENV APP_HOME=app
RUN mkdir APP_HOME
WORKDIR /app
COPY . .
RUN gradle build

FROM adoptopenjdk/openjdk11:alpine-jre
ENV BUILD_FILE=/app/build/libs/api-demo-0.0.1-SNAPSHOT.jar \
    APP=app/jarfile.jar

COPY --from=builder $BUILD_FILE $APP
EXPOSE 8080
CMD ["java", "-jar", "app/jarfile.jar"]