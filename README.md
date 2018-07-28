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
  5. Postman / Curl
Besides all those mentioned tools, all its dependencies also needs to be installed on your machine to run the application.

## How to run the Application
The steps to run the application are:
  1. Install all the required tools.
  2. Download this project and extract it to any folder.
  3. Run the database server using XAMPP.
  4. Open `localhost/phpmyadmin` on your browser to create the database and user. This step will only be done once.
  5. Create `awan_tunai_app` database.
  6. Create user on `phpmyadmin` with the username of `root` and leave the password blank. You may use your preferred user and change the `application.properties` on `/bank/src/main/resources` on `spring.datasource.username` and `spring.datasource.password` to your own details.
  7. Open the terminal. Go into the main directory of the application.
  8. Run the server: `mvn sprin-root:run`

## How to send request
