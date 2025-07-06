# 🎟️ Ticket Manager

A full-stack ticket management system with role-based access control, built with **Spring Boot**, **Hibernate**, **PostgreSQL**, **Next.js**, and **TypeScript**. The system supports admin features, CSV import/export, file storage via **MinIO**, and Docker-based deployment.

<p align="center">
  <img src="screenshots/1.png" alt="Main Window" width="48%" style="margin-right: 2%;" />
  <img src="screenshots/2.png" alt="Conversion Example" width="48%" />
</p>

<p align="center">
  <img src="screenshots/3.png" alt="App Demo" width="600"/>
</p>

---

## 🚀 Features

### 🔐 Authentication & Authorization
- JWT-based authentication
- Registration and login endpoints
- Role-based access: **USER**, **ADMIN**, **SUPER_ADMIN**
- Automatic SUPER_ADMIN account generation on first run with a generated password

### 🛠️ Admin Functionality
- Assign/remove admin role
- Block/unblock users
- View user information and profile picture (stored in MinIO)

### 🎫 Ticket Management
- Create, read, update, and delete tickets
- Admins can edit/delete tickets of users with lower roles
- CSV import/export of ticket collections
- Pagination, sorting, and filtering

### 🧑‍💻 Frontend (Next.js + TypeScript)
- Authentication (Sign In / Sign Up)
- Ticket listing and editing pages
- Admin panel for managing users
- User profile page with avatar upload
- Responsive design using [shadcn/ui](https://ui.shadcn.com/)

### 🐳 Docker Support
- Full setup via Docker and Docker Compose
- Services: Backend, Frontend, PostgreSQL, MinIO

---

## Full Report

📄 See [report.pdf](./лаб8.pdf) for full details.


