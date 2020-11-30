FROM openjdk:8-jdk-alpine
ADD ./target/*.jar /myapp/myapp.jar
CMD ["java", "-Xmx2048m", "-jar", "/myapp/myapp.jar"]
EXPOSE 8081