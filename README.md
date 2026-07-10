# 📈 TradeWise Backend

TradeWise is a backend REST API for a Stock Portfolio Analytics Platform built using **Spring Boot**. The goal of this project is to simulate a production-ready backend with secure authentication, portfolio management, watchlists, transaction tracking, and stock analytics.

> This project is being built from scratch while following industry best practices in software design, Git workflow, and backend architecture.

---

## 🚀 Features

### ✅ Completed
- User Registration
- User Login
- BCrypt Password Hashing
- Spring Security Configuration
- Global Exception Handling
- DTO-based Architecture
- PostgreSQL Integration

### 🚧 In Progress
- JWT Authentication

### 📅 Planned
- Portfolio Management
- Watchlist
- Transaction History
- Stock Analytics
- Role-Based Authorization
- Docker
- Unit & Integration Testing
- CI/CD Pipeline
- API Documentation (Swagger)

---

## 🛠️ Tech Stack

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- PostgreSQL
- Maven
- JWT
- Git & GitHub

---

## 📂 Project Structure

```
src
├── config
├── controller
├── dto
├── entity
├── exception
├── repository
├── security
└── service
```

---

## ⚙️ Getting Started

### Clone the repository

```bash
git clone https://github.com/rajshinde-45/tradewise-backend.git
```

### Configure application.properties

Copy:

```
application-example.properties
```

to

```
application.properties
```

Update:

- PostgreSQL username
- PostgreSQL password
- JWT Secret

### Run

```bash
mvn spring-boot:run
```

---

## 🗺️ Roadmap

- [x] Project Setup
- [x] Registration API
- [x] Login API
- [x] Password Encryption
- [ ] JWT Authentication
- [ ] Portfolio Module
- [ ] Watchlist Module
- [ ] Transaction Module
- [ ] Stock Analytics
- [ ] Docker
- [ ] Testing
- [ ] CI/CD

---

## 👨‍💻 Author

**Raj Shinde**

Building TradeWise to learn modern backend development using Spring Boot while following real-world software engineering practices.