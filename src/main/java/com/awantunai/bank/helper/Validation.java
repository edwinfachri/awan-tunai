package com.awantunai.bank.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Validation {

  private final static Logger logger = LoggerFactory.getLogger(Validation.class);

  public Validation() {}

  public Boolean accNumberValidation(String accNumber) {
      if (accNumber.length() != 10) {
        logger.error("Account number must contain exactly 10 characters");
        return false;
      }

      if (!accNumber.matches("[0-9]+") || accNumber.matches("[a-zA-Z]+")) {
        logger.error("Account number must contain only number");
        return false;
      }

      return true;
  }

  public Boolean accPinValidation(String accPin) {
      if (accPin.length() != 6) {
        logger.error("Pin number must contain exactly 6 characters");
        return false;
      }

      if (!accPin.matches("[0-9]+") || accPin.matches("[a-zA-Z]+")) {
        logger.error("Pin number must contain only number");
        return false;
      }

      return true;
  }

  public Boolean amountValidation(Integer amount) {
      if (amount < 0) {
        logger.error("Amount must be positive integer");
        return false;
      }

      return true;
  }

  public Boolean firstLastNameValidation(String name) {
      if (name.length() < 3 && name.length() > 50) {
        logger.error("Name must contain more than 2 characters and less than 51 characters");
        return false;
      }

      if (name.matches("[0-9]+")) {
        logger.error("Name must not contain number");
        return false;
      }

      return true;
  }

  public Boolean addressValidation(String address) {
      if (address.length() < 6 && address.length() > 150) {
        logger.error("Address must contain more than 5 characters and less than 151 characters");
        return false;
      }

      return true;
  }

  public Boolean birthDateValidation(String birthDate) {
      if (birthDate.length() != 8 && birthDate.length() != 10) {
        logger.error("Birth date format must be YYYYMMDD or YYYY-MM-DD");
        return false;
      }

      return true;
  }

  public Boolean phoneValidation(String phone) {
      if (phone.length() < 7 && phone.length() > 15) {
        logger.error("phone must contain more than 6 characters and less than 16 characters");
        return false;
      }

      if (!phone.matches("[0-9]+") || phone.matches("[a-zA-Z]+")) {
        logger.error("Phone number must contain only number");
        return false;
      }

      return true;
  }

  public Boolean passwordValidation(String password) {
      if (password.length() < 5 && password.length() > 25) {
        logger.error("Password must contain more than 4 characters and less than 26 characters");
        return false;
      }

      return true;
  }

  public Boolean usernameValidation(String username) {
      if (username.length() < 5 && username.length() > 25) {
        logger.error("Username must contain more than 4 characters and less than 26 characters");
        return false;
      }

      return true;
  }
}
