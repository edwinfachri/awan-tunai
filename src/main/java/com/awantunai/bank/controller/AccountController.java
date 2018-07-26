package com.awantunai.bank.controller;

import com.awantunai.bank.exception.ResourceNotFoundException;
import com.awantunai.bank.model.Account;
import com.awantunai.bank.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/awantunai")
public class AccountController {

    @Autowired
    AccountRepository accountRepository;

    // Get All Accounts
    @GetMapping("/accounts")
    public List<Account> getAllAccount() {
      return accountRepository.findAll();
    }

    // Create an Account
    @PostMapping("/accounts")
    public Account createAccount(@Valid @RequestBody Account account) {
      return accountRepository.save(account);
    }

    // Get an Account
    @GetMapping("/accounts/{id}")
    public Account getAccountById(@PathVariable(value="id") Long accountId) {
      return accountRepository.findById(accountId)
          .orElseThrow(() -> new ResourceNotFoundException("Account", "id", accountId));
    }

    // Deposit to an Account
    @PutMapping("/accounts/deposit/{id}")
    public Account deposit(@PathVariable(value="id") Long accountId,
        @Valid @RequestBody Account accountDetails) {
      Account account = accountRepository.findById(accountId)
          .orElseThrow(() -> new ResourceNotFoundException("Account", "id", accountId));
      account.setBalance(accountDetails.getBalance() + account.getBalance());

      Account updateAccount = accountRepository.save(account);
      return updateAccount;
    }

    // Withdraw to an Account
    @PutMapping("/accounts/withdraw/{id}")
    public Account withdraw(@PathVariable(value="id") Long accountId,
        @Valid @RequestBody Account accountDetails) {
      Account account = accountRepository.findById(accountId)
          .orElseThrow(() -> new ResourceNotFoundException("Account", "id", accountId));
      account.setBalance(account.getBalance() - accountDetails.getBalance());

      Account updateAccount = accountRepository.save(account);
      return updateAccount;
    }

    // Delete an Account
    @DeleteMapping("/accounts/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable(value="id") Long accountId) {
      Account account = accountRepository.findById(accountId)
          .orElseThrow(() -> new ResourceNotFoundException("Account", "id", accountId));

      accountRepository.delete(account);

      return ResponseEntity.ok().build();
    }
}
