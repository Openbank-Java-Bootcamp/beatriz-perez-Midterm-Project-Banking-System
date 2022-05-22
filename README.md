# Midterm project: Banking System

A working **REST API**, built by **Beatriz Pérez Fernández** for OpenBank-IronHack Java BootCamp <br/>
that runs on a local server:

- GitHub repository :  [GitHub repository](https://github.com/Openbank-Java-Bootcamp/beatriz-perez-Midterm-Project-Banking-System.git)
- Documentation in the README.md file: [README.md file](https://github.com/Openbank-Java-Bootcamp/beatriz-perez-Midterm-Project-Banking-System/blob/master/README.md)
  <br/><br/>
***

## Readme index:
- [Description of the project](#description-of-the-project)
- [How to use the files](#how-to-use-the-files)
- [Project Structure](#project-Structure)
  - [Class diagram](#simplified-class-diagram)
  - [4 types of accounts](#accounts)
  - [3 types of Users + 2 Roles](#users)
  - [create new Checking, Savings, or CreditCard Accounts](#create-accounts)
  - [Interest and Fees](#interest-and-fees)
  - [Account Access](#account-access)
- Extra features <-----------------------------------------------
- Setup and technologies used <-----------------------------------------------


## Description of the project
        

  <br/>
***

## How to use the files
1. Download the **project**: [GitHub repository](https://github.com/Openbank-Java-Bootcamp/beatriz-perez-Midterm-Project-Banking-System.git)

2. Make sure your **DB** is ready for queries. <br/>
   You can use workbench and create an "ironhack_labs" schema to run the app (and an "ironhack_labs_test" schema to run the tests): [MySQL Workbench](https://www.mysql.com/products/workbench/)

3. Run the **app** from your IDE, for example IntelliJ IDEA. <br/>
   All entity tables will be created in the DB, go check it in workbench!: [IntelliJ IDEA](https://www.jetbrains.com/idea/promo/?source=google&medium=cpc&campaign=9736637745&term=intellij%20idea&gclid=Cj0KCQjwm6KUBhC3ARIsACIwxBiNkmA6c4mmEzhGgPIqwR7oB8EcwLAwNaYghuxPdrZg9VKEas3JgLMaAjX3EALw_wcB)

4. Use Postman to test all available **endpoints**. <br/>
   You'll see in workbench how DB tables reflect the changes you make :)  [Postman](https://www.postman.com/) <br/> <br/>
   Use this Postman collection to test the app even faster: <br/> <br/>
   [![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/11775509-03638dea-ac4f-4625-8997-614e8e14049a?action=collection%2Ffork&collection-url=entityId%3D11775509-03638dea-ac4f-4625-8997-614e8e14049a%26entityType%3Dcollection%26workspaceId%3D14973f32-7e73-4c19-8b2d-26167656e88f)
   <br/>
***

  <br/>

## Project Structure

### Simplified class diagram

- editable class diagram - project structure: [Class diagram](https://drive.google.com/file/d/1AzKxl9wNN_4bO68MQG1XOqBxD4r2wUzp/view?usp=sharing)

![class diagram](./src/images/class-diagram.png)

### Accounts
### requirement 1: The system has 4 types of accounts: StudentChecking, Checking, Savings, and CreditCard

   ## Account class
   ### Parent class
   **Table name:** Account <br/>
   **Inheritance type:** Single Table ---> All accounts will be added in this ONE table  <br/>
   **Properties:**  <br/>
    - Long accountNumber ---> Generated Value  <br/>
    - LocalDate creationDate ---> set by constructor  as the current date  <br/>
    - String secretKey ---> HASHED KEY (encrypted before being saved in the DB) + Not Empty validation  <br/>
    - AccountHolder primaryOwner ---> User Class > AccountHolder Class (OneToOne relation) + Not Null validation  <br/>
    - AccountHolder secondaryOwner ---> User Class > AccountHolder Class (OneToOne relation) + not required  <br/>
    - Status status ---> Status Enum --> Set by constructor as ACTIVE  <br/>
    - Money balance ---> Embedded  + Not Null validation  <br/>
    - Money minimumBalance ---> Embedded  + Not Null validation  <br/>
    - Money penaltyFee ---> Embedded , amount = 40 (FINAL value), same Currency as balance  <br/>
    <br/>
   **Service methods:**  <br/>
    - getAllAccounts() <br/>
    - getAllAccountsByOwner() <br/>
    - getAllMyAccounts() <br/>
    - getAccountByNumber() <br/>
    - getMyAccountByNumber() <br/>
    - updateAccountBalance() <br/>
    - operateAsThirdParty() <br/>
    - transferMoney() <br/>
    - deleteAccountByNumber() <br/>
    - getOwnerFromAuthentication() <br/>
    - checkPenaltyAlreadyApplied() <br/>
    - applyPenaltyFeeIfApplicable() <br/>
    - checkInterestRates() <br/>

   ## Checking Account class
   ### Child class
   **Extends:** Account class <br/>
   **Included in table:** Account <br/>
   **Properties:**  <br/>
    - Money monthlyMaintenanceFee ---> Embedded <br/>
    - AccountType type = REGULAR / STUDENT ---> AccountType Enum **Created as Student type by default**  <br/>
    <br/>
    * AccountType depends on the age of the primaryOwner: if it is less than 24, a Student type account is created otherwise a Regular Checking Account is created.  <br/>
    * When the primaryOwner of a Student Checking Account becomes 24 the account type is changed to REGULAR and conditions are changed (minimumBalance and monthlyMaintenanceFee) but the account is still valid and keeps it's original accountNumber.  <br/><br/>
   **Conditions:**  <br/>
    - REGULAR CheckingAccounts: minimumBalance of 250 <br/>
    - STUDENT CheckingAccounts: minimumBalance of 0 <br/>
    - REGULAR CheckingAccounts: monthlyMaintenanceFee of 12 <br/>
    - STUDENT CheckingAccounts: monthlyMaintenanceFee of 0 <br/>
   **Service methods:**  <br/>
    - createCheckingAccount() <br/>
    - checkAge() <br/>
    * Penalty fees applied by Account Service  <br/><br/>

   ## Savings Account class
   ### Child class
   **Extends:** Account class <br/>
   **Included in table:** Account <br/>
   **Properties:**  <br/>
    - BigDecimal interestRate ---> Decimal max + Decimal min validation <br/>
    - LocalDate interestReviewDate  ---> set by constructor  as the current date <br/>
    <br/>
   **Conditions:**  <br/>
    - Default interestRate of 0.0025 ---> Constructor overloading + chaining <br/>
    - Default minimumBalance of 1000 ---> Constructor overloading + chaining <br/>
    - interestRate max=0.5, min=0 - Controlled by **Spring Boot Validation** (min was not required, but was added to avoid negative interests) <br/>
    - minimumBalance max=1000, min=100 - Controlled by the **SERVICE** with checkMinimumBalance() method <br/>
   **Service methods:**  <br/>
    - createSavingsAccount() <br/>
    - checkMinimumBalance() <br/>
    * Penalty fees applied by Account Service <br/>
    * Interest rates ADDED annually by Account Service **based on balance** <br/><br/>

   ## CreditCard Account class
   ### Child class
   **Extends:** Account class <br/>
   **Included in table:** Account <br/>
   **Properties:**  <br/>
    - BigDecimal interestRate ---> Decimal max + Decimal min validation <br/>
    - LocalDate interestReviewDate  ---> set by constructor  as the current date <br/>
    - Money creditLimit ---> Embedded <br/>
    <br/>
   **Conditions:**  <br/>
    - **Minimum balance is set by constructor according to credit limit** <br/>
    - Default creditLimit of 100 ---> Constructor overloading + chaining <br/>
    - Default interestRate of 0.2  ---> Constructor overloading + chaining <br/>
    - InterestRate max=0.2, min=0.1 - Controlled by **Spring Boot Validation** <br/>
    - CreditLimit max=100000, min=100 - Controlled by the **SERVICE** <br/>
   **Service methods:**  <br/>
    - createCreditCardAccount() <br/>
    - checkCreditLimit() <br/>
    * Penalty fees applied by Account Service <br/>
    * Interest rates DEDUCTED monthly by Account Service **based on negative balance** (credit) <br/><br/>


### Users
### requirement 2: The system has 3 types of Users: Admins, Third-party Users and AccountHolders + 2 Roles

   ## User class
   ### Parent class
   **Table name:** User <br/>
   **Inheritance type:** Single Table ---> All users will be added in this ONE table  <br/>
   **Properties:**  <br/>
    - Long id ---> Generated Value <br/>
    - Name name ---> Embedded <br/>
    - String username  ---> Not Empty validation <br/>
    - String password  ---> Not Empty validation <br/>
    - Collection<Role> roles ---> ManyToMany relation **User instances are given a ROLE_ADMIN automatically**<br/>
    <br/>
   **Service methods:**  <br/>
    - getAllUsers() <br/>
    - getUserById() <br/>
    - createUser() <br/>
    - updateUserById() <br/>
    - deleteUserById(Long id) <br/>
    <br/>


   ## AccountHolder class
   ### Child class
   **Extends:** User class <br/>
   **Included in table:** User <br/>
   **Properties:**  <br/>
    - Date dateOfBirth ---> Not Null validation <br/>
    - Address primaryAddress ---> Embedded  + Not Null validation <br/>
    - Address mailingAddress ---> optional, Embedded <br/>
    * **AccountHolder instances are given a ROLE_ACCOUNTHOLDER automatically**<br/>
    <br/>
   **Conditions:**  <br/>
    - The AccountHolders can access their own accounts and only their accounts when passing the correct credentials using Bearer Auth. (Account service) <br/>
    - Account-holders can transfer money from any of their accounts to any other account (regardless of owner).  (Account service) <br/>
   **Service methods:**  <br/>
    - createAccountHolder() <br/>
    - updateAccountHolderById() <br/>
    <br/>


   ## ThirdParty class
   ### Parent class
   **Table name:** Third_party (no extending child classes)
   **Properties:**  <br/>
    - Long id ---> Generated Value <br/>
    - String username ---> Not Empty validation <br/>
    - String password ---> Not Empty validation <br/>
    <br/>
   **Conditions:**  <br/>
    - Third parties can charge and refund money to other accounts (Account service) <br/>
    - Third party users are added to the database by an admin <br/>
   **Service methods:**  <br/>
    - getAllThirdParties() <br/>
    - createThirdParty() <br/>
    - updateThirdPartyById <br/>
    - deleteThirdPartyById <br/>
    <br/>

   ## Role class
   ### Parent class
   **Table name:** Role (no extending child classes)
   **Properties:**  <br/>
    - Long id ---> Generated Value <br/>
    - String name ---> Not Empty validation <br/>
    <br/>
   **Service methods:**  <br/>
    - getAllRoles() <br/>
    - createRole <br/>
    - addRoleToUser <br/>
    <br/>


### Create accounts
### requirement 3: Admins can create new Checking, Savings, or CreditCard Accounts

- Create CHECKING account through createCheckingAccount() method in CheckingAccountService (it wil automatically be set to STUDENT / REGULAR by checkAge() method)<br/>
- Create CREDIT CARD account through createCreditCardAccount() method in CreditCardAccountService (correct limits will be set by checkCreditLimit() method)<br/>
- Create SAVINGS account through createSavingsAccount() method in SavingsAccountService (correct limits will be set by checkMinimumBalance() method)<br/>

### Interest and fees
### requirement 4: Interest and Fees are applied automatically every time an account is accessed by AccountService methods

- **Penalty fee:** applied any time the balance of an account goes below the minimum through applyPenaltyFeeIfApplicable() method. Previously we check if it has been already applied through checkPenaltyAlreadyApplied() method, so it is not applied twice.
- **Interests:** reviewed any time an account is accessed and applied if appropriate through checkInterestRates() method. It will check the type of the account and the date of the last review.

### Account access
### requirement 5: Account Access

#### ANYONE can access:
- log in endpoint: http://localhost:8080/api/login
- third party transactions endpoint:  PATCH http://localhost:8080/api/accounts/third-party-transaction

#### Only ACCOUNT HOLDERS can access:
- endpoint to get a list of all YOUR active accounts (as primary OR secondary owner): GET http://localhost:8080/api/account-holder/accounts
- endpoint to get the details of one of YOUR accounts by account number: GET http://localhost:8080/api/account-holder/accounts/{account-number}
- endpoint to transfer money from one of YOUR accounts to any other existing account (if funds or credit are sufficient): PATCH http://localhost:8080/api/account-holder/transfer

#### Only ADMIN role users can access:
- endpoint to get a list of all existing roles: GET http://localhost:8080/api/roles
- endpoint to create a new role: POST http://localhost:8080/api/roles
- endpoint to assign a role to a user manually: PATCH http://localhost:8080/api/roles/assign

- endpoint to get a list of all active users: GET http://localhost:8080/api/users
- endpoint to get the details of any user by ID: GET http://localhost:8080/api/users/{id}
- endpoint to get a list of all active third parties: GET http://localhost:8080/api/users/third-party

- endpoint to create a new admin-user: POST http://localhost:8080/api/users/admin-user
- endpoint to create a new AccountHolder user: POST http://localhost:8080/api/users/account-holder
- endpoint to create a new ThirdParty user: POST http://localhost:8080/api/users/third-party

- endpoint to update an admin-user: PUT http://localhost:8080/api/users/admin-user/{id}
- endpoint to update an AccountHolder user: PUT http://localhost:8080/api/users/account-holder/{id}
- endpoint to update a ThirdParty user: PUT http://localhost:8080/api/users/third-party/{id}

- endpoint to delete a user by ID: DELETE http://localhost:8080/api/users/{id}
- endpoint to delete a ThirdParty user by ID: DELETE http://localhost:8080/api/users/third-party/{id}

- endpoint to get a list of all active accounts: GET http://localhost:8080/api/accounts
- endpoint to get a list of all active accounts by owner ID (primary AND secondary owner): GET http://localhost:8080/api/accounts/user/{owner-id}
- endpoint to get the details of any account by account number: GET http://localhost:8080/api/accounts/{account-number}

- endpoint to create a new Checking Account: POST http://localhost:8080/api/accounts/checking
- endpoint to create a new Credit Card Account: POST http://localhost:8080/api/accounts/credit-card
- endpoint to create a new Savings Account: POST http://localhost:8080/api/accounts/savings

- endpoint to freely modify an account's balance: PATCH http://localhost:8080/api/accounts/{account-number}

- endpoint to delete an account by account number: DELETE http://localhost:8080/api/accounts/{account-number}


***

## Extra features
### Fraud Detection
The application must recognize patterns that indicate fraud and Freeze the account status when potential fraud is detected.
Patterns that indicate fraud include:
- Transactions made in 24 hours total to more than 150% of the customers highest daily total transactions in any other 24 hour period.
- More than 2 transactions occur on a single account within a 1 second period.

***

## Setup and technologies used

- Task management in Trello: [Trello tasks board](https://trello.com/invite/b/uEPSEIQa/8df7c946d07d38e4d7ce9ce5a126751e/midtermbankingsystem)

1. Java/Spring Boot backend. Project structure created with [start.spring.io](https://start.spring.io/)

    ![spring initializr](./src/images/initializr.png)

2. Model: **ENTITIES**
   - entities for accounts and users, properties and relationships
   - auxiliary classes and enums (Status, Money, Address...)
   - dependencies and application properties for validation
   - conditions for each entity property when needed
   * Money class for all currency and BigDecimal for any other decimal or large number

3. Repository: communicate with **DATABASE**
   - repositories for all entities: roles, accounts and users to communicate with the DataBase
   - Everything is stored in MySQL database tables:

   ![MySQL database tables](./src/images/DBtables.png)

4. Service: methods and **BUSINESS LOGIC**
   - service interfaces for all entities: roles, accounts and users to include methods to implement
   - service implementations (classes) for all entities: roles, accounts and users to implement methods and handle possible errors

5. Controller: **ENDPOINTS** and methods for requests (GET, POST, PUT/PATCH, and DELETE route)
   - controller interfaces for roles, accounts and users to include methods to implement
   - controller implementations (classes) for roles, accounts and users to implement methods and establish endpoint routes

6. Security configuration: **AUTHENTICATION** and **AUTHORIZATION** with Spring Security
   - dependencies: jwt dependency
   - filters for authentication and authorization
   - security configuration file
   - PasswordEncoder to encode passwords
   
7. **Tests** and test application properties

***




