# Accounting System - Spring Boot Backend

A comprehensive RESTful API backend for accounting system with complete financial management capabilities.

## Technology Stack

- **Java**: 17
- **Spring Boot**: 3.2.0
- **Spring Data JPA**: Database operations
- **Hibernate**: ORM framework
- **H2 Database**: In-memory database (development)
- **PostgreSQL**: Production database support
- **Maven**: Build and dependency management
- **Lombok**: Reduce boilerplate code
- **Jakarta Validation**: Input validation

## Features

### Core Modules

1. **Account Management**
   - Account Heads (Categories): Asset, Liability, Equity, Revenue, Expense
   - Chart of Accounts with hierarchical structure
   - Multi-currency support
   - Automatic balance tracking

2. **Customer Management**
   - Customer master data
   - Credit limit management
   - Balance tracking
   - Search functionality

3. **Customer Transactions**
   - Invoice management
   - Payment processing
   - Credit/Debit notes
   - Payment status tracking
   - Multiple payment methods

4. **General Ledger**
   - Ledger entries (Debit/Credit)
   - Account-wise ledger
   - Balance calculation
   - Reconciliation support

5. **Check Management**
   - Check register
   - Check status tracking
   - Expense allocation
   - Print management

6. **Dashboard & Analytics**
   - Financial summary (Assets, Liabilities, Revenue, Expenses)
   - Net income calculation
   - Customer statistics
   - Transaction monitoring

## Project Structure

```
accounting-backend/
├── src/
│   ├── main/
│   │   ├── java/com/accounting/
│   │   │   ├── model/
│   │   │   │   ├── AccountHead.java
│   │   │   │   ├── Account.java
│   │   │   │   ├── Customer.java
│   │   │   │   ├── CustomerTransaction.java
│   │   │   │   ├── LedgerEntry.java
│   │   │   │   ├── LedgerEntryTransaction.java
│   │   │   │   ├── CheckRegister.java
│   │   │   │   └── CheckIssueDetail.java
│   │   │   ├── repository/
│   │   │   │   ├── AccountHeadRepository.java
│   │   │   │   ├── AccountRepository.java
│   │   │   │   ├── CustomerRepository.java
│   │   │   │   ├── CustomerTransactionRepository.java
│   │   │   │   ├── LedgerEntryRepository.java
│   │   │   │   ├── LedgerEntryTransactionRepository.java
│   │   │   │   ├── CheckRegisterRepository.java
│   │   │   │   └── CheckIssueDetailRepository.java
│   │   │   ├── service/
│   │   │   │   ├── AccountHeadService.java
│   │   │   │   ├── AccountService.java
│   │   │   │   ├── CustomerService.java
│   │   │   │   └── DashboardService.java
│   │   │   ├── controller/
│   │   │   │   ├── AccountHeadController.java
│   │   │   │   ├── AccountController.java
│   │   │   │   ├── CustomerController.java
│   │   │   │   └── DashboardController.java
│   │   │   └── AccountingSystemApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
└── pom.xml
```

## Database Schema

### Entities

1. **account_heads** - Account categories
2. **accounts** - Chart of accounts
3. **customers** - Customer master
4. **customer_transactions** - All customer transactions
5. **ledger_entries** - General ledger
6. **ledger_entry_transactions** - Ledger details
7. **check_register** - Check management
8. **check_issue_details** - Check expense allocation

### Relationships

- Account → AccountHead (Many-to-One)
- CustomerTransaction → Customer (Many-to-One)
- LedgerEntry → Account (Many-to-One)
- LedgerEntryTransaction → LedgerEntry (Many-to-One)
- CheckRegister → Account (Many-to-One)
- CheckIssueDetail → CheckRegister (Many-to-One)
- CheckIssueDetail → Account (Many-to-One)

## Setup Instructions

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- (Optional) PostgreSQL for production

### Installation

1. **Extract the project**
```bash
cd accounting-backend
```

2. **Build the project**
```bash
mvn clean install
```

3. **Run the application**
```bash
mvn spring-boot:run
```

The server will start at: `http://localhost:8080`

### Configuration

#### Development (H2 Database)

Default configuration uses H2 in-memory database. No additional setup required.

Access H2 Console:
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:accountingdb`
- Username: `sa`
- Password: (empty)

#### Production (PostgreSQL)

Update `src/main/resources/application.properties`:

```properties
# Comment out H2 configuration
# Uncomment PostgreSQL configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/accountingdb
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

Create database:
```sql
CREATE DATABASE accountingdb;
```

## API Endpoints

### Dashboard
```
GET /api/dashboard - Get dashboard summary
```

### Account Heads
```
GET    /api/account-heads           - Get all account heads
GET    /api/account-heads/{id}      - Get by ID
GET    /api/account-heads/active    - Get active only
GET    /api/account-heads/code/{code} - Get by code
GET    /api/account-heads/type/{type} - Get by type
POST   /api/account-heads           - Create new
PUT    /api/account-heads/{id}      - Update
DELETE /api/account-heads/{id}      - Delete
```

### Accounts
```
GET    /api/accounts                     - Get all accounts
GET    /api/accounts/{id}                - Get by ID
GET    /api/accounts/active              - Get active only
GET    /api/accounts/code/{code}         - Get by code
GET    /api/accounts/account-head/{id}   - Get by account head
GET    /api/accounts/type/{type}         - Get by account type
POST   /api/accounts                     - Create new
PUT    /api/accounts/{id}                - Update
DELETE /api/accounts/{id}                - Delete
```

### Customers
```
GET    /api/customers              - Get all customers
GET    /api/customers/{id}         - Get by ID
GET    /api/customers/active       - Get active only
GET    /api/customers/code/{code}  - Get by code
GET    /api/customers/search?name= - Search by name
POST   /api/customers              - Create new
PUT    /api/customers/{id}         - Update
DELETE /api/customers/{id}         - Delete
```

## API Examples

### Get Dashboard Data
```bash
curl http://localhost:8080/api/dashboard
```

### Create Account Head
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

### Create Account
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

### Create Customer
```bash
curl -X POST http://localhost:8080/api/customers \
  -H "Content-Type: application/json" \
  -d '{
    "code": "CUST001",
    "name": "Acme Corporation",
    "email": "contact@acme.com",
    "phone": "+1-555-0101",
    "address": "123 Business St",
    "city": "New York",
    "country": "USA",
    "creditLimit": 100000,
    "balance": 0,
    "isActive": true
  }'
```

### Search Customers
```bash
curl http://localhost:8080/api/customers/search?name=Acme
```

## Testing

### Run Tests
```bash
mvn test
```

### Manual Testing with cURL

Test all endpoints:
```bash
# Dashboard
curl http://localhost:8080/api/dashboard

# Account Heads
curl http://localhost:8080/api/account-heads

# Accounts
curl http://localhost:8080/api/accounts

# Customers
curl http://localhost:8080/api/customers
```

### Using Postman

Import the following collection:
1. Create new collection "Accounting System"
2. Add requests for all endpoints above
3. Set base URL: `http://localhost:8080/api`

## Configuration

### Application Properties

Key configurations in `application.properties`:

```properties
# Server
server.port=8080

# Database (H2)
spring.datasource.url=jdbc:h2:mem:accountingdb
spring.datasource.username=sa
spring.datasource.password=

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# H2 Console
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# CORS
spring.web.cors.allowed-origins=http://localhost:4200
```

## Build & Deploy

### Build JAR
```bash
mvn clean package
```

### Run JAR
```bash
java -jar target/accounting-system-1.0.0.jar
```

### Production Build
```bash
mvn clean package -Pprod
```

## Troubleshooting

### Port Already in Use
```bash
# Change port in application.properties
server.port=8081
```

### Database Connection Issues
- Verify database credentials
- Check if database is running
- Ensure correct JDBC URL

### Build Failures
```bash
# Clean and rebuild
mvn clean install -U
```

## Future Enhancements

- [ ] Add JWT authentication
- [ ] Implement role-based access control
- [ ] Add transaction history tracking
- [ ] Implement audit logging
- [ ] Add batch processing support
- [ ] Create comprehensive reports API
- [ ] Add export functionality (PDF, Excel)
- [ ] Implement email notifications
- [ ] Add multi-tenancy support
- [ ] Create GraphQL API

## Dependencies

Main dependencies used:
- `spring-boot-starter-web` - REST API
- `spring-boot-starter-data-jpa` - Database operations
- `spring-boot-starter-validation` - Input validation
- `h2` - In-memory database
- `postgresql` - Production database
- `lombok` - Code generation

## License

MIT License

## Support

For issues and questions:
1. Check application logs
2. Review H2 console for data
3. Verify API endpoints with curl/Postman
4. Check Spring Boot documentation

---

**Backend Server Ready! 🚀**
"# livaBackend" 
