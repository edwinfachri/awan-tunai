package com.awantunai.bank.repository;

import com.awantunai.bank.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
  Admin findByUsername(String username);
  Admin findBySessionId(String sessionId);
  Integer countBySessionId(String sessionId);
}
