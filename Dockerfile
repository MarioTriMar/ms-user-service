FROM openjdk:21
LABEL authors="mario"
COPY "./target/ms-user-service-0.0.1-SNAPSHOT.jar" "app.jar"
ENTRYPOINT ["java","-jar","app.jar"]

