# Project Documentation

## Copilot Autofix & Dependabot Demo

This is a Spring Boot application demonstrating GitHub's Dependabot and Copilot Autofix capabilities.

### Features

- **Spring Boot REST API** with CRUD operations
- **JPA/Hibernate** for data persistence
- **H2 In-Memory Database** for local testing
- **Deliberately Vulnerable Dependencies** for testing security scanning

### Vulnerable Dependencies (For Testing)

This project intentionally includes vulnerable dependencies to test Dependabot and GitHub Autofix:

1. **Log4j 2.14.1** - Contains CVE-2021-44228 (Log4Shell)
2. **Spring Core 5.3.0** - Contains CVE-2021-22118

⚠️ **WARNING**: These vulnerabilities are intentional for demonstration purposes. DO NOT use in production!

### API Endpoints

#### Users API

- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get user by ID
- `GET /api/users/email/{email}` - Get user by email
- `POST /api/users` - Create new user
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user

### Running the Application

```bash
# Using Gradle wrapper
.\gradlew.bat bootRun

# Or build and run
.\gradlew.bat build
java -jar build/libs/copilot-autofix-demo-0.0.1-SNAPSHOT.jar
```

### H2 Database Console

Access the H2 console at: http://localhost:8080/h2-console

- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (leave blank)

### Testing Dependabot

1. Push this code to GitHub
2. Dependabot will automatically detect the vulnerable dependencies
3. It will create pull requests to update them
4. GitHub Copilot Autofix may suggest automatic fixes

### Example API Usage

```bash
# Create a user
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"name":"John Doe","email":"john@example.com","phone":"1234567890","address":"123 Main St"}'

# Get all users
curl http://localhost:8080/api/users

# Get user by ID
curl http://localhost:8080/api/users/1

# Update user
curl -X PUT http://localhost:8080/api/users/1 \
  -H "Content-Type: application/json" \
  -d '{"name":"John Updated","email":"john@example.com","phone":"9876543210","address":"456 Oak Ave"}'

# Delete user
curl -X DELETE http://localhost:8080/api/users/1
```

### Security Scanning

This project is configured with Snyk for security scanning. The Snyk rules will automatically scan new code for vulnerabilities.
