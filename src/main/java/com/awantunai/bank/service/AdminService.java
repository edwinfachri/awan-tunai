package com.awantunai.bank.service;

import java.util.List;

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

    public AdminService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Create an admin
    @Transactional
    public void createAdmin(String username, String password, Integer employeeId) {
        logger.info("Admin " + username + " is created...");
        jdbcTemplate.update("insert into admins(username, password, employee_id, status, created_at, updated_at) values (?,?,?,?,?,?)"
        , username, password, employeeId, 0, LocalDateTime.now(), LocalDateTime.now());
    }

    // Return all username of admin
    // public List<String> findAllAdmins() {
    //     return jdbcTemplate.query("select username from admins",
    //             (rs, rowNum) -> rs.getString("username"));
    // }

}
