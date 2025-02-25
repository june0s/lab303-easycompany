FROM maven:3.8.5-eclipse-temurin-8-alpine AS build

WORKDIR /build
COPY pom.xml .
COPY src ./src

RUN mvn package -Dmaven.test.skip=true

FROM tomcat:8.5.54-jdk8-corretto
#FROM tomcat:10-jdk17

# 패키지 업데이트 및 MySQL Client 설치
#RUN apt-get update && apt-get install -y mysql-client && apt-get install -y dnsutils && rm -rf /var/lib/apt/lists/*

RUN rm -Rf /usr/local/tomcat/webapps/*
COPY --from=build /build/target/lab303-easycompany-1.0.0.war /usr/local/tomcat/webapps/ROOT.war
