# Project Documentation: "TimeSlotManager"

## Project Description:

The "TimeSlotManager" project is a web application designed for managing class schedules. It provides the ability to book available time slots and display the schedule of classes.

## Technologies and Tools:

- **Programming Language:** Java
- **ORM Framework:** Hibernate
- **Logging:** Log4J
- **Authentication and Authorization:** Cognito AWS
- **Frontend:** Thymeleaf (HTML templating)
- **Database Management System:** MySQL
- **Framework:** Spring Boot

## Project Components:

### 1. Controllers:

#### a. `BookingController`:

- Handles requests related to booking and displaying schedules.
- Implements methods for displaying the schedule (`showBookingSchedule`) and booking a time slot (`bookTimeSlot`).

#### b. `ScheduleController`:

- Responsible for displaying schedules and lists of booked slots.
- Implements methods for displaying schedules (`showSchedule`) and lists of booked slots (`showAllTimeSlots`).

### 2. Services:

#### a. `BookingService`:

- Provides logic for booking and displaying time slots.
- Interacts with repositories (`TimeSlotRepository`, `BookingRepository`).

### 3. Repositories:

#### a. `BookingRepository`:

- Interface for interacting with the database for the `Booking` entity.

#### b. `TimeSlotRepository`:

- Interface for performing database operations related to the `TimeSlot` entity.

### 4. Entities:

#### a. `TimeSlot`:

- Represents the entity of a time slot.
- Contains information about the day of the week, start time, status, and occupation.

#### b. `Booking`:

- Represents the entity of a booking.
- Associated with a time slot through a "many-to-many" relationship.

## Plans to work on:

1. **Add Cognito Authentication and Authorization:**
    - Integrate with AWS Cognito or a similar service to enhance security and user identification.

2. **Improve the Interface:**
    - Develop a more appealing and user-friendly interface to enhance the user experience.

3. **Work on Unit Tests:**
    - Develop additional unit tests to ensure the stability and reliability of the application.
   
4. ** Deploy on AWS **
    - To deploy this project on AWS BeansTalk and send link to other students

