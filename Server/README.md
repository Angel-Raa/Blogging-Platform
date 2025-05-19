# Secure Blogging API Platform with JWT Authentication

A robust Spring Boot-based RESTful API that provides a secure blogging platform with JWT authentication, user management, and content organization capabilities. The platform enables users to create, manage, and organize blog posts with categories while ensuring secure access through role-based authorization.

The API implements a comprehensive security model using JWT tokens for authentication and authorization, with support for multiple user roles (ADMIN, MODERATOR, USER). It features a clean architecture following REST best practices, with clear separation of concerns between controllers, services, and data access layers. The platform includes robust error handling, input validation, and secure password management with compromised password checking.

## Repository Structure
```
.
├── docker-compose.yaml         # Docker configuration for PostgreSQL database
├── mvnw & mvnw.cmd            # Maven wrapper scripts for build automation
├── pom.xml                    # Maven project configuration and dependencies
└── src/
    ├── main/
    │   ├── java/io/github/angel/raa/
    │   │   ├── Application.java                # Spring Boot application entry point
    │   │   ├── configuration/                  # Security and JWT configuration
    │   │   ├── controller/                     # REST API endpoints
    │   │   ├── dto/                           # Data transfer objects
    │   │   ├── exception/                      # Custom exceptions and handlers
    │   │   ├── persistence/                    # Entity and repository definitions
    │   │   ├── service/                        # Business logic implementation
    │   │   └── utils/                         # Utility classes
    │   └── resources/
    │       └── application.properties          # Application configuration
    └── test/                                   # Test cases
```

## Usage Instructions
### Prerequisites
- JDK 21 or later
- Docker and Docker Compose
- PostgreSQL 15 (provided via Docker)
- Maven 3.9.9 or later (provided via wrapper)

### Installation

1. Clone the repository:
```bash
git clone <repository-url>
cd <repository-name>
```

2. Start the PostgreSQL database:
```bash
docker-compose up -d
```

3. Build and run the application:
```bash
# For Unix-like systems:
./mvnw clean install
./mvnw spring-boot:run

# For Windows:
mvnw.cmd clean install
mvnw.cmd spring-boot:run
```

### Quick Start

1. Register a new user:
```bash
curl -X POST http://localhost:8080/api/v1/authentication/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "user1",
    "password": "securePassword123",
    "email": "user1@example.com",
    "fullName": "John Doe"
  }'
```

2. Login to get JWT token:
```bash
curl -X POST http://localhost:8080/api/v1/authentication/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "user1",
    "email": "user1@example.com",
    "password": "securePassword123"
  }'
```

3. Create a new blog post (authenticated):
```bash
curl -X POST http://localhost:8080/api/v1/posts \
  -H "Authorization: Bearer <your-jwt-token>" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "My First Post",
    "content": "Hello World!"
  }'
```

### More Detailed Examples

1. Managing Categories:
```bash
# Create a new category
curl -X POST http://localhost:8080/api/v1/category \
  -H "Authorization: Bearer <your-jwt-token>" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Technology"
  }'

# Get all categories
curl -X GET http://localhost:8080/api/v1/category \
  -H "Authorization: Bearer <your-jwt-token>"
```

2. Working with Posts:
```bash
# Get all posts (paginated)
curl -X GET "http://localhost:8080/api/v1/posts?page=0&size=10" \
  -H "Authorization: Bearer <your-jwt-token>"

# Get post by slug
curl -X GET http://localhost:8080/api/v1/posts/my-first-post \
  -H "Authorization: Bearer <your-jwt-token>"
```

### Troubleshooting

1. Authentication Issues
- Error: "Invalid JWT token"
  - Verify token hasn't expired (default 24 hours)
  - Ensure token is properly formatted in Authorization header
  - Check if token was issued by this application

2. Database Connection Issues
- Error: "Cannot connect to PostgreSQL"
  - Verify Docker container is running: `docker ps`
  - Check port mapping: `docker-compose ps`
  - Ensure database credentials match application.properties

3. Build Issues
- Error: "Could not find or load main class org.apache.maven.wrapper.MavenWrapperMain"
  - Run: `mvn -N wrapper:wrapper`
  - Verify .mvn directory exists

## Data Flow
The application follows a layered architecture for processing requests:

```ascii
Client Request → Controller → Service → Repository → Database
     ↑                                                 ↓
     └─────────────── Response ←────────────────────────
```

Component interactions:
1. Controllers validate incoming requests and handle HTTP concerns
2. Services implement business logic and transaction management
3. Repositories handle data persistence operations
4. DTOs manage data transfer between layers
5. JWT filter intercepts requests for authentication
6. Global exception handler provides consistent error responses
7. Entity classes map to database tables