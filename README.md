# Midterm project: Banking System

by **Beatriz Pérez Fernández** for OpenBank-IronHack Java BootCamp

***

## Assignment Requirements and Deliverables:

### Project Structure Requirements
1. The system must have 4 types of accounts: StudentChecking, Checking, Savings, and CreditCard.
2. The system must have 3 types of Users: Admins, Third-party Users and AccountHolders.
3. Admins can create new accounts. When creating a new account they can create Checking, Savings, or CreditCard Accounts.
4. Interest and Fees should be applied appropriately.
5. Account Access

### Technical Requirements
- Include a Java/Spring Boot backend.
- Everything should be stored in MySQL database tables.
- Include at least 1 GET, POST, PUT/PATCH, and DELETE route.
- Include authentication with Spring Security.
- Include unit and integration tests.
- Include robust error handling.
- You must use the Money class for all currency and BigDecimal for any other decimal or large number math.

### Extra features (bonus)
#### Fraud Detection
The application must recognize patterns that indicate fraud and Freeze the account status when potential fraud is detected.
Patterns that indicate fraud include:
- Transactions made in 24 hours total to more than 150% of the customers highest daily total transactions in any other 24 hour period.
- More than 2 transactions occur on a single account within a 1 second period.

### Deliverables
- A working REST API, built by you that runs on a local server:
      


- The URL of the GitHub repository for your app:

   [GitHub repository](https://github.com/Openbank-Java-Bootcamp/beatriz-perez-Midterm-Project-Banking-System.git)

- A simplified class diagram representing your project structure should be added to the repo:

   [Class diagram](https://drive.google.com/file/d/1AzKxl9wNN_4bO68MQG1XOqBxD4r2wUzp/view?usp=sharing)

- Adequate and complete documentation in the README.md file:

   [README.md file](https://github.com/Openbank-Java-Bootcamp/beatriz-perez-Midterm-Project-Banking-System/blob/master/README.md)


***

## How the project was built:

- Task management in Trello:

   [Trello tasks board](https://trello.com/invite/b/uEPSEIQa/8df7c946d07d38e4d7ce9ce5a126751e/midtermbankingsystem)

### Steps

1. Download PROJECT STRUCTURE and dependencies from [start.spring.io](https://start.spring.io/)

    Spring Boot set up with **spring Initializr**:

    ![spring initializr](./src/images/initializr.png)

2. Model: CREATE ENTITIES
   - Create entities for accounts and users and establish their properties and relationships
   - Create auxiliary classes and enums (Status, Money...)
   - Add dependencies and application properties for validation
   - Add conditions for each entity property when needed

3. Repository: COMMUNICATE WITH DATABASE
   - Create repositories for all entities: roles, accounts and users to communicate with the DataBase

4. Service: CREATE METHODS AND ADD BUSINESS LOGIC
   - Create service interfaces for all entities: roles, accounts and users to include methods to implement
   - Create service implementations (classes) for all entities: roles, accounts and users to implement methods

***

## How the project works and how to use it:


