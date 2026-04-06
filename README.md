# Setup Instructions
## Option 1: Run with Docker (Recommended)
1. Install & run Docker Desktop, Xming
2. Start the application
docker compose up --build

## Option 2: Run locally (without Docker)
1. Install requirements
- Java 21
- Maven
- MariaDB
2. Create database

- Run the SQL from: init.sql

3. Configure database

- Edit: src/main/resources/db.properties

- Example:

db.url=jdbc:mariadb://localhost:3306/fuel_calculator_localization
db.user=root
db.password=your_password
4. Run the app
mvn clean javafx:run
