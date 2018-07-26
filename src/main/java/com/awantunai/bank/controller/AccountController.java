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

    // Get All Notes
    @GetMapping("/accounts")
    public List<Account> getAllAccount() {
      return accountRepository.findAll();
    }

    // Create a new Note
    @PostMapping("/accounts")
    public Account createAccount(@Valid @RequestBody Account account) {
      return accountRepository.save(account);
    }

    // Get a Single Note
    @GetMapping("/accounts/{id}")
    public Account getAccountById(@PathVariable(value="id") Long accountId) {
      return accountRepository.findById(accountId)
          .orElseThrow(() -> new ResourceNotFoundException("Account", "id", accountId));
    }

    // Update a Note
    @PutMapping("/accounts/{id}")
    public Account updateAccount(@PathVariable(value="id") Long accountId,
        @Valid @RequestBody Account accountDetails) {
      Account account = accountRepository.findById(accountId)
          .orElseThrow(() -> new ResourceNotFoundException("Account", "id", accountId));
      account.setBalance(accountDetails.getBalance());

      Account updateAccount = accountRepository.save(account);
      return updateAccount;
    }

    // Delete a Note
    @DeleteMapping("/accounts/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable(value="id") Long accountId) {
      Account account = accountRepository.findById(accountId)
          .orElseThrow(() -> new ResourceNotFoundException("Account", "id", accountId));

      accountRepository.delete(account);

      return ResponseEntity.ok().build();
    }
}
