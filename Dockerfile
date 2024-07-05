FROM openjdk:8-jdk-alpine

WORKDIR /app

VOLUME /app/data

EXPOSE 800

COPY . .

ENTRYPOINT ["java","-jar","blog-app-server-0.0.1-SNAPSHOT.jar"]