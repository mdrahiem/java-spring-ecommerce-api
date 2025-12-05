# Ecommerce REST API

## Requirements
- Java 21 or later
- Maven 3.6 or later
- PostgreSQL 12 or later

## Database Setup
1. Ensure the PostgreSQL Docker container is running (Port 5433).
2. The application uses the default `postgres` database.
3. The application connects to the default `postgres` database on port 5433.
   - Url: `jdbc:postgresql://localhost:5433/postgres`
   - Username: `postgres`
   - Password: `postgres`

## Build and Run
1. Navigate to the project directory:
   ```bash
   cd Projects/ecommerce-api
   ```
2. Build the application:
   ```bash
   mvn clean install
   ```
3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

## Endpoints
- `GET /api/products` - Fetch all products
- `GET /api/products/{id}` - Fetch product details
- `GET /api/categories` - Fetch all categories with sub-categories

## Data
The database tables will be automatically created (`ddl-auto=update`). You will need to insert initial data using SQL or a DB tool.

