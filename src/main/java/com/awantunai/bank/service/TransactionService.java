package com.awantunai.bank.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
public class TransactionService {

    private final static Logger logger = LoggerFactory.getLogger(TransactionService.class);

    private final JdbcTemplate jdbcTemplate;

    public TransactionService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Transfer money from account
    @Transactional
    public void transfer(String accountId, Integer amount, String destinationId, String note) {
        logger.info(accountId + " transfered "+ amount +" to " + destinationId);
        jdbcTemplate.update("insert into transactions(account_id, amount, destination_id, note, type, created_at, updated_at) values (?,?,?,?,?,?)"
        , accountId, amount, destinationId, note, 3, LocalDateTime.now(), LocalDateTime.now());
    }

    // Withdraw money from account
    @Transactional
    public void withdraw(String accountId, Integer amount) {
        logger.info(accountId + " withdraw "+ amount);
        jdbcTemplate.update("insert into transactions(account_id, amount, destination_id, type, created_at, updated_at) values (?,?,?,?,?,?)"
        , accountId, amount, accountId, 2, LocalDateTime.now(), LocalDateTime.now());
    }

    // Deposit money to account
    @Transactional
    public void deposit(String accountId, Integer amount) {
        logger.info(accountId + " deposit "+ amount);
        jdbcTemplate.update("insert into transactions(account_id, amount, destination_id, type, created_at, updated_at) values (?,?,?,?,?,?)"
        , accountId, amount, accountId, 1, LocalDateTime.now(), LocalDateTime.now());
    }

    // Return all transactionname of transaction
    // public List<String> findAllTransactions() {
    //     return jdbcTemplate.query("select first_name from transactions",
    //             (rs, rowNum) -> rs.getString("transactionname"));
    // }

}
