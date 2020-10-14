# Start with a base image containing Java runtime
FROM openjdk:8-jdk-alpine

# Add Maintainer Info
LABEL maintainer="hossein.sharrif@gmail.com"
#RUN apt-get install -y youtube-dl

RUN apk add youtube-dl
# Add a volume pointing to /tmp
RUN youtube-dl --version
VOLUME /tmp

EXPOSE ${PORT}

# The application's jar file
ARG JAR_FILE=target/soroush_test_project-0.0.1-SNAPSHOT.jar

# Add the application's jar to the container
COPY ${JAR_FILE} app.jar
# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dserver.port=$PORT","-jar","/app.jar"]