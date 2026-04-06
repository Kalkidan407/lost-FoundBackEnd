
# Lost & Found API

 #### A secure RESTful backend for a Lost & Found platform built with Spring Boot, featuring JWT authentication and interactive API documentation via Swagger.

## Features

🔐 JWT Authentication (Login & Register)

👤 User Management

## Lost & Found Item Management

```
   Role-Based Authorization

  Interactive API Documentation (Swagger UI)

  PostgreSQL Database Integration

  Cloud Deployment Ready (Render)
```


## Tech Stack
```
 Java 21

 Spring Boot 3.4.5

 Spring Security

 Spring Data JPA

 JWT (JSON Web Token)

 PostgreSQL

 Maven

 Swagger / OpenAPI 2.6.0 (springdoc)
```

## Project Structure

```
 src/main/java/com/lostfound/lostfound
│
├── config/          # Security & OpenAPI configuration
├── controller/      # REST Controllers
├── service/         # Business logic layer
├── repository/      # JPA repositories
├── model/           # Entities
├── dto/             # Data Transfer Objects
├── security/        # JWT filter & utilities custom implementation
└── LostfoundApplication.java
```

## Authentication Flow 

  1. User register via  
     ```
      POST /api/auth/register
     ```

 2. User login via 
  ```
    POST /api/auth/login
  ``` 
 3. Server returns JWT token 
 ```
   {
     "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
  }
```


4. Use token in requests
   
   ```
   Authorization: Bearer <your_token>
   ```
 

## API Documentation
 Swagger Ui avaliable at :- 
   https://findme-1-e6k5.onrender.com/swagger-ui/index.html#/


## How to Authorize in swagger
 1. Click Authorize
 2.  Paste :- 
    ```
    Bearer <your-token>
    ```
 3. Now access protected endpoints 🔐

## Enviroment variables

    ```
       spring.datasource.url=jdbc:postgresql://.....
       spring.datasource.username=${SPRING_DATASOURCE_USERNAME}
       spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}
    ```


 ## Run Locally

1️⃣ Clone Repository

```
  git clone  https://github.com/Kalkidan407/lost-FoundBackEnd.git
  cd lostfound-api
```

2️⃣ Build Project
```
  mvn clean install
```
3️⃣ Run Application   
 ```
  mvn spring-boot:run
 ```

##  Security

```   
 Passwords encrypted using BCrypt
 Stateless authentication using JWT
 All protected routes require valid token
 Swagger configured with Bearer Authentication
```

## Future Improvements
 ```
 Email verification
 Password reset
 Role-based ADMIN dashboard
 Image upload support (Cloud storage)
 Pagination & filtering
 Unit & integration tests
```

##  Author
```
  Developed by Kalkidan Kelemework
  Backend Developer | Spring Boot | REST APIs | Cloud Deployment
```


#14 15.56 [ERROR]   location: variable dto of type com.lostfound.lostfound.dto.location.LocationResponse
#14 15.56 [ERROR] /app/src/main/java/com/lostfound/lostfound/service/LocationService.java:[46,29] cannot find symbol
#14 15.56 [ERROR]   symbol:   method getName()
#14 15.56 [ERROR]   location: variable request of type com.lostfound.lostfound.dto.location.LocationRequest
#14 15.56 [ERROR] /app/src/main/java/com/lostfound/lostfound/service/LocationService.java:[47,29] cannot find symbol
#14 15.56 [ERROR]   symbol:   method getLatitude()
#14 15.56 [ERROR]   location: variable request of type com.lostfound.lostfound.dto.location.LocationRequest
#14 15.56 [ERROR] /app/src/main/java/com/lostfound/lostfound/service/LocationService.java:[48,30] cannot find symbol
#14 15.56 [ERROR]   symbol:   method getLongitude()
#14 15.56 [ERROR]   location: variable request of type com.lostfound.lostfound.dto.location.LocationRequest
#14 15.56 [ERROR] /app/src/main/java/com/lostfound/lostfound/service/LocationService.java:[91,36] cannot find symbol
#14 15.56 [ERROR]   symbol:   method getName()
#14 15.56 [ERROR]   location: variable updateedLocation of type com.lostfound.lostfound.dto.location.LocationRequest
#14 15.56 [ERROR] /app/src/main/java/com/lostfound/lostfound/service/LocationService.java:[92,40] cannot find symbol
#14 15.56 [ERROR]   symbol:   method getLatitude()
#14 15.56 [ERROR]   location: variable updateedLocation of type com.lostfound.lostfound.dto.location.LocationRequest
#14 15.56 [ERROR] /app/src/main/java/com/lostfound/lostfound/service/LocationService.java:[93,40] cannot find symbol
#14 15.56 [ERROR]   symbol:   method getLongitude()
#14 15.56 [ERROR]   location: variable updateedLocation of type com.lostfound.lostfound.dto.location.LocationRequest
#14 15.56 [ERROR] /app/src/main/java/com/lostfound/lostfound/service/ReportService.java:[32,21] cannot find symbol
#14 15.56 [ERROR]   symbol:   method getId()
#14 15.56 [ERROR]   location: variable report of type com.lostfound.lostfound.model.Report
#14 15.56 [ERROR] /app/src/main/java/com/lostfound/lostfound/service/ReportService.java:[33,26] cannot find symbol
#14 15.56 [ERROR]   symbol:   method getMessage()
#14 15.56 [ERROR]   location: variable report of type com.lostfound.lostfound.model.Report
#14 15.56 [ERROR] /app/src/main/java/com/lostfound/lostfound/service/ReportService.java:[35,13] cannot find symbol
#14 15.56 [ERROR]   symbol:   method getUser()
#14 15.56 [ERROR]   location: variable report of type com.lostfound.lostfound.model.Report
#14 15.56 [ERROR] /app/src/main/java/com/lostfound/lostfound/service/ReportService.java:[37,19] cannot find symbol
#14 15.56 [ERROR]   symbol:   method getUser()
#14 15.56 [ERROR]   location: variable report of type com.lostfound.lostfound.model.Report
#14 15.56 [ERROR] /app/src/main/java/com/lostfound/lostfound/service/ReportService.java:[38,21] cannot find symbol
#14 15.56 [ERROR]   symbol:   method getUser()
#14 15.56 [ERROR]   location: variable report of type com.lostfound.lostfound.model.Report
#14 15.56 [ERROR] /app/src/main/java/com/lostfound/lostfound/service/ReportService.java:[39,22] cannot find symbol
#14 15.56 [ERROR]   symbol:   method getUser()
#14 15.56 [ERROR]   location: variable report of type com.lostfound.lostfound.model.Report
#14 15.56 [ERROR] /app/src/main/java/com/lostfound/lostfound/service/ReportService.java:[40,8] cannot find symbol
#14 15.56 [ERROR]   symbol:   method setReportedBy(com.lostfound.lostfound.dto.user.UserResponse)
#14 15.56 [ERROR]   location: variable dto of type com.lostfound.lostfound.dto.report.ReportResponse
#14 15.56 [ERROR] /app/src/main/java/com/lostfound/lostfound/service/ReportService.java:[50,28] cannot find symbol
#14 15.56 [ERROR]   symbol:   method getMessage()
#14 15.56 [ERROR]   location: variable dto of type com.lostfound.lostfound.dto.report.ReportRequest
#14 15.56 [ERROR] /app/src/main/java/com/lostfound/lostfound/service/ReportService.java:[51,31] cannot find symbol
#14 15.56 [ERROR]   symbol:   method getReportType()
#14 15.56 [ERROR]   location: variable dto of type com.lostfound.lostfound.dto.report.ReportRequest
#14 15.56 [ERROR] /app/src/main/java/com/lostfound/lostfound/service/ReportService.java:[54,43] cannot find symbol
#14 15.56 [ERROR]   symbol:   method getReportedById()
#14 15.56 [ERROR]   location: variable dto of type com.lostfound.lostfound.dto.report.ReportRequest
#14 15.56 [ERROR] /app/src/main/java/com/lostfound/lostfound/service/ReportService.java:[56,11] cannot find symbol
#14 15.56 [ERROR]   symbol:   method setUser(com.lostfound.lostfound.model.User)
#14 15.56 [ERROR]   location: variable report of type com.lostfound.lostfound.model.Report
#14 15.56 [ERROR] /app/src/main/java/com/lostfound/lostfound/service/ReportService.java:[59,43] cannot find symbol
#14 15.56 [ERROR]   symbol:   method getReportedItemId()
#14 15.56 [ERROR]   location: variable dto of type com.lostfound.lostfound.dto.report.ReportRequest
#14 15.56 [ERROR] /app/src/main/java/com/lostfound/lostfound/service/ReportService.java:[61,11] cannot find symbol
#14 15.56 [ERROR]   symbol:   method setItem(com.lostfound.lostfound.model.Item)
#14 15.56 [ERROR]   location: variable report of type com.lostfound.lostfound.model.Report
#14 15.56 [ERROR] /app/src/main/java/com/lostfound/lostfound/service/ReportService.java:[93,26] cannot find symbol
#14 15.56 [ERROR]   symbol:   method getMessage()
#14 15.56 [ERROR]   location: variable dto of type com.lostfound.lostfound.dto.report.ReportRequest
#14 15.56 [ERROR] /app/src/main/java/com/lostfound/lostfound/service/ReportService.java:[95,12] cannot find symbol
#14 15.56 [ERROR]   symbol:   method getReportedItemId()
#14 15.56 [ERROR]   location: variable dto of type com.lostfound.lostfound.dto.report.ReportRequest
#14 15.56 [ERROR] /app/src/main/java/com/lostfound/lostfound/service/ReportService.java:[96,48] cannot find symbol
#14 15.56 [ERROR]   symbol:   method getReportedItemId()
#14 15.56 [ERROR]   location: variable dto of type com.lostfound.lostfound.dto.report.ReportRequest
#14 15.56 [ERROR] /app/src/main/java/com/lostfound/lostfound/service/ReportService.java:[98,15] cannot find symbol
#14 15.56 [ERROR]   symbol:   method setItem(com.lostfound.lostfound.model.Item)
#14 15.56 [ERROR]   location: variable report of type com.lostfound.lostfound.model.Report
#14 15.56 [ERROR] -> [Help 1]
#14 15.56 [ERROR] 
#14 15.56 [ERROR] To see the full stack trace of the errors, re-run Maven with the -e switch.
#14 15.56 [ERROR] Re-run Maven using the -X switch to enable full debug logging.
#14 15.56 [ERROR] 
#14 15.56 [ERROR] For more information about the errors and possible solutions, please read the following articles:
#14 15.56 [ERROR] [Help 1] http://cwiki.apache.org/confluence/display/MAVEN/MojoFailureException
#14 ERROR: process "/bin/sh -c mvn clean package -DskipTests" did not complete successfully: exit code: 1
------
 > [build 5/5] RUN mvn clean package -DskipTests:
15.56 [ERROR] /app/src/main/java/com/lostfound/lostfound/service/ReportService.java:[98,15] cannot find symbol
15.56 [ERROR]   symbol:   method setItem(com.lostfound.lostfound.model.Item)
15.56 [ERROR]   location: variable report of type com.lostfound.lostfound.model.Report
15.56 [ERROR] -> [Help 1]

