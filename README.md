# Movie Reservation System API

## Overview

### The Movie Reservation System API provides the following functionalities:

- **User Registration & Authentication:** Secure user signup and login using JWT
- **Role-Based Access Control:** Differentiate between admin and regular users.
- **Movie Management:** Manage movies and their showtimes.
- **Seat Reservation:** Reserve and release seats for specific showtimes.
- **Reservation Cancellation:** Cancel reservations with proper seat release and timestamping.

## Diagrams

### Context

```mermaid
C4Context
    Person(user, "User", "A user of the movie reservation system.")
    Person(admin, "Admin", "Administrator managing movies and showtimes.")

    System(movieReservationSystem, "Movie Reservation System API", "Allows users to book and manage movie reservations.")

    System_Ext(database, "PostgreSQL Database", "Stores all the data related to users, movies, showtimes, seats, and reservations.")

    Rel(user, movieReservationSystem, "Uses")
    Rel(admin, movieReservationSystem, "Manages")
    Rel(movieReservationSystem, database, "Reads from and writes to")

```

### ERD

```mermaid
erDiagram
    ROLE {
        BIGINT id PK
        VARCHAR name
    }
    
    USER_ACCOUNT {
        BIGINT id PK
        VARCHAR username
        VARCHAR password
        VARCHAR email
        BIGINT role_id FK
    }
    
    MOVIE {
        BIGINT id PK
        VARCHAR title
        TEXT description
        VARCHAR genre
        VARCHAR poster_image
    }
    
    SHOWTIME {
        BIGINT id PK
        TIMESTAMP start_time
        TIMESTAMP end_time
        BIGINT movie_id FK
    }
    
    SEAT {
        BIGINT id PK
        VARCHAR seat_number
        BOOLEAN reserved
        BIGINT showtime_id FK
    }
    
    RESERVATION {
        BIGINT id PK
        BIGINT user_id FK
        BIGINT showtime_id FK
        TIMESTAMP reservation_time
        TIMESTAMP cancelled_at
    }
    
    RESERVATION_SEAT {
        BIGINT reservation_id PK, FK
        BIGINT seat_id PK, FK
    }
    
    ROLE ||--o{ USER_ACCOUNT : "has"
    USER_ACCOUNT ||--o{ RESERVATION : "makes"
    MOVIE ||--o{ SHOWTIME : "has"
    SHOWTIME ||--o{ SEAT : "has"
    RESERVATION ||--o{ RESERVATION_SEAT : "includes"
    SEAT ||--o{ RESERVATION_SEAT : "included_in"
```