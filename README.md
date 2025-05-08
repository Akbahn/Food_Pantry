

# School Food Pantry Management System 🥫

A desktop-based JavaFX application that allows students to request food, volunteers to manage inventory, and admins to oversee operations. This project connects to a MySQL database hosted on Microsoft Azure and follows a modular Java 23+ architecture.

---
## 👥 Team Members

| Name            | Role / Contribution                                     |
|-----------------|-------------------------------------------------------  |
| Hyder Akbar     | Full-Stack Developer, Database Integration, JavaFX UI   |
| Catherine Parveez | [Software Developer, Tech Support, Scribe]            |
| Christopher D'Aleo | [e.g. Admin Dashboard, Analytics]                    |
| [Add Name Here] | [e.g. UX Design, Scene Builder Layouts]                 |
| [Add Name Here] | [e.g. UX Design, Scene Builder Layouts]                 |


## 💡 Features

### 👤 Student
- Log in with credentials
- Browse available food items by category
- Add items to cart and request them
- View request history and pending requests
- All requests are tracked with status and timestamp

### 🙋 Volunteer
- Log in with credentials
- View and approve/deny student food requests
- Manage inventory (add, remove, update items)
- View real-time pending requests

### 🛡️ Admin (Planned)
- Full control over users, inventory, and requests
- View reports and analytics (coming soon)
- Manage volunteer shifts and alert settings (planned)

---

## 🧱 Tech Stack

| Component       | Technology                     |
|----------------|---------------------------------|
| UI             | JavaFX with Scene Builder       |
| Backend        | Java 23 with modular structure  |
| Database       | MySQL hosted on Azure           |
| ORM/DAO        | Custom DAO classes (JDBC)       |
| Build Tool     | Maven                           |
| IDE            | IntelliJ IDEA                   |

---

## 🔐 Authentication

- Login credentials are verified against the `users` table
- Upon login, `CurrentUser` stores session info (ID, name, role, etc.)
- User roles determine which dashboard (student/volunteer/admin) is loaded

---

## 📦 Modules and Packages

- `org.main.food_pantry.Controllers`: All controller logic
- `org.main.food_pantry.Models`: JavaFX model classes for TableViews
- `org.main.food_pantry.Databases`: DAO layer, DB connection, `CurrentUser`
- `org.main.food_pantry.Items`: Food and inventory data classes
- `org.main.food_pantry.Users`: User abstractions and role classes

---

## 🔄 Request Workflow

1. Student checks out cart
2. Request is inserted into the `requests` table
3. Foreign keys validate `user_id` and `food_id`
4. Volunteer views pending requests and approves or denies
5. Inventory updates automatically upon approval

---

## 🔧 Setup Instructions

1. Clone this repo:
   ```bash
   git clone https://github.com/your-username/food-pantry-app.git
   ```
   
## 🛠️ Future Improvements

- 📊 Admin dashboard with charts (e.g., request trends, inventory usage)
- 🧑‍🤝‍🧑 Volunteer shift scheduling and management system
- 📬 Email alerts for low inventory or expiring items
- ✅ Unit tests for DAO methods to improve reliability
=======
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



