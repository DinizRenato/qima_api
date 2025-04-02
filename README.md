# Spring Boot API

This is a Spring Boot-based REST API that manages categories, products, and users with authentication and authorization.

## Technologies Used
- Java 17
- Spring Boot 3.4.4
- Spring Data JPA
- H2 Database (for development and testing)
- Spring Security
- Swagger (SpringDoc OpenAPI)

## Project Structure
```
/src/main/java/com/qima/tech
├── config        # Configuration classes (Security, Swagger, etc.)
├── entities      # JPA Entities (Category, Product, User)
├── repositories  # Spring Data JPA Repositories
├── services      # Business logic and service layer
├── resources     # REST Controllers
├── init          # Database seeders (AdminInitializer, CategorySeeder)
```

## Database Configuration (H2)
The application uses an in-memory H2 database by default. Access the H2 console:
- **URL:** `http://localhost:8080/h2-console`
- **JDBC URL:** `jdbc:h2:mem:testdb`
- **Username:** `sa`
- **Password:** (empty by default)

If the H2 console does not load due to security restrictions, update `application.properties`:
```
spring.h2.console.enabled=true
spring.h2.console.settings.web-allow-others=true
```

## Running the Project
To run the project locally:
```sh
mvn spring-boot:run
```

## API Endpoints
### Authentication
- `POST /api/auth/login` - Authenticate and receive a JWT token

### Users
- `GET /api/users` - List all users (Admin only)
- `POST /api/users` - Create a new user
- `PUT /api/users/{id}` - Update user information
- `POST /api/users/{id}/roles` - Assign roles to a user

### Categories
- `GET /api/categories` - List all categories
- `POST /api/categories` - Create a new category (Admin only)
- `GET /api/categories/{id}` - Get category details
- `GET /api/categories/{id}/products` - Get category with products

### Products
- `GET /api/products` - List all products
- `POST /api/products` - Create a new product (Admin only)
- `PUT /api/products/{id}` - Update product details
- `DELETE /api/products/{id}` - Delete a product (Admin only)

## Swagger API Documentation
After running the application, access Swagger UI at:
- **URL:** `http://localhost:8080/swagger-ui.html`

## Running Tests
To execute unit tests:
```sh
mvn test
```

## Contact
For questions or contributions, feel free to open an issue or submit a pull request!

