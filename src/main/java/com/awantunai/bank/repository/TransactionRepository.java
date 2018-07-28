package com.awantunai.bank.repository;

import com.awantunai.bank.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.awantunai.bank.model.Account;
import java.util.Collection;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
  Collection<Transaction> findByAccountId(Long accountId);
}
