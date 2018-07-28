package com.awantunai.bank.controller;

import com.awantunai.bank.helper.ResourceNotFoundException;
import com.awantunai.bank.model.Account;
import com.awantunai.bank.model.User;
import com.awantunai.bank.model.Transaction;
import com.awantunai.bank.repository.AccountRepository;
import com.awantunai.bank.repository.UserRepository;
import com.awantunai.bank.repository.TransactionRepository;
import com.awantunai.bank.controller.AdminController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/awantunai")
public class AccountController {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AdminController adminController = new AdminController();

    // // Login to Accounts
    // @PostMapping("/login")
    // public String login(@RequestBody Account accountDetails) {
    //   Account account = accountRepository.findByAccNumber(accountDetails.getAccNumber());
    //   if (account.getAccPin().equals(accountDetails.getAccPin())) {
    //     // Generate random UID as a session ID
    //     String uid = UUID.randomUUID().toString();
    //     account.setSessionId(uid);
    //     accountRepository.save(account);
    //     return uid;
    //   } else {
    //     return "Login Failed";
    //   }
    // }
    //
    // // Login to Accounts
    // @PostMapping("/logout")
    // public String logout(@RequestBody Account accountDetails) {
    //   Account account = accountRepository.findByAccNumber(accountDetails.getAccNumber());
    //   // Remove value of random UID on session ID in database
    //   account.setSessionId(null);
    //   accountRepository.save(account);
    //   return "Logged Out";
    // }

    // Get All Accounts
    @GetMapping("/accounts")
    public ResponseEntity<?> getAllAccount(@RequestParam("sessionId") String sessionId) {
      // Check if Admin is logged in
      if (!adminController.findAdminBySessionId(sessionId)) {
        return ResponseEntity.badRequest().body("Please login first.");
      }
      try {
        return ResponseEntity.ok().body(accountRepository.findAll());
      } catch (Exception e) {
        return ResponseEntity.badRequest().body("Transaction failed.");
      }
    }

    // Create an Account
    @PostMapping("/users/{id}/accounts")
    public ResponseEntity<?> createAccount(@PathVariable(value="id") Long userId, @RequestParam("sessionId") String sessionId, @Valid @RequestBody Account account) {
      // Check if Admin is logged in
      if (!adminController.findAdminBySessionId(sessionId)) {
        return ResponseEntity.badRequest().body("Please login first.");
      }
      try {
        User user = userRepository.findById(userId)
        .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        return ResponseEntity.ok().body(accountRepository.save(new Account(user, account.getAccNumber(), account.getAccPin(), account.getBalance())));
      } catch (Exception e) {
        return ResponseEntity.badRequest().body("Transaction failed.");
      }
    }

    // Get an Account
    @GetMapping("/accounts/{id}")
    public ResponseEntity<?> getAccountById(@RequestParam("sessionId") String sessionId, @PathVariable(value="id") Long accountId) {
      // Check if Admin is logged in
      if (!adminController.findAdminBySessionId(sessionId)) {
        return ResponseEntity.badRequest().body("Please login first.");
      }

      try {
        return ResponseEntity.ok().body(accountRepository.findById(accountId)
            .orElseThrow(() -> new ResourceNotFoundException("Account", "id", accountId)));
      } catch (Exception e) {
        return ResponseEntity.badRequest().body("Transaction failed.");
      }
    }

    // Deposit to an Account
    @PutMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestParam("sessionId") String sessionId, @RequestBody Account accountDetails) {
      // Check if Admin is logged in
      if (!adminController.findAdminBySessionId(sessionId)) {
        return ResponseEntity.badRequest().body("Please login first.");
      }

      // Try to find by account number
      try {
        Account account = accountRepository.findByAccNumber(accountDetails.getAccNumber());
        account.setBalance(account.getBalance() + accountDetails.getBalance());
        Transaction transaction = transactionRepository.save(new Transaction(account, 1, accountDetails.getBalance(), true));
        Account updateAccount = accountRepository.save(account);
        return ResponseEntity.ok().body(updateAccount);
      } catch (Exception e) {
        return ResponseEntity.badRequest().body("Account not found");
      }
    }

    // Withdraw to an Account
    @PutMapping("/withdraw")
    public ResponseEntity<?> withdraw(@RequestParam("sessionId") String sessionId, @RequestBody Account accountDetails) {
      // Check if Admin is logged in
      if (!adminController.findAdminBySessionId(sessionId)) {
        return ResponseEntity.badRequest().body("Please login first.");
      }

      // Try to find by account number
      try {
        Account account = accountRepository.findByAccNumber(accountDetails.getAccNumber());
        account.setBalance(account.getBalance() - accountDetails.getBalance());
        Transaction transaction = transactionRepository.save(new Transaction(account, 2, accountDetails.getBalance(), true));
        Account updateAccount = accountRepository.save(account);
        return ResponseEntity.ok().body(updateAccount);
      } catch (Exception e) {
        return ResponseEntity.badRequest().body("Account not found");
      }
    }

    // Delete an Account
    @DeleteMapping("/accounts/{id}")
    public ResponseEntity<?> deleteAccount(@RequestParam("sessionId") String sessionId, @PathVariable(value="id") Long accountId) {
      // Check if Admin is logged in
      if (!adminController.findAdminBySessionId(sessionId)) {
        return ResponseEntity.badRequest().body("Please login first.");
      }

      try {
        Account account = accountRepository.findById(accountId)
            .orElseThrow(() -> new ResourceNotFoundException("Account", "id", accountId));
        accountRepository.delete(account);
        return ResponseEntity.ok().body("Account deleted.");
      } catch (Exception e) {
        return ResponseEntity.badRequest().body("Transaction failed.");
      }
    }
}
