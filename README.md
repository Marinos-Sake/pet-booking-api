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

## Future Improvements

1. **Refine Deletion Logic**  
   Prevent cascading hard-deletes across related entities. Instead, adopt a safer approach (e.g., soft delete) to preserve historical data such as payments, bookings, and reviews.

2. **Expand CRUD Operations**  
   Add missing CRUD endpoints or service methods where necessary to provide more complete functionality.

3. **Optimize Security Context**  
   Avoid loading the entire `User` entity into the security context (due to heavy one-to-one relationships with `Person`). Instead, load only the essential authentication details.

4. **Enhance Error Handling**  
   Improve the global `ErrorHandler` to provide clearer, more consistent, and developer-friendly error responses across the API.

## 📂 Database Schema

Main tables:

- `users` → authentication and roles
- `persons` → personal details
- `pets` → cats or dogs linked to a person
- `rooms` → rooms with price and availability
- `bookings` → reservations with total price calculation
- `payments` → payments linked to bookings with automatic status updates
- `reviews` → user reviews for bookings
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
   git clone git@github.com:Marinos-Sake/pet-booking-api.git

2. Configure environment variables:
   Copy `application.yml.sample` to `application.yml`.

3. Build and start the application:
   `./gradlew bootRun`

4. API will be available at:
   http://localhost:8080


## 📘 API Documentation (Swagger)

The API is documented with Swagger / OpenAPI.

- Swagger UI: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
- OpenAPI JSON: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)


## 🔗 Front-end Repository

This back-end API is connected with the front-end application built in React + Vite + Tailwind.  
You can find it here:  
👉 [Pet Booking Front-end](https://github.com/Marinos-Sake/pet-booking-frontend)




