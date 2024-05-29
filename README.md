# Gym Management Applicaiton API

## Description
A Spring Boot-based REST API designed for managing gym operations. This application supports functionalities such as user registration and login, password management, training enrollment, and user profile management. Built using Spring Data JPA with Hibernate for efficient data management, it incorporates Spring Security for secure access and protects user data with token-based authentication and password hashing.

## Features
- **User Management**: Register as Trainee or Trainer, login, update information, list all trainings trainee or trainer is enrolled in, and activate/deactivate users.
- **Authentication and Authorization**: Handle user authentication using token-based methods and manage roles and permissions.
- **Training Management**: List all training types, add new trainings, and manage enrollments.
- **Health Indicators and Metrics**: Monitor application health and performance metrics using Prometheus.

## Technologies
- **Spring Boot**: Framework for creating stand-alone, production-grade Spring-based Applications.
- **Spring Data JPA**: Simplifies data access operations by reducing boilerplate code.
- **Hibernate**: Object-relational mapping framework for the Java language.
- **Spring Security**: Provides authentication and authorization support.
- **MySQL**: Database for storing application data.
- **Prometheus**: Monitoring system and time series database.
- **JUnit**: Framework for writing and running repeatable tests.

## Database Schema
![image](https://github.com/Mariam-Katamashvili/Gym-Application/assets/127763064/e201bc01-21d4-48cd-9b42-1c8780dfa6a1)

## Getting Started

### Prerequisites
- Java 11 or higher
- Maven
- MySQL Database

### Installation
1. **Clone the repository:**
   ```bash
   git clone https://github.com/Mariam-Katamashvili/Gym-Application.git

2. **Navigate to the project directory:**
   ```bash
   cd Gym-Application
   
3. **Set up the database:**
    - Create a database in your SQL server.
   - Update `application.properties` with your database credentials and URL.

4. **Build the project:**
   ```bash
   mvn clean install

5. **Run the application:**
   ```bash
   mvn spring-boot:run

## Usage
After starting the application, you can interact with it using HTTP requests. Consider using tools like Swagger or Postman for testing the API endpoints.

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change. Ensure to update tests as appropriate.

## License
This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.
