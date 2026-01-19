# CentralPerk – Backend ☕🍽️

Backend service for the food ordering application built using **Spring Boot**. It provides REST APIs for authentication, menu management, order handling, and admin features.

---

## Features

- User login & registration 🔐
- JWT authentication 🪪
- Menu management 📋
- Order placement 🛒
- Order history 📦
- Admin order management 👨‍💼

---

## Tech Stack

- Java
- Spring Boot
- Spring Security + JWT
- MySQL
- JPA / Hibernate

---

## Setup

```bash
git clone https://github.com/your-username/centralperk-backend.git
cd centralperk-backend
CREATE DATABASE central_perk;

Update application.properties:
  spring.datasource.url=jdbc:mysql://localhost:3306/central_perk
  spring.datasource.username=your_username
  spring.datasource.password=your_password

./mvnw spring-boot:run

