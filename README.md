# test_store_app



[![Build Status](https://travis-ci.com/mhsh88/test_store_app.svg)](https://travis-ci.com/mhsh88/test_store_app)
[![docker build](https://img.shields.io/docker/cloud/build/mhsh88/test_store_app)](https://cloud.docker.com/u/astorprotect/repository/docker/mhsh88/test_store_app)
[![codecov](https://codecov.io/gh/mhsh88/test_store_app/branch/master/graph/badge.svg)](https://codecov.io/gh/mhsh88/test_store_app)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=mhsh88_test_store_app&metric=alert_status)](https://sonarcloud.io/dashboard?id=mhsh88_test_store_app)

Design and implement [Spring Boot](http://projects.spring.io/spring-boot/) test store with ci/cd 

## Getting Started


There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `ir.sharifi.soroush.soroush_test_project.SoroushTestProjectApplication.java` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```


## Instructions

For compile (also runs unit tests)

```
mvn package
```

## Run integration tests

```
mvn verify
```

## Run the webapp manually

```
mvn spring-boot:run
```

after then navigate your browser to  http://localhost:8080/

## Create a docker image

```
mvn package
docker build -t test_store_app .
```


## Run the docker image

```
docker run -p 8080:8080 test_store_app
```