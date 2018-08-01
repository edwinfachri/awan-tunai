package com.awantunai.bank.service;

import java.util.List;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.awantunai.bank.helper.Validation;

import java.time.LocalDateTime;

@Component
public class UserService {

    private final static Logger logger = LoggerFactory.getLogger(UserService.class);

    private final JdbcTemplate jdbcTemplate;

    private final Validation validate;

    public UserService(JdbcTemplate jdbcTemplate, Validation validate) {
        this.jdbcTemplate = jdbcTemplate;
        this.validate = validate;
    }

    // Create a User
    @Transactional
    public void createUser(String firstName, String lastName, String address, String birthDate, String phone) {
        try {

          if (!validate.firstLastNameValidation(firstName)) {
              return;
          }
          if (!validate.firstLastNameValidation(lastName)) {
              return;
          }
          if (!validate.addressValidation(address)) {
              return;
          }
          if (!validate.birthDateValidation(birthDate)) {
              return;
          }
          if (!validate.phoneValidation(phone)) {
              return;
          }

          String lowerFirstName = firstName.toLowerCase();
          String lowerLastName = lastName.toLowerCase();
          String lowerAddress = address.toLowerCase();

          jdbcTemplate.update("insert ignore into users(first_name, last_name, address, birth_date, phone, created_at, updated_at) values (?,?,?,?,?,?,?)"
          , lowerFirstName, lowerLastName, lowerAddress, birthDate, phone, LocalDateTime.now(), LocalDateTime.now());
          logger.info("User " + lowerFirstName +" " + lowerLastName + " is created...");
        } catch (Exception e) {
          logger.error("Transaction Failed: "+e);
        }

    }

    @Transactional
    public Integer countUser() {
        String sql = "select count(*) from users";
        return (Integer) jdbcTemplate.queryForObject(
                sql, Integer.class);
    }

}
