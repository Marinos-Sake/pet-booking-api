# Pet Booking API 🐾

A full-stack pet booking system where users can schedule and manage reservations for their cats or dogs.

This repository contains the **back-end REST API**, built with **Spring Boot 3.4.7**, and using:

- Java 17
- Spring Web, Spring Data JPA
- Hibernate Validator
- MySQL Database
- Lombok
- JWT Authentication & Authorization

---

## ✨ Features

- User registration, login, JWT authentication
- Manage users and personal profiles
- CRUD for pets (linked to users/persons)
- Manage rooms with types, capacity, availability, and pricing
- Bookings with automatic total price calculation (based on nights × room price)
- Payments linked to bookings with automatic status update (PENDING → CONFIRMED → COMPLETED)
- Reviews for completed bookings
- File uploads for attachments (user profile, pets, etc.)
- Role-based access control (admin vs user)

---

## 📂 Database Schema

Main tables:

- `users` → authentication and roles
- `persons` → personal details
- `pets` → cats or dogs linked to a person
- `rooms` → available rooms with price and availability
- `bookings` → reservations with total price calculation
- `payments` → payments linked to bookings with automatic status updates
- `reviews` → user reviews for completed bookings
- `attachments` → file uploads linked to users

---

## 🚀 Getting Started

### Prerequisites
- Java 17+
- Gradle
- MySQL

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/pet-booking.git
   cd pet-booking

2. Configure environment variables:
   Copy `application.yml.sample` to `application.yml`.

3. Build and start the application:
   `./gradlew bootRun`

4. API will be available at:
   http://localhost:8080

## 🔗 Front-end Repository

This back-end API is connected with the front-end application built in React + Vite + Tailwind.  
You can find it here:  
👉 [Pet Booking Front-end](https://github.com/Marinos-Sake/pet-booking-frontend)




