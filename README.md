# Cinema Booking System (Backend)

The Cinema Booking System is a React/ Java Spring/ MySQL-based ticket reservation system allowing bookings for movie in a few easy steps.  

It allows users to browse movies, book tickets, and manage their reservations. 

At the same time, it allows admin to manage users, movie details and customer orders. 

This backend repository contains the server-side code for the application using Java Spring.

## Table of Contents

- [Features](#features)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Project Structure](#project-structure)

## Features
### User :
- Browse movies, view movie details and search for a movie.
- User signup and login. (Using JWT token and Spring Security)
- Book movie tickets and view reservations.
- Cancel orders.
- Reset Password.

### Admin :
- #### Movie management :
  - Add movies with specific sessions and seats.
  - Edit Movies.
  - Delete Movies.
- #### Order management :
  - Filter all the orders with movie title, order date, user Id, total price, etc.
  - Cancel, confirm or complete the order.
- #### User management :
  - Block user accounts.
  - Edit users information such as password, username and phone number.
  - View all the users information and filter with username, create date, or email.

## Prerequisites

Before you begin, ensure you have met the following requirements:

- Java installed on your machine.
- A Frontend for the Cinema Booking System. (Please refer to `https://github.com/naya0000/Cinema-Booking-System-Frontend`)
- A movie_project.sql file connected to the server.

## Getting Started

1. Clone the repository:

   ```bash
   git clone https://github.com/naya0000/Cinema-Booking-System-Backend.git
2. Navigate to the project directory:
   ```bash
   cd Cinema-Booking-System-Backend
3. Start the development server:
   ```bash
   press `run application`
4. Start the frontend server at the same time
   
5. Open your browser and visit http://localhost:3000/cinemas

## Project Structure

- `src/`: Contains the source code for the application.
  - `main/`
    - `Java/`
      - `com.example.demo/`
        - `controller`
          - `MovieController/`: Add, edit, delete and search for a movie.
          - `RoleController/`: Two roles __ User and Admin
          - `SeatController/`: Create, edit and delete seats for a movie session.
          - `SessionController/`: Create, edit and delete movie sessions.
          - `OrderController/`: Manage client orders.
          - `UserController/`: Manage clients.
          - `AuthController/`: User login, sign up and admin login by JWT token.
        - `security`
          - `JwtAuthenticationFilter/`: Authenticate user authorities in `doFilterInternal`.
          - `JWTService/`: Generate and parse login token.
          - `SpringUser/`: Override `getAuthorities`, `getPassword`, ``
          - `SpringUserService/`
        - `model`
          - `Movie/`
          - `CustomerOrder/`
          - `User/`
          - `Role/`
          - `Seat/`
          - `Session/`
        - `service`
          - `MovieService/`
          - `OrderService/`
          - `UserService/`
          - `RoleService/`
          - `SeatService/`
          - `SessionService/`
          - `AuthService/`
       
          

