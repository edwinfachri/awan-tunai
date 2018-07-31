package com.awantunai.bank.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
public class AccountService {

    private final static Logger logger = LoggerFactory.getLogger(AccountService.class);

    private final JdbcTemplate jdbcTemplate;

    public AccountService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Create an account
    @Transactional
    public void createAccount(String accNumber, String accPin, Integer balance, Integer user_id) {
        logger.info("Account " + accNumber + " is created...");
        jdbcTemplate.update("insert into accounts(acc_number, acc_pin, balance, user_id, created_at, updated_at) values (?,?,?,?,?,?)"
        , accNumber, accPin, balance, user_id, LocalDateTime.now(), LocalDateTime.now());
    }

    // Return all accountname of admin
    // public List<String> findAllAccounts() {
    //     return jdbcTemplate.query("select accountname from admins",
    //             (rs, rowNum) -> rs.getString("accountname"));
    // }

}
