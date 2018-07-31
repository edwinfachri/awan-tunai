package com.awantunai.bank.service;

import java.util.List;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
public class UserService {

    private final static Logger logger = LoggerFactory.getLogger(UserService.class);

    private final JdbcTemplate jdbcTemplate;

    public UserService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Create a User
    @Transactional
    public void createUser(String firstName, String lastName, String address, String birthDate, String phone) {
        try {
          logger.info("User " + firstName +" " + lastName + " is created...");
          jdbcTemplate.update("insert ignore into users(first_name, last_name, address, birth_date, phone, created_at, updated_at) values (?,?,?,?,?,?,?)"
          , firstName, lastName, address, birthDate, phone, LocalDateTime.now(), LocalDateTime.now());
        } catch (Exception e) {
          logger.error("Transaction Failed: "+e);
        }

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
