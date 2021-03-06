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
public class AccountService {

    private final static Logger logger = LoggerFactory.getLogger(AccountService.class);

    private final JdbcTemplate jdbcTemplate;

    private final UserService userService;

    private final Validation validate;

    public AccountService(JdbcTemplate jdbcTemplate,
                          UserService userService,
                          Validation validate
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.userService = userService;
        this.validate = validate;
    }

    // Create an account
    @Transactional
    public void createAccount(String accNumber, String accPin, Integer balance,
                              Long userId)
    {

        if (!validate.accNumberValidation(accNumber)) {
            return;
        }
        if (!validate.accPinValidation(accPin)) {
            return;
        }
        if (!validate.amountValidation(balance)) {
            return;
        }

        try {
          jdbcTemplate.update("insert into accounts(acc_number, acc_pin, balance, user_id, created_at, updated_at) values (?,?,?,?,?,?)"
          , accNumber, accPin, balance, userId, LocalDateTime.now(), LocalDateTime.now());
        } catch (Exception e) {
          logger.error("Transaction Failed: "+e);
        }
    }

    @Transactional
    public String findAccount(String accNumber) {
        String sql = "select case when count(1) > 0 then id else 'null' end as id from accounts where acc_number=?";
        String account_id = (String) jdbcTemplate.queryForObject(
                sql, new Object[] { accNumber }, String.class);
        return account_id;
    }

    @Transactional
    public Integer countAccount() {
        String sql = "select count(*) from accounts";
        return (Integer) jdbcTemplate.queryForObject(
                sql, Integer.class);
    }

}
