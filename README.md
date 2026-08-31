# Sunrise Dental Clinic Management System

Sunrise Dental Clinic Management System is a Java web application developed for the CIS6003 Advanced Programming assignment.

The system was created to replace the clinic’s manual paper-based process for patient records, appointments and billing.

## Features

- User login and logout
- Patient registration
- Patient search, update and delete
- NIC validation
- Appointment registration
- Appointment search by appointment number
- Multiple treatment selection
- Double-booking prevention
- Appointment update and cancellation
- Treatment-based billing
- Invoice generation
- Invoice viewing and printing
- Payment status handling
- Appointment reports
- Staff/User management
- Help section
- Appointment JSON API
- Session-based access control
- BCrypt password hashing

## Technologies

- Java 17
- JSP
- Jakarta Servlets
- JDBC
- MySQL
- Apache Tomcat 10.1
- Maven
- Bootstrap 5
- SweetAlert2
- JUnit 5
- BCrypt
- Git
- GitHub

## Architecture

The system follows a layered structure:

JSP / View  
↓  
Servlet / Controller  
↓  
Service Layer  
↓  
DAO Layer  
↓  
MySQL Database  

The project also follows an MVC-style structure.

- Model - Patient, Appointment, Invoice and User
- View - JSP pages
- Controller - Servlet classes

## Design Patterns

The following design patterns are used in the project:

- MVC-style Pattern
- DAO Pattern
- Service Layer Pattern
- Constructor Injection
- Intercepting Filter Pattern
- Post/Redirect/Get Pattern

## OOP Concepts

The project uses:

- Encapsulation
- Abstraction
- Inheritance
- Polymorphism
- Association

## Database

The system uses MySQL.

Main tables:

- users
- patients
- appointments
- invoices

## Security

The system includes:

- BCrypt password hashing
- Session-based login
- AuthenticationFilter
- Administrator and User roles
- Restricted database user
- Input validation

## Testing

JUnit tests are included for service and DAO layers.

Main test classes:

- PatientServiceTest
- AppointmentServiceTest
- InvoiceServiceTest
- UserServiceTest
- PatientDAOTest
- AppointmentDAOTest

Run tests using:

mvn test

Manual testing was also carried out for the main system functions.

## Git Workflow

The project uses two main branches:

dev → Pull Request → master

The dev branch is used for development changes.  
After testing, changes are pushed to GitHub and merged into master using a Pull Request.

## Project Setup

1. Install Java 17
2. Install MySQL
3. Install Apache Tomcat 10.1
4. Create the sunrise_dental_db database
5. Configure the database connection
6. Build the project using Maven
7. Run the project using Tomcat

Build command:

mvn clean package

## Main Project Structure

src/main/java/com/dental/system/

- model
- dao
- service
- servlet
- util
- Filter

src/main/webapp/

- JSP pages

src/test/java/com/dental/system/

- service tests
- DAO tests

## Future Improvements

- Separate treatment tables
- Dentist schedule management
- Email or SMS reminders
- Online payments
- Audit logs
- Database connection pooling
- Token-based API authentication
- Automated UI testing
- CI/CD workflow

## Author

S.M. Dinindu Madhuwantha Senanayake

## Note

This project was developed for academic purposes.
