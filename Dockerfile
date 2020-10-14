FROM maven:3.6.3-openjdk-8 AS builder
# Start with a base image containing Java runtime

LABEL maintainer="hossein.sharrif@gmail.com"
COPY src /build/src
COPY pom.xml /build/
WORKDIR /build/
RUN mvn install dependency:go-offline


FROM openjdk:8-jre-alpine
WORKDIR /app
COPY --from=builder /build/target/app.jar /app/
RUN apk add youtube-dl
RUN youtube-dl --version
EXPOSE ${PORT}
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dserver.port=$PORT","-jar","app.jar"]