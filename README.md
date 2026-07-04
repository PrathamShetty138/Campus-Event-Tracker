# Campus Event Tracker

A full-stack web application for managing campus events built with Spring Boot, Thymeleaf, and MySQL.

## 🎯 Features

- **User Authentication**: Secure registration and login with Spring Security
- **Event Management**: Create, view, and manage campus events
- **RSVP System**: One-click RSVP with duplicate prevention
- **Smart Reminders**: Automatic email notifications 24 hours before events
- **Search & Filter**: Find events by type, date, and title
- **Responsive Design**: Mobile-friendly interface with Bootstrap 5
- **Role-Based Access**: Secure access control for different user types

## 🛠️ Tech Stack

- **Backend**: Spring Boot 3.2.0 (Java 21)
- **Frontend**: Thymeleaf, HTML, CSS, Bootstrap 5
- **Database**: MySQL with Spring Data JPA
- **Security**: Spring Security with BCrypt password encryption
- **Build Tool**: Maven
- **Email**: Spring Mail for reminder notifications

## 📋 Prerequisites

- Java 21 or higher
- MySQL 8.0 or higher
- Maven 3.6 or higher

## 🚀 Quick Start

### 1. Database Setup
```sql
CREATE DATABASE campus_events;
```

### 2. Configure Database
Update `src/main/resources/application.properties`:
```properties
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 3. Run Application
```bash
mvn spring-boot:run
```

### 4. Access Application
Open your browser to: `http://localhost:8080`

## 📱 Pages Available

1. **Homepage** (`/`) - Browse and filter upcoming events
2. **Login** (`/login`) - User authentication
3. **Register** (`/register`) - New user registration
4. **Dashboard** (`/dashboard`) - View your RSVP'd events
5. **Create Event** (`/events/create`) - Create new events
6. **Event Details** (`/events/{id}`) - View event details and RSVP
7. **About** (`/about`) - About the application
8. **Contact** (`/contact`) - Contact information
9. **Error Pages** (`/error/403`, `/error/404`) - Error handling

## 🗄️ Database Schema

### Users Table
- `id` (Primary Key)
- `name` (User's full name)
- `email` (Unique, used for login)
- `password` (BCrypt encrypted)
- `role` (USER/ADMIN)

### Events Table
- `id` (Primary Key)
- `title` (Event name)
- `type` (Sports/Cultural/Workshop/Academic)
- `date` (Event date)
- `time` (Event time)
- `location` (Event location)
- `description` (Event details)
- `created_by` (Foreign Key to Users)

### RSVPs Table
- `id` (Primary Key)
- `event_id` (Foreign Key to Events)
- `user_id` (Foreign Key to Users)
- `timestamp` (When RSVP was made)
- Unique constraint on (event_id, user_id)

### Reminders Table
- `id` (Primary Key)
- `rsvp_id` (Foreign Key to RSVPs)
- `reminder_time` (When to send reminder)
- `sent` (Boolean flag)
- `hours_before` (Hours before event, default: 24)

## 🔔 Reminder System

The application includes an automatic reminder system:

- **Automatic Creation**: Reminders are created when users RSVP
- **Email Notifications**: Sent 24 hours before events
- **Scheduled Tasks**: Background process checks every 5 minutes
- **Smart Cleanup**: Reminders deleted when RSVPs are cancelled

### Email Configuration

For production, configure email in `application.properties`:
```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
```

For development, reminders are logged to console.

## 🎨 UI Features

- **Modern Design**: Gradient backgrounds and smooth animations
- **Responsive Layout**: Works on desktop, tablet, and mobile
- **Interactive Elements**: Hover effects and transitions
- **Bootstrap Icons**: Comprehensive icon set
- **Glass Morphism**: Modern frosted glass effects

## 🔒 Security Features

- **Password Encryption**: BCrypt hashing
- **CSRF Protection**: Built-in Spring Security protection
- **SQL Injection Prevention**: JPA parameterized queries
- **XSS Protection**: Thymeleaf automatic escaping
- **Session Management**: Secure session handling

## 📊 Event Types

- **Sports** ⚽ - Athletic events and competitions
- **Cultural** 🎭 - Arts, music, and cultural programs
- **Workshop** 💡 - Educational and skill-building sessions
- **Academic** 📚 - Lectures, seminars, and academic events

## 🚀 Deployment

### Development
```bash
mvn spring-boot:run
```

### Production
```bash
mvn clean package
java -jar target/event-tracker-1.0.0.jar
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## 📄 License

This project is created for educational purposes.

## 📞 Support

For support, email support@campuseventtracker.com or visit our contact page.

---

**Built with ❤️ for campus communities**