
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