# How to Run Campus Event Tracker

## Prerequisites
1. **Java 21** - Make sure Java 21 is installed
2. **MySQL 8.0+** - MySQL must be running
3. **Maven 3.6+** - Maven should be installed

## Step-by-Step Instructions

### 1. Start MySQL Server
Make sure your MySQL server is running on localhost:3306

### 2. Configure Database Credentials
Open `src/main/resources/application.properties` and update:
```properties
spring.datasource.username=root
spring.datasource.password=shetty  # Your MySQL password
```

### 3. Create Database (Optional)
The application will auto-create the database, but you can manually create it:
```sql
CREATE DATABASE campus_events;
```

### 4. Run the Application

**Option A: Using Maven (Recommended)**
```bash
mvn spring-boot:run
```

**Option B: Using the run.bat file**
```bash
.\run.bat
```

### 5. Access the Application
Open your browser and go to:
```
http://localhost:8081
```

## Troubleshooting

### Port Already in Use
If you see "Port 8081 was already in use":

1. Find the process using port 8081:
```bash
netstat -ano | findstr :8081
```

2. Kill the process (replace PID with actual process ID):
```bash
taskkill /F /PID <PID>
```

3. Run the application again

### Database Connection Failed
- Verify MySQL is running
- Check username and password in `application.properties`
- Ensure MySQL is listening on port 3306

### Build Failed
If Maven build fails, try:
```bash
mvn clean install -DskipTests
```

## Default Pages
- Homepage: http://localhost:8081/
- Login: http://localhost:8081/login
- Register: http://localhost:8081/register
- Dashboard: http://localhost:8081/dashboard

## Stopping the Application
Press `Ctrl + C` in the terminal where the application is running
