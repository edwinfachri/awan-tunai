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

          jdbcTemplate.update("insert ignore into users(first_name, last_name, address, birth_date, phone, created_at, updated_at) values (?,?,?,?,?,?,?)"
          , firstName, lastName, address, birthDate, phone, LocalDateTime.now(), LocalDateTime.now());
          logger.info("User " + firstName +" " + lastName + " is created...");
        } catch (Exception e) {
          logger.error("Transaction Failed: "+e);
        }

    }

    public String lowerCase(String str) {
      return str.toLowerCase();
    }



    // public List<Long> getUserId(String firstName, String lastName, String address, String birthDate, String phone) {
    //     return jdbcTemplate.query("select id from users where first_name = ? and last_name = ?and phone = ?", firstName, lastName, phone,
    //                 (rs, rowNum) -> rs.getLong("id"));
    // }

    // Return all username of user
    // public List<String> findAllUsers() {
    //     return jdbcTemplate.query("select first_name from users",
    //             (rs, rowNum) -> rs.getString("username"));
    // }

}
