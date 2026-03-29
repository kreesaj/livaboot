# Spring Boot Backend - Quick Start Guide

## Prerequisites Check

Before starting, verify you have:

```bash
# Check Java version (need 17+)
java -version

# Check Maven version (need 3.6+)
mvn -version
```

If not installed:
- **Java 17**: Download from https://adoptium.net/
- **Maven**: Download from https://maven.apache.org/download.cgi

## Quick Start (5 Minutes)

### Step 1: Navigate to Project
```bash
cd accounting-backend
```

### Step 2: Build the Project
```bash
mvn clean install
```

Expected output:
```
[INFO] BUILD SUCCESS
[INFO] Total time: XX s
```

### Step 3: Run the Application
```bash
mvn spring-boot:run
```

Expected output:
```
Started AccountingSystemApplication in X.XXX seconds
```

✅ **Backend is now running at: http://localhost:8080**

## Verify Installation

### Test 1: Check Server Status
```bash
curl http://localhost:8080/api/dashboard
```

Should return JSON with dashboard data.

### Test 2: Access H2 Console
1. Open browser: http://localhost:8080/h2-console
2. JDBC URL: `jdbc:h2:mem:accountingdb`
3. Username: `sa`
4. Password: (leave empty)
5. Click "Connect"

### Test 3: Get All Accounts
```bash
curl http://localhost:8080/api/accounts
```

Should return empty array `[]` or seeded data.

## Create Your First Data

### 1. Create Account Head
```bash
curl -X POST http://localhost:8080/api/account-heads \
  -H "Content-Type: application/json" \
  -d '{
    "code": "AH001",
    "name": "Current Assets",
    "type": "ASSET",
    "description": "Short-term assets",
    "isActive": true
  }'
```

### 2. Create Account
```bash
curl -X POST http://localhost:8080/api/accounts \
  -H "Content-Type: application/json" \
  -d '{
    "code": "ACC001",
    "name": "Cash in Hand",
    "accountHeadId": 1,
    "openingBalance": 50000,
    "balance": 50000,
    "currency": "USD",
    "isActive": true
  }'
```

### 3. Create Customer
```bash
curl -X POST http://localhost:8080/api/customers \
  -H "Content-Type: application/json" \
  -d '{
    "code": "CUST001",
    "name": "Acme Corporation",
    "email": "contact@acme.com",
    "phone": "+1-555-0101",
    "creditLimit": 100000,
    "balance": 0,
    "isActive": true
  }'
```

### 4. Verify Dashboard
```bash
curl http://localhost:8080/api/dashboard
```

You should see your data reflected!

## API Endpoints Quick Reference

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /api/dashboard | Dashboard summary |
| GET | /api/account-heads | All account heads |
| POST | /api/account-heads | Create account head |
| GET | /api/accounts | All accounts |
| POST | /api/accounts | Create account |
| GET | /api/customers | All customers |
| POST | /api/customers | Create customer |

## Configuration Files

### application.properties
Located at: `src/main/resources/application.properties`

Key settings:
```properties
server.port=8080                              # Change port
spring.datasource.url=jdbc:h2:mem:accountingdb # Database URL
spring.jpa.show-sql=true                       # Show SQL queries
spring.h2.console.enabled=true                 # H2 Console
```

## Using Postman

1. Download Postman: https://www.postman.com/downloads/
2. Create new collection "Accounting API"
3. Add requests:
   - GET: http://localhost:8080/api/dashboard
   - GET: http://localhost:8080/api/account-heads
   - POST: http://localhost:8080/api/account-heads
   - GET: http://localhost:8080/api/accounts
   - POST: http://localhost:8080/api/accounts
   - GET: http://localhost:8080/api/customers
   - POST: http://localhost:8080/api/customers

## Troubleshooting

### Port 8080 Already in Use
```bash
# Option 1: Change port in application.properties
server.port=8081

# Option 2: Kill process on port 8080
# On Linux/Mac:
lsof -ti:8080 | xargs kill -9

# On Windows:
netstat -ano | findstr :8080
taskkill /PID <PID> /F
```

### Maven Build Fails
```bash
# Clean and rebuild
mvn clean install -U

# Skip tests if needed
mvn clean install -DskipTests
```

### Cannot Access H2 Console
1. Check `application.properties`: `spring.h2.console.enabled=true`
2. Restart application
3. Clear browser cache

## Development Workflow

### 1. Make Code Changes
Edit files in `src/main/java/com/accounting/`

### 2. Hot Reload (DevTools)
Spring Boot DevTools auto-reloads on save.

### 3. Manual Restart
```bash
# Stop: Ctrl+C
# Start: mvn spring-boot:run
```

### 4. View Logs
Logs appear in console. To save to file:
```bash
mvn spring-boot:run > app.log 2>&1
```

## Database Management

### View All Tables
In H2 Console, run:
```sql
SHOW TABLES;
```

### View Account Heads
```sql
SELECT * FROM account_heads;
```

### View Accounts
```sql
SELECT * FROM accounts;
```

### View Customers
```sql
SELECT * FROM customers;
```

### Clear All Data
```sql
DELETE FROM customer_transactions;
DELETE FROM ledger_entry_transactions;
DELETE FROM ledger_entries;
DELETE FROM check_issue_details;
DELETE FROM check_register;
DELETE FROM customers;
DELETE FROM accounts;
DELETE FROM account_heads;
```

## Production Deployment

### Build JAR File
```bash
mvn clean package
```

Output: `target/accounting-system-1.0.0.jar`

### Run JAR
```bash
java -jar target/accounting-system-1.0.0.jar
```

### Run with Custom Port
```bash
java -jar -Dserver.port=8081 target/accounting-system-1.0.0.jar
```

### Background Execution
```bash
# Linux/Mac
nohup java -jar target/accounting-system-1.0.0.jar &

# Windows
start javaw -jar target/accounting-system-1.0.0.jar
```

## Switch to PostgreSQL

### 1. Install PostgreSQL
Download from: https://www.postgresql.org/download/

### 2. Create Database
```sql
CREATE DATABASE accountingdb;
```

### 3. Update application.properties
```properties
# Comment H2 config
#spring.datasource.url=jdbc:h2:mem:accountingdb

# Uncomment PostgreSQL config
spring.datasource.url=jdbc:postgresql://localhost:5432/accountingdb
spring.datasource.username=postgres
spring.datasource.password=yourpassword
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

### 4. Restart Application
```bash
mvn spring-boot:run
```

Tables will be auto-created!

## Next Steps

1. ✅ Backend is running
2. 📝 Review API documentation
3. 🧪 Test all endpoints
4. 💾 Explore H2 console
5. 🚀 Deploy to production

## Useful Commands

```bash
# Build without tests
mvn clean package -DskipTests

# Run tests only
mvn test

# Check for updates
mvn versions:display-dependency-updates

# Clean everything
mvn clean

# Create executable JAR
mvn clean package spring-boot:repackage
```

## Getting Help

- Check logs in console
- Review H2 database content
- Test endpoints with curl/Postman
- Spring Boot docs: https://spring.io/guides

---

**Your Spring Boot backend is ready! 🚀**

Start the Angular frontend next to see the complete system in action.
