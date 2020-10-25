FROM maven:3.6.3-openjdk-8 AS builder
# Start with a base image containing Java runtime


COPY src /build/src
COPY pom.xml /build/
WORKDIR /build/

RUN mvn dependency:go-offline
RUN mvn package


#FROM openjdk:8-jre-alpine
FROM alpine
LABEL maintainer="hossein.sharrif@gmail.com"
WORKDIR /app
#COPY target/soroush_test_project-0.0.1-SNAPSHOT.jar /app/
COPY --from=builder /build/target/app.jar /app/
RUN apk add youtube-dl && apk add openjdk11 --repository=http://dl-cdn.alpinelinux.org/alpine/edge/community && apk upgrade
#&& apk add tor --update-cache --repository http://dl-3.alpinelinux.org/alpine/edge/testing/ && rm -rf /var/cache/apk/*
#COPY torrc.default /app/
#RUN chown -R tor /etc/tor
#USER tor
#\
#                                         tor \
#                                         --update-cache --repository http://dl-3.alpinelinux.org/alpine/edge/testing/ \
#                                         && rm -rf /var/cache/apk/*
RUN youtube-dl --version
#RUN rc-status --list
EXPOSE ${PORT}
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dserver.port=$PORT","-jar","app.jar"]
#COPY commands.sh /app/commands.sh
#RUN ["chmod", "+x", "/app/commands.sh"]
#ENTRYPOINT ["/app/commands.sh"]