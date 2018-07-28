# AWAN TUNAI JOB APPLICATION TEST

## Contributors
The contributor(s) are:
  1. Edwin Fachri Wicaksono

## Overview
This project is a test of a data engineering position in Awan Tunai.

## Problems
Production Team needs an application to input data to database manually.

## Main Functionality
The main functionalities of the system are:
  1. To create user and to login to system.
  2. To manipulate (withdraw and deposit) account.
  3. To manipulate interacted account (transfer).
  4. To fetch transaction log.

## Application Requirement
The requirements to run the application are:
  1. XAMPP Version 7.2.3
  2. Java Version 1.8
  3. Spring Version 2.0.0
  4. Maven Version 3.3
Besides all those mentioned tools, all its dependencies also needs to be installed on your machine to run the application.

## Assumption
The assumptions for this project are:
  1. The user is administrator or officer at awan-tunai.
  2. The user mentioned on the point 1 does not need pin number to access the account since they are the officer
  3. The application will return JSON format

## How to run the Application Server
The steps to run the Application Server are:
  1. Install all the required tools.
  2. Download this project and extract it to any folder.
  3. Run the database server using XAMPP.
  4. Open `localhost/phpmyadmin` on your browser to create the database and user. This step will only be done once.
  5. Create `awan_tunai_app` database.
  6. Create user on `phpmyadmin` with the username of `root` and leave the password blank. You may use your preferred user and change the `application.properties` on `/bank/src/main/resources` on `spring.datasource.username` and `spring.datasource.password` to your own details.
  7. Open the terminal. Go into the main directory of the application.
  8. Run the server: `mvn sprin-root:run`

## API Format
The format for request to the services are:
  1. Create Admin
    - Data
    No  | Parameter | Type
    --- | --- | ---
    1   | username | String
    2   | password | String
    3   | employeeId | Long
    4   | status | Boolean

  2. Login
    - Data
    No  | Parameter | Type
    --- | --- | ---
    1   | username | String
    2   | password | String

  3. Logout
    - Data
    No  | Parameter | Type
    --- | --- | ---
    1   | sessionId | String

  4. Create User
    - Parameter: sessionId
    - Data
    No  | Parameter | Type
    --- | --- | ---
    1   | firstName | String
    1   | lastName | String
    1   | birthDate | date
    1   | phone | String
    1   | address | String

  5. Create Account
    - Parameter: sessionId
    - Data
    No  | Parameter | Type
    --- | --- | ---
    1   | accNumber | String
    2   | accPin | String
    3   | balance | Integer

  6. Deposit
    - Parameter: sessionId
    - Data
    No  | Parameter | Type
    --- | --- | ---
    1   | accNumber | String
    2   | balance | Integer

  7. Withdraw
    - Parameter: sessionId
    - Data
    No  | Parameter | Type
    --- | --- | ---
    1   | accNumber | String
    2   | balance | Integer

  8. Transfer
    - Parameter: sessionId
    - Data
    No  | Parameter | Type
    --- | --- | ---
    1   | accNumber | String
    2   | amount | Integer
    3   | destination | String

  9. View Transactions
    - Parameter: sessionId

  10. Create Account
    - Parameter: sessionId
    - URL Path data: accountId




## How to send request
The steps to send requests are:
  1. Make sure the application server and database server are on
  2. Open Terminal and send the following request using `curl`
    - Create Admin
    ```
    curl -X POST \
      http://localhost:8080/awantunai/admins \
      -H 'Cache-Control: no-cache' \
      -H 'Content-Type: application/json' \
      -H 'Postman-Token: fe9218f9-391b-40f0-b8eb-a922c78f8afc' \
      -d '{"username" : "edwinn", "password" : "fachrii", "employeeId" : "1", "status" : "1"}'
    ```
    - Login
    ```
    curl -X POST \
      http://localhost:8080/awantunai/admins/login \
      -H 'Cache-Control: no-cache' \
      -H 'Content-Type: application/json' \
      -H 'Postman-Token: f6d5c821-46dd-4fec-ace7-40a6f202e80a' \
      -d '{"username" : "edwinn", "password" : "fachrii"}'
    ```
    You will get a key to make a transaction through this application that will be sent altogether with the curl via parameter. Save It.

    My key is `0258d42a-ca66-432f-8307-4ed78b103cfc`

    - Logout
    ```
    curl -X POST \
      http://localhost:8080/awantunai/admins/logout \
      -H 'Cache-Control: no-cache' \
      -H 'Content-Type: application/json' \
      -H 'Postman-Token: cee3390b-0c4d-485c-ac33-5b210b142940' \
      -d '{"sessionId" : "0258d42a-ca66-432f-8307-4ed78b103cfc"}'
    ```
    Change the key to the one you obtained while login.

    - Create User
    ```
    curl -X POST \
      'http://localhost:8080/awantunai/users?sessionId=0258d42a-ca66-432f-8307-4ed78b103cfc' \
      -H 'Cache-Control: no-cache' \
      -H 'Content-Type: application/json' \
      -H 'Postman-Token: 5c60815b-ac7b-4399-b8c1-91dc4f956ff2' \
      -d '{"firstName" : "edwin", "lastName" : "fachri", "birthDate" : "19940917", "phone" : "085959336191", "address" : "Taman Anyelir"}'
    ```
    Always replace the `sessionId` parameter with your own key.

    - Create Account
    ```
    curl -X POST \
      'http://localhost:8080/awantunai/users/1/accounts?sessionId=0258d42a-ca66-432f-8307-4ed78b103cfc' \
      -H 'Cache-Control: no-cache' \
      -H 'Content-Type: application/json' \
      -H 'Postman-Token: 08abdd21-8f8c-49d7-a43c-68adabe6e954' \
      -d '{"accNumber" : "1234567890", "accPin" : "123456", "balance" : "500000"}'
    ```

    - Deposit
    ```
    curl -X PUT \
      'http://localhost:8080/awantunai/deposit?sessionId=0258d42a-ca66-432f-8307-4ed78b103cfc' \
      -H 'Cache-Control: no-cache' \
      -H 'Content-Type: application/json' \
      -H 'Postman-Token: 86b381c0-f675-4b08-802b-9d96516857a1' \
      -d '{"accNumber":"1234567890", "balance": 500}'
    ```

    - Withdraw
    ```
    curl -X PUT \
      'http://localhost:8080/awantunai/withdraw?sessionId=0258d42a-ca66-432f-8307-4ed78b103cfc' \
      -H 'Cache-Control: no-cache' \
      -H 'Content-Type: application/json' \
      -H 'Postman-Token: 1fd1c5ea-704f-4daa-969d-563648297060' \
      -d '{"accNumber":"1234567890", "balance": 500}'
    ```

    - Transfer
    Create another account by following command
    ```
    curl -X POST \
      'http://localhost:8080/awantunai/users/1/accounts?sessionId=0258d42a-ca66-432f-8307-4ed78b103cfc' \
      -H 'Cache-Control: no-cache' \
      -H 'Content-Type: application/json' \
      -H 'Postman-Token: 6a41d9fa-e8a1-45a6-a302-64de085ba31c' \
      -d '{"accNumber" : "1234567891", "accPin" : "123456", "balance" : "500000"}'
    ```
    and then make the transfer.
    ```
    curl -X POST \
      'http://localhost:8080/awantunai/transfer?sessionId=0258d42a-ca66-432f-8307-4ed78b103cfc' \
      -H 'Cache-Control: no-cache' \
      -H 'Content-Type: application/json' \
      -H 'Postman-Token: 0e39be91-80d1-40f1-b930-a1cd1f9840ae' \
      -d '{"accNumber" : "1234567890", "amount" : "599", "destination" : "1234567891"}'
    ```

    - View Transactions
    ```
    curl -X GET \
      'http://localhost:8080/awantunai/transactions?sessionId=0258d42a-ca66-432f-8307-4ed78b103cfc' \
      -H 'Cache-Control: no-cache' \
      -H 'Postman-Token: 2ada7c4d-aa6f-430d-9c8a-ab7be723187a'
    ```

    - View Transactions from particular account
    ```
    curl -X GET \
      'http://localhost:8080/awantunai/transactions/1?sessionId=0258d42a-ca66-432f-8307-4ed78b103cfc' \
      -H 'Cache-Control: no-cache' \
      -H 'Postman-Token: 49b20271-2626-4a36-a5eb-33726cbe1fb3'
    ```
