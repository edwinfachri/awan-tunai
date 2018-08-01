package com.awantunai.bank.service;

import java.util.List;

import com.awantunai.bank.model.Admin;
import com.awantunai.bank.helper.Validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
public class AdminService {

    private final static Logger logger = LoggerFactory.getLogger(AdminService.class);

    private final JdbcTemplate jdbcTemplate;

    private final Validation validate;

    public AdminService(JdbcTemplate jdbcTemplate, Validation validate) {
        this.jdbcTemplate = jdbcTemplate;
        this.validate = validate;
    }

    // Create an admin
    @Transactional
    public void createAdmin(String username, String password, Long employeeId) {
        try {
          if (!validate.usernameValidation(username)) {
              return;
          }

          if (!validate.passwordValidation(password)) {
              return;
          }

          jdbcTemplate.update("insert into admins(username, password, employee_id, status, created_at, updated_at) values (?,?,?,?,?,?)"
          , username, password, employeeId, 0, LocalDateTime.now(), LocalDateTime.now());
          logger.info("Admin " + username + " is created...");
        } catch (Exception e) {
          logger.error("Transaction Failed: "+e);
        }
    }

    @Transactional
    public Integer countAdmin() {
        String sql = "select count(*) from admins";
        return (Integer) jdbcTemplate.queryForObject(
                sql, Integer.class);
    }
}