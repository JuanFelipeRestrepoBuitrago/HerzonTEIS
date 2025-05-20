# HERZON Jewelry E-Commerce

![HERZON LOGO](https://github.com/user-attachments/assets/40f1fc9d-9b01-43c0-8bba-2fd99369c31f)

HERZON is a high-end jewelry e-commerce platform bridging Colombian craftsmanship with Swiss elegance. Built with Spring Boot 3.4.2.

Visit the live demo at [Herzon](https://herzon-teis.duckdns.org)

## Table of Contents

- [HERZON Jewelry E-Commerce](#herzon-jewelry-e-commerce)
  - [Table of Contents](#table-of-contents)
  - [Features](#features)
  - [Prerequisites](#prerequisites)
  - [Setup](#setup)
  - [Setup for Production](#setup-for-production)
  - [Our Deployed Application](#our-deployed-application)
  - [Contributors](#contributors)

## Features
- User authentication (Admin/Customer)
- Jewelry catalog
- Live auctions
- Shopping cart & wishlist
- Product reviews
- Admin CRUD for products
- Generate authenticity certificate as PDF for selected jewels
- Generate order receipt as downloadable PDF

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
docker compose -f ./docker-compose-dev.yml up -d
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
Replace `YOUR_DB_PORT`, `YOUR_USERNAME`, and `YOUR_PASSWORD` with your PostgreSQL port, username, and password respectively. The docker-compose file sets the default port to `5432`, the default username to `root`, and the default password to `123`. If using the docker compose file, you can use the following values:
```bash
DATABASE_URL=jdbc:postgresql://localhost:5432/herzon_db
DATABASE_USERNAME=root
DATABASE_PASSWORD=123
```

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
8. When you're done, exit the spring-boot application with `Ctrl+C` and stop the PostgreSQL server with:
```bash
docker compose -f ./docker-compose-dev.yml down
```

## Setup for Production

### Installing Docker and Docker Compose in all instances

1. Update the package list and install dependencies:
```bash
sudo apt update
sudo apt upgrade -y
sudo apt install -y apt-transport-https ca-certificates curl software-properties-common
```

2. Add Docker's official GPG key:
```bash
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/trusted.gpg.d/docker-archive-keyring.gpg
```
3. Set up the stable repository:
```bash
sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"
```

4. Install Docker Engine:
```bash
sudo apt update && sudo apt install -y docker-ce docker-ce-cli containerd.io
```
5. Start and enable Docker:
```bash
sudo usermod -aG docker $USER && logout && sudo systemctl restart docker
```
6. Verify Docker installation:
```bash
docker --version
```
7. Verify Docker Compose installation:
```bash
docker compose --version
```

### Execution

1. Clone repository:
```bash
git clone https://github.com/JuanFelipeRestrepoBuitrago/HerzonTEIS.git
```
2. Set the environment variables in your system as described in the `.env.example` file. You can create a `.env` file in the root of the project and copy the contents of the `.env.example` file into it. Then, set the values for each variable according to your production environment.
3. Initialize Docker Swarm:
```bash
docker swarm init
```
4. Connect the Docker Swarm Manager to a Docker Swarm Worker:
```bash
docker swarm join --token generated_token_by_swarm_init ip_connected_by_swarm_init:2377
```
5. Run the project with docker compose:
```bash
docker stack deploy -c docker-compose.yml herzon-teis 
```
6. Access the Nginx Proxy Manager at:
```bash
http://public_ip_of_vpc:81
```
7. Set up the let's encrypt SSL certificate by following the next steps:
    - Go to the Nginx Proxy Manager dashboard.
    - Click on "SSL Certificates" in the left sidebar.
    - Click on "Add SSL Certificate".
    - Fill the domain names like `your_domain_name.com` and `*.your_domain_name.com`.
    - Fill the Use DNS Challenge section with your DNS provider.
        - Select the DNS provider from the dropdown list.
        - Fill the API key and secret with the credentials of your DNS provider.
        - Fill the propagation time with 120 seconds, if failed, try increasing this time.
    - Accept the terms of service.
    - Click "Save".
8. Set up the proxy hosts.
    - Go to the Nginx Proxy Manager dashboard.
    - Click on "Proxy Hosts" in the left sidebar.
    - Click on "Add Proxy Host".
    - Fill the domain names like `your_domain_name.com` and `*.your_domain_name.com`.
    - Fill the scheme with `http`.
    - Fill the forward hostname/IP with the IP address of your server if the application is exporting its ports to the server, or `name_of_service_in_docker_compose_file` if the application is running in a docker compose file.
    - Fill the forward port with the desired port of the application.
    - Enable the Websockets Support option.
    - Enable the SSL option.
        - Select the SSL certificate you created in step 7.
        - Enable the Force SSL option.
        - Enable the HTTP/2 Support option.
    - Click "Save".
9. Access the application at:
```bash
https://your_domain_name.com
```
10. When you're done, stop the Docker Swarm with:
```bash
docker stack rm herzon-teis
```

## Our Deployed Application

You can access our application at [HERZON](https://herzon-teis.duckdns.org) or with the domain name `herzon-teis.duckdns.org`. The application is running on a virtual private server (VPS) with the following specifications:
- **Provider**: AWS
- **Instance Type**: t3.medium
- **CPU**: 2 vCPUs
- **RAM**: 4 GB
- **Docker Swarm**: 2 nodes (1 manager and 1 worker)

**Note**: if you want to log in as an admin, use the following credentials: 
- **Username**: admin
- **Password**: 123

## Contributors

- [Juan Felipe Restrepo Buitrago](https://github.com/JuanFelipeRestrepoBuitrago)
- [Kevin Quiroz](https://github.com/KevinQzG)
- [Julián Mejía](https://github.com/julimejia)
