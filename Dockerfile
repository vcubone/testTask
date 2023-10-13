FROM eclipse-temurin:19-alpine
COPY ./target/testTask-1.jar app.jar
CMD ["java","-jar","app.jar"]