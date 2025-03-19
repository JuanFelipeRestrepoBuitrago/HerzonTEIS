# HERZON Jewelry E-Commerce

![HERZON LOGO](https://github.com/user-attachments/assets/40f1fc9d-9b01-43c0-8bba-2fd99369c31f)

HERZON is a high-end jewelry e-commerce platform bridging Colombian craftsmanship with Swiss elegance. Built with Spring Boot 3.4.2.

Visit the live demo at [Herzon](https://herzonteis-1.onrender.com/)

## Features
- User authentication (Admin/Customer)
- Jewelry catalog
- Live auctions
- Shopping cart & wishlist
- Product reviews
- Admin CRUD for products

## Prerequisites
- Java 21
- Docker 20.0.1 (optional)
- Maven 3.9.9 (optional)
- PostgreSQL 16 (optional)

## Setup
1. Clone repository:
```bash
git clone https://github.com/JuanFelipeRestrepoBuitrago/HerzonTEIS.git
```
2. Run PostgreSQL server with the docker-compose file at the root of the project:
```bash
docker-compose up -d
```
3. Set the following environment variables in your system:
   Linux or MacOS:
```bash
export DATABASE_URL=jdbc:postgresql://localhost:YOUR_DB_PORT/name_of_your_database
export DATABASE_USER=YOUR_USERNAME
export DATABASE_PASSWORD=YOUR_PASSWORD
```
Windows:
```bash
set DATABASE_URL=jdbc:postgresql://localhost:YOUR_DB_PORT/name_of_your_database
set DATABASE_USER=YOUR_USERNAME
set DATABASE_PASSWORD=YOUR_PASSWORD
```
Replace `YOUR_DB_PORT`, `YOUR_USERNAME`, and `YOUR_PASSWORD` with your PostgreSQL port, username, and password respectively. The docker-compose file sets the default port to `5432`, the default username to `root`, and the default password to `123`.

4. Optionally, you can run the following command to clean and build the project:
```bash
mvn clean install
```
or if you don't have Maven installed:
```bash
./mvnw clean install
```

5. Execute Migrations with Flyway:
```bash
mvn clean flyway:migrate
```
or if you don't have Maven installed:
```bash
./mvnw clean flyway:migrate
```

6. Run the project with Maven:
```bash
mvn spring-boot:run
```
or if you don't have Maven installed:
```bash
./mvnw spring-boot:run
```
7. Access the application at:
```bash
http://localhost:8080
```
8. When you're done, exit the spring-boot application with `Ctrl+C` and stop the MySQL server with:
```bash
docker-compose down
```

## Contributors

- [Juan Felipe Restrepo Buitrago](https://github.com/JuanFelipeRestrepoBuitrago)
- [Kevin Quiroz](https://github.com/KevinQzG)
- [Julián Mejía](https://github.com/julimejia)
