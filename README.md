# 🍽️ Food Pantry Management System

A JavaFX-based desktop application designed to manage a school's food pantry system. Built with Java, MySQL (hosted on Microsoft Azure), and FXML UI using Scene Builder.

---

## ✅ Current Features

### 📡 Database Integration
- Connected to a **MySQL database hosted on Microsoft Azure**
- Automatically creates the `food_pantry` database and essential tables on first run:
  - `users`
  - `food_items`
  - `requests`

### 👤 User Authentication & Roles
- Fully functional **login system** using `username` and `password`
- Role-based access and scene routing:
  - **Student** → `student-page.fxml`
  - **Volunteer** → `volunteer-page.fxml`
  - **Admin** → `admin-page.fxml`
- **Sign-up functionality** via `create-account-page.fxml`, allowing new users to register and save credentials to the Azure-hosted database

### 🧩 FXML Navigation
- Scene transitions handled via a centralized `SceneManager` class
- FXML views working correctly:
  - `login-page.fxml`
  - `create-account-page.fxml`
  - `student-page.fxml`

### 📦 Inventory System
- `food_items` table created with fields:
  - `name`, `category`, `quantity`, `expiration_date`, and `description`
- Inventory is displayed to students using a JavaFX `TableView` populated directly from the database

---

## 🚀 Tech Stack
- Java 23
- JavaFX 23.0.1
- Scene Builder
- MySQL 8.0 (hosted on Azure)
- Maven

---

## 📌 Next Features (Planned)
- Students can submit food requests from available inventory
- Volunteers can mark requests as "In Progress" or "Fulfilled"
- Admin panel for managing users, requests, and inventory
- Request history per user and filtering options

---


