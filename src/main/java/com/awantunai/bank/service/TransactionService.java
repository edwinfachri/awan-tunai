package com.awantunai.bank.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.awantunai.bank.helper.Validation;


import java.time.LocalDateTime;

@Component
public class TransactionService {

    private final static Logger logger = LoggerFactory.getLogger(TransactionService.class);

    private final JdbcTemplate jdbcTemplate;

    private final AccountService accountService;

    private final Validation validate;


    public TransactionService(JdbcTemplate jdbcTemplate,
                              AccountService accountService,
                              Validation validate) {
        this.jdbcTemplate = jdbcTemplate;
        this.accountService = accountService;
        this.validate = validate;
    }

    // Transfer money from account
    @Transactional
    public void transfer(String accountId, Integer amount, String destinationId, String note) {
        try {

          if (!validate.accNumberValidation(accountId)) {
              return;
          }

          if (!validate.amountValidation(amount)) {
              return;
          }

          if (!validate.accNumberValidation(destinationId)) {
              return;
          }

          String acc_id = accountService.findAccount(accountId);
          if (acc_id == "null") {
            logger.error("Sender account not found");
            return;
          }

          String des_id = accountService.findAccount(destinationId);
          if (des_id == "null") {
            logger.error("Receiver account not found");
            return;
          }

          jdbcTemplate.update("insert into transactions(account_id, amount, destination, note, type, created_at, updated_at) values (?,?,?,?,?,?,?)"
          , acc_id, amount, des_id, note, 3, LocalDateTime.now(), LocalDateTime.now());
          jdbcTemplate.update("update accounts set balance = balance - ? where id = ?", amount, acc_id);
          jdbcTemplate.update("update accounts set balance = balance + ? where id = ?", amount, des_id);
          logger.info(accountId + " transfered "+ amount +" to " + destinationId);
        } catch (Exception e) {
          logger.error("Transaction Failed: "+e);
        }

    }

    // Withdraw money from account
    @Transactional
    public void withdraw(String accountId, Integer amount) {
        try {
          String acc_id = accountService.findAccount(accountId);
          if (acc_id == "null") {
            logger.error("Sender account not found");
            return;
          }


          jdbcTemplate.update("insert into transactions(account_id, amount, destination, type, created_at, updated_at) values (?,?,?,?,?,?)"
          , acc_id, amount, acc_id, 2, LocalDateTime.now(), LocalDateTime.now());
          jdbcTemplate.update("update accounts set balance = balance - ? where id = ?", amount, acc_id);
          logger.info(accountId + " withdraw "+ amount);
        } catch (Exception e) {
          logger.error("Transaction Failed: "+e);
        }

    }

    // Deposit money to account
    @Transactional
    public void deposit(String accountId, Integer amount) {
        try {
          String acc_id = accountService.findAccount(accountId);
          if (acc_id == "null") {
            logger.error("Sender account not found");
            return;
          }


          jdbcTemplate.update("insert into transactions(account_id, amount, destination, type, created_at, updated_at) values (?,?,?,?,?,?)"
          , acc_id, amount, acc_id, 1, LocalDateTime.now(), LocalDateTime.now());
          jdbcTemplate.update("update accounts set balance = balance + ? where id = ?", amount, acc_id);
          logger.info(accountId + " deposit "+ amount);
        } catch (Exception e) {
          logger.error("Transaction Failed: "+e);
        }

    }







    // Return all transactionname of transaction
    // public List<String> findAllTransactions() {
    //     return jdbcTemplate.query("select first_name from transactions",
    //             (rs, rowNum) -> rs.getString("transactionname"));
    // }

}
