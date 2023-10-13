# Test-task
## Test task with SpringBoot (Java) Backend
The solution of a test task.

### Technology Stack
Component           | Technology
---                 | ---
Backend (REST)      | [SpringBoot](http://localhost:8080/string, http://localhost:8080/map) 
Server Build Tools  | Maven(Java) or Gradle
Documentation tools | Swagger
Test Tools          | Junit

## Folder Structure
```bash
PROJECT_FOLDER
│  README.md
│  pom.xml           
│  Dockerfile
└──[src]      
│  └──[main]      
│     └──[java/ru/batorov/testTask]
|        └──[config]      #Configuration files
|        └──[controllers] #Controllers
|        └──[services]    #Services
|        └──[utils]       #Utility files
│  └──[test]              #Contains tests
│
└──[target]              #Java build files, auto-created after running java build: mvn install
```
## Prerequisites
Ensure you have this installed before proceeding further
- Java 19
- Maven 2.7.13
- Docker(Optional)

## About
This is the solution of a test task.
The goal of the project is to 
- Highlight techniques of making a rest api

### Features of the Project
* Backend
  * The ability to get result as a map or a string

* Build
  * The ability to launch the applications image from dockerhub
 
### Build (SpringBoot Java)
* Run the command
```
mvn install
```
### Build Docker Image
* Download [docker image](https://hub.docker.com/repository/docker/vcubone/test-task/general)
* Create docker-compose.yml file
```
version: "3"
services:
  task:
    image: vcubone/test-task:main
    ports:
      - "8080:8080"
```
* Run the command
```
docker-compose up
```
### Accessing Application
Component         | URL
---               | ---
String            |  http://localhost:8080/string
Map               |http://localhost:8080/map
Swagger           |  http://localhost:8080/swagger-ui/
```
IN: String. String.length < 1000000
OUT /string: String. Like: "character": frequence, ...
OUT /map: Map<Character, Integer>
```
### Screenshots
#### Swagger
![Dashboard](/screenshots/swagger.png?raw=true)
---
#### Postman
![Dashboard](/screenshots/string.png?raw=true)
![Dashboard](/screenshots/map.png?raw=true)
---
