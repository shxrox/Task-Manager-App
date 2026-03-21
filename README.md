# 📝 Task Manager Full-Stack Application

## 🚀 Overview
This project is a full-stack **Task Manager Web Application** built using **Spring Boot** (backend) and **Angular** (frontend). It enables users to perform complete **CRUD operations** (Create, Read, Update, Delete) on tasks stored in a **MySQL or MongoDB database**.

The application demonstrates modern development practices including **JWT-based authentication** for security and **Docker Compose** for containerized deployment.

---

## 🛠️ Tech Stack

### 🔙 Backend (Spring Boot)
- Java 17+
- Spring Boot (RESTful API)
- Spring Data JPA (MySQL) / Spring Data MongoDB
- Spring Security with JWT Authentication
- Maven / Gradle

### 🎨 Frontend (Angular)
- Angular 14+
- Reactive Forms
- Angular Routing
- HttpClient
- Angular Material / Bootstrap

---

## ✨ Features

### 📌 Task Management
- **View Tasks** – Display tasks in table or card format
- **Filter Tasks** – Filter by status (TO_DO, IN_PROGRESS, DONE)
- **Create / Edit Tasks** – Using reactive forms
- **Task Details** – View full task information (optional)
- **Validation** – Required title with optional constraints

### 🔐 Authentication & Security
- **User Registration**
- **User Login (JWT-based authentication)**
- **Protected API Routes**
- **JWT Interceptor** – Automatically attaches token to requests
- **UI State Handling** – Show/hide content based on login status

---

## 📁 Project Structure
```
Task-Manager-App/
├── backend/            # Spring Boot REST API
│   ├── src/
│   ├── pom.xml
│   └── Dockerfile
├── frontend/           # Angular UI
│   ├── src/
│   ├── package.json
│   └── Dockerfile
├── docker-compose.yml  # Multi-container setup
└── README.md
```

---

## ⚙️ Setup & Installation

### 🐳 Option 1: Run with Docker (Recommended)

Make sure you have **Docker** and **Docker Compose** installed.

Run the following command in the project root:
```
docker-compose up --build
```

This will start:
- Database (MySQL / MongoDB)
- Spring Boot backend
- Angular frontend

---

### 🧑‍💻 Option 2: Manual Setup

#### 1️⃣ Database Setup
- Install MySQL or MongoDB
- Create a database
- Update `application.properties` with your DB credentials

#### 2️⃣ Backend Setup
```
cd backend
mvn spring-boot:run
```

Backend will run at:
```
http://localhost:8080
```

#### 3️⃣ Frontend Setup
```
cd frontend
npm install
ng serve
```

Frontend will run at:
```
http://localhost:4200
```

---

## 🔑 Authentication Details

- **Default Username:** admin
- **Default Password:** password123
- **Token Storage:** Local Storage / Session Storage

---

## 📊 API Endpoints

| Method | Endpoint              | Description              |
|--------|---------------------|--------------------------|
| POST   | /api/auth/register  | Register new user        |
| POST   | /api/auth/login     | Login and get JWT        |
| GET    | /api/tasks          | Get all tasks            |
| GET    | /api/tasks/{id}     | Get task by ID           |
| POST   | /api/tasks          | Create task              |
| PUT    | /api/tasks/{id}     | Update task              |
| DELETE | /api/tasks/{id}     | Delete task              |

---

## 🛠️ Evaluation Checklist

### ✅ Angular
- Reactive Forms implemented
- Routing configured
- Validation applied
- HttpClient integrated

### ✅ Spring Boot
- Layered architecture (Controller, Service, Repository)
- REST API design followed

### ✅ Database
- Proper entity/schema design
- JPA or MongoDB configuration

### ✅ Security
- JWT authentication flow implemented
- Protected routes working

### ✅ Docker
- Dockerfiles for frontend & backend
- docker-compose working correctly

---

## 📌 Future Improvements
- Add task deadlines and reminders
- Implement role-based access control
- Add pagination and search
- Improve UI/UX design

---

## 📄 License
This project is for educational purposes.

---

## 👨‍💻 Author
Developed as a full-stack project using Spring Boot and Angular.

