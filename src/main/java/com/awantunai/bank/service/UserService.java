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
    public void createUser(String firstName, String lastName, String address, Date birthDate, String phone) {
        logger.info("User " + firstName +" " + lastName + " is created...");
        jdbcTemplate.update("insert into users(first_name, last_name, address, birth_date, phone, created_at, updated_at) values (?,?,?,?,?,?)"
        , firstName, lastName, address, birthDate, phone, LocalDateTime.now(), LocalDateTime.now());
    }

    // Return all username of user
    // public List<String> findAllUsers() {
    //     return jdbcTemplate.query("select first_name from users",
    //             (rs, rowNum) -> rs.getString("username"));
    // }

}
