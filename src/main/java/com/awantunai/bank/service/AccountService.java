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

    public String findAccount(String accNumber) {
        String sql = "select case when count(1) > 0 then id else 'null' end as id from accounts where acc_number=?";
        String streetName = (String) jdbcTemplate.queryForObject(
                sql, new Object[] { accNumber }, String.class);
        return streetName;
    }

    // Return all accountname of admin
    // public List<String> findAllAccounts() {
    //     return jdbcTemplate.query("select accountname from admins",
    //             (rs, rowNum) -> rs.getString("accountname"));
    // }

}
