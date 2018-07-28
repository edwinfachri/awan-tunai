package com.awantunai.bank.controller;

import com.awantunai.bank.helper.ResourceNotFoundException;
import com.awantunai.bank.model.Transaction;
import com.awantunai.bank.model.Account;
import com.awantunai.bank.repository.TransactionRepository;
import com.awantunai.bank.repository.AccountRepository;
import com.awantunai.bank.controller.AdminController;
import com.awantunai.bank.controller.AccountController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/awantunai")
public class TransactionController {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AdminController adminController = new AdminController();

    @Autowired
    AccountController accountController = new AccountController();

    // Get All Transactions
    @GetMapping("/transactions")
    public ResponseEntity<?> getAllTransaction(@RequestParam("sessionId") String sessionId) {
      // Check if Admin is logged in
      if (!adminController.findAdminBySessionId(sessionId)) {
        return ResponseEntity.badRequest().body("Please login first.");
      }

      try {
        return ResponseEntity.ok().body(transactionRepository.findAll());
      } catch (Exception e) {
        return ResponseEntity.badRequest().body("Transaction failed.");
      }
    }

    // Create a Transaction
    @PostMapping("/transactions")
    public ResponseEntity<?> createTransaction(@RequestParam("sessionId") String sessionId, @Valid @RequestBody Transaction transaction) {
      // Check if Admin is logged in
      if (!adminController.findAdminBySessionId(sessionId)) {
        return ResponseEntity.badRequest().body("Please login first.");
      }

      try {
        return ResponseEntity.ok().body(transactionRepository.save(transaction));
      } catch (Exception e) {
        return ResponseEntity.badRequest().body("Transaction failed.");
      }
    }

    // // Create a Transfer Transaction
    // @PostMapping("/transactions/transfer")
    // public ResponseEntity<?> transfer(@RequestParam("sessionId") String sessionId, @Valid @RequestBody Transaction transaction) {
    //   // Check if Admin is logged in
    //   if (!adminController.findAdminBySessionId(sessionId)) {
    //     return ResponseEntity.badRequest().body("Please login first.");
    //   }
    //
    //   try {
    //     Account sender = accountRepository.findByAccNumber(transaction.getAccNumber());
    //     sender.setBalance(sender.getBalance() - transaction.getAmount());
    //     Account receiver = accountRepository.findByAccNumber(transaction.getDestination());
    //     receiver.setBalance(receiver.getBalance() + transaction.getAmount());
    //     Account updateSender = accountRepository.save(sender);
    //     Account updateReceiver = accountRepository.save(receiver);
    //     return ResponseEntity.ok().body(transactionRepository.save(transaction));
    //   } catch (Exception e) {
    //     return ResponseEntity.badRequest().body("Transaction failed.");
    //   }
    // }

    // Get a Transaction
    @GetMapping("/transactions/{id}")
    public ResponseEntity<?> getTransactionById(@RequestParam("sessionId") String sessionId, @PathVariable(value="id") Long transactionId) {
      // Check if Admin is logged in
      if (!adminController.findAdminBySessionId(sessionId)) {
        return ResponseEntity.badRequest().body("Please login first.");
      }
      try {
        return ResponseEntity.ok().body(transactionRepository.findById(transactionId)
            .orElseThrow(() -> new ResourceNotFoundException("Transaction", "id", transactionId)));
      } catch (Exception e) {
        return ResponseEntity.badRequest().body("Transaction failed.");
      }
    }

    // // Modify Transaction
    // @PutMapping("/transactions/deposit/{id}")
    // public Transaction editTransaction(@PathVariable(value="id") Long transactionId,
    //     @Valid @RequestBody Transaction transactionDetails) {
    //   Transaction transaction = transactionRepository.findById(transactionId)
    //       .orElseThrow(() -> new ResourceNotFoundException("Transaction", "id", transactionId));
    //   transaction.setType(transactionDetails.getType());
    //   transaction.setAmount(transactionDetails.getAmount());
    //   transaction.setDestination(transactionDetails.getDestination());
    //   transaction.setNote(transactionDetails.getNote());
    //   transaction.setStatus(transactionDetails.getStatus());
    //   Transaction updateTransaction = transactionRepository.save(transaction);
    //   return updateTransaction;
    // }
    //
    //
    // // Delete a Transaction
    // @DeleteMapping("/transactions/{id}")
    // public ResponseEntity<?> deleteTransaction(@PathVariable(value="id") Long transactionId) {
    //   Transaction transaction = transactionRepository.findById(transactionId)
    //       .orElseThrow(() -> new ResourceNotFoundException("Transaction", "id", transactionId));
    //
    //   transactionRepository.delete(transaction);
    //
    //   return ResponseEntity.ok().build();
    // }
}
