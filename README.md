HOW TO BUILD/RUN/USE THE APP:
============================

1.- Create the docker network executing:

docket network create itinerary-network

2.- Build and run project fgracia-itinerary-api, by going to the project base directory and executing the following commands

mvn clean package

docker build -t itinerary-api .

docker run -p 8080:8080 --net itinerary-network --name itinerary-api itinerary-api


3.- To use fgracia-itinerary-api, swagger can be found in

http://localhost:8080/swagger-ui/

4.- Build and run project fgracia-itinerary-calculator, by going to the project base directory and executing the following commands

mvn clean package

docker build -t itinerary-calculator .

docker run -p 8081:8081 --net itinerary-network --name itinerary-calculator itinerary-calculator

5.- To use fgracia-itinerary-calculator, swagger can be found in

http://localhost:8081/swagger-ui/


FRAMEWORK/LIBRARIES USED
========================

Spring Boot: Java Framework to create fast and easy ejecutable Java applications, is very useful to create from scratch a RESTful service.

Spring JPA: Used to access the database with no need of sql queries.

H2 database: Memory-stored database. It is not a good option for a production environment, but fits perfectly the requeriments I needed for this application

Springfox Swagger UI: Library that auto-generates the swagger UI using a few annotations

JUnit and Mockito: Used to execute unit tests emulating the behavior of external elements of the class we want to test
