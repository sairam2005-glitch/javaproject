# 💰 Wallet Application

A full-stack digital wallet application built with **Java Spring Boot** backend and **vanilla HTML/CSS/JavaScript** frontend. Users can register, log in, check their balance, and send money to other users.

---

## ✨ Features

- **User Registration** — Sign up with email & password, receive ₹1000 starting balance
- **User Login** — Authenticate with email and password
- **Balance Dashboard** — View current wallet balance in real-time
- **Money Transfer** — Send money to any registered user
- **Error Handling** — Prevents self-transfers, insufficient balance, duplicate accounts, and invalid credentials
- **Transactional Safety** — Money transfers use `@Transactional` for atomicity

---

## 🛠️ Tech Stack

| Layer | Technology |
|-------|------------|
| **Backend** | Java 17+, Spring Boot 3.2.5 |
| **Database** | H2 (in-memory relational DB) |
| **ORM** | Spring Data JPA / Hibernate |
| **Frontend** | HTML5, CSS3, Vanilla JavaScript |
| **API Format** | RESTful JSON |
| **Build Tool** | Maven 3.9.6 (wrapper included) |

---

## 📁 Project Structure

```
Wallet/
├── README.md
├── backend/
│   ├── pom.xml
│   ├── mvnw.cmd
│   ├── .mvn/wrapper/
│   └── src/main/
│       ├── java/com/wallet/
│       │   ├── WalletApplication.java          # Spring Boot entry point
│       │   ├── model/
│       │   │   └── User.java                   # JPA Entity (id, email, password, balance)
│       │   ├── repository/
│       │   │   └── UserRepository.java         # Spring Data JPA Repository
│       │   ├── service/
│       │   │   └── WalletService.java          # Business logic with @Transactional
│       │   ├── controller/
│       │   │   └── WalletController.java       # REST API endpoints
│       │   └── config/
│       │       └── WebConfig.java              # CORS configuration
│       └── resources/
│           ├── application.properties          # H2 & JPA configuration
│           └── static/                         # Frontend served by Spring Boot
│               ├── login.html
│               ├── dashboard.html
│               ├── send.html
│               └── style.css
└── frontend/                                   # Standalone frontend files
    ├── login.html
    ├── dashboard.html
    ├── send.html
    └── style.css
```

---

## 🚀 Getting Started

### Prerequisites

- **Java 17** or higher installed ([Download](https://adoptium.net/))
- No Maven installation needed — Maven wrapper is included

### Run the Application

```bash
# Clone the repository
git clone https://github.com/atharv01h/Wallet-App.git
cd wallet-app/backend

# Start the Spring Boot server (Windows)
.\mvnw.cmd spring-boot:run

# Start the Spring Boot server (Linux/Mac)
./mvnw spring-boot:run
```

> **Note:** First run downloads all dependencies and may take a few minutes. Subsequent runs are much faster.

### Open in Browser

Navigate to: **http://localhost:8080/login.html**

---

## 📡 API Endpoints

| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| `POST` | `/api/register` | Register a new user | `{ "email": "...", "password": "..." }` |
| `POST` | `/api/login` | Login with credentials | `{ "email": "...", "password": "..." }` |
| `GET` | `/api/balance?email=...` | Get wallet balance | — |
| `POST` | `/api/send` | Transfer money | `{ "fromEmail": "...", "toEmail": "...", "amount": 100 }` |

### Example API Usage (cURL)

```bash
# Register
curl -X POST http://localhost:8080/api/register \
  -H "Content-Type: application/json" \
  -d '{"email":"alice@wallet.com","password":"pass123"}'

# Login
curl -X POST http://localhost:8080/api/login \
  -H "Content-Type: application/json" \
  -d '{"email":"alice@wallet.com","password":"pass123"}'

# Check Balance
curl http://localhost:8080/api/balance?email=alice@wallet.com

# Send Money
curl -X POST http://localhost:8080/api/send \
  -H "Content-Type: application/json" \
  -d '{"fromEmail":"alice@wallet.com","toEmail":"bob@wallet.com","amount":250}'
```

---

## 🖥️ Application Pages

### Login / Register
- Enter email and password
- Click **"Create Account"** to register (₹1000 default balance)
- Click **"Sign In"** to log in

### Dashboard
- View your current wallet balance
- Navigate to send money or logout
- Refresh balance button for real-time updates

### Send Money
- Enter recipient's email and amount
- Submit to transfer funds instantly
- Error messages shown for invalid operations

---

## ⚙️ Business Rules

| Rule | Description |
|------|-------------|
| Default Balance | New users receive ₹1000 on registration |
| Self-Transfer | Cannot send money to yourself |
| Insufficient Funds | Cannot send more than your available balance |
| Duplicate Email | Cannot register with an already-used email |
| Positive Amount | Transfer amount must be greater than zero |

---

## 🗄️ Database

The app uses **H2 in-memory database** — all data resets on server restart.

- **H2 Console**: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:walletdb`
  - Username: `sa`
  - Password: *(leave empty)*

---

## 🏗️ Architecture

```
┌─────────────┐     HTTP/JSON     ┌──────────────────────────────────────┐
│   Frontend   │ ◄──────────────► │           Spring Boot Backend        │
│  (HTML/JS)   │                  │                                      │
│              │                  │  Controller → Service → Repository   │
│  login.html  │                  │      ↓           ↓          ↓        │
│  dashboard   │                  │  REST API    Business    JPA/H2 DB   │
│  send.html   │                  │              Logic                   │
└─────────────┘                  └──────────────────────────────────────┘
```

---

## 📝 License

This project is open source and available for educational purposes.

---

## 👨‍💻 Created By

**Atharv Hatwar**
