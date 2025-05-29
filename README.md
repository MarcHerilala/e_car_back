# E-Car Market Backend API

This is the backend API for the E-Car Market application, built with Spring Boot. It provides endpoints for managing cars, appointments, users, and authentication.

## Live API

- **API URL**: https://e-car-back.onrender.com/
- **API Documentation**: https://e-car-back.onrender.com/swagger-ui/index.html

*Note: Initial load may take a few moments due to cold start on the free tier.*

## Features

- 🚗 Car Management
  - List, create, update, and delete cars
  - Filter cars by brand, model, and features
  - Image upload support via AWS S3
  
- 📅 Appointment System
  - Schedule test drives
  - Manage appointment status
  - Email notifications
  
- 👤 User Management
  - User registration and authentication
  - Role-based access control (Admin/Client)
  - Google OAuth2 integration
  
- 🔐 Security
  - JWT-based authentication
  - Protected endpoints
  - CORS configuration

## Tech Stack

- Java 17
- Spring Boot 3.2.5
- Spring Security
- Spring Data JPA
- PostgreSQL
- AWS S3 for image storage
- JWT for authentication
- Google OAuth2
- Maven
- Docker

## API Documentation

The API is documented using OpenAPI (Swagger). When running locally, access the documentation at:
```
http://localhost:8080/swagger-ui/index.html
```

For the live version, visit:
```
https://e-car-back.onrender.com/swagger-ui/index.html
```

## Environment Variables

The following environment variables are required:

```properties
DB_URL=your_database_url
DB_USERNAME=your_database_username
DB_PASSWORD=your_database_password
AWS_ACCESS_KEY=your_aws_access_key
AWS_SECRET_KEY=your_aws_secret_key
AWS_S3_BUCKET=your_s3_bucket_name
MAIL_PWD=your_email_password
GOOGLE_CLIENT_ID=your_google_client_id
```

## Running with Docker

### Prerequisites
- Docker installed on your machine
- Docker Compose (optional, but recommended)

### Using Docker Compose (Recommended)

1. Create a `.env` file with your environment variables
2. Run the application:
   ```bash
   docker-compose up
   ```

The API will be available at `http://localhost:8080`

### Using Docker directly

1. Build the Docker image:
   ```bash
   docker build -t ecar-backend .
   ```

2. Run the container:
   ```bash
   docker run -p 8080:8080 \
     --env-file .env \
     ecar-backend
   ```

## Development

### Running Locally with Java

1. Ensure you have Java 17 installed
2. Set up the required environment variables
3. Run the following commands:
   ```bash
   ./mvnw clean install
   ./mvnw spring-boot:run
   ```

The API will be available at `http://localhost:8080`