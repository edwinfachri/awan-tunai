package com.awantunai.bank.controller;

import com.awantunai.bank.exception.ResourceNotFoundException;
import com.awantunai.bank.model.Transaction;
import com.awantunai.bank.model.Account;
import com.awantunai.bank.repository.TransactionRepository;
import com.awantunai.bank.repository.AccountRepository;
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

    // Get All Transactions
    @GetMapping("/transactions")
    public List<Transaction> getAllTransaction() {
      return transactionRepository.findAll();
    }

    // Create a Transaction
    @PostMapping("/transactions")
    public Transaction createTransaction(@Valid @RequestBody Transaction transaction) {
      return transactionRepository.save(transaction);
    }

    // Create a Transfer Transaction
    @PostMapping("/transactions/transfer")
    public Transaction transfer(@Valid @RequestBody Transaction transaction) {
      Account sender = accountRepository.findByAccNumber(transaction.getAccNumber());
      sender.setBalance(sender.getBalance() - transaction.getAmount());
      Account receiver = accountRepository.findByAccNumber(transaction.getDestination());
      receiver.setBalance(receiver.getBalance() + transaction.getAmount());
      Account updateSender = accountRepository.save(sender);
      Account updateReceiver = accountRepository.save(receiver);
      return transactionRepository.save(transaction);
    }

    // Get a Transaction
    @GetMapping("/transactions/{id}")
    public Transaction getTransactionById(@PathVariable(value="id") Long transactionId) {
      return transactionRepository.findById(transactionId)
          .orElseThrow(() -> new ResourceNotFoundException("Transaction", "id", transactionId));
    }

    // Modify Transaction
    @PutMapping("/transactions/deposit/{id}")
    public Transaction editTransaction(@PathVariable(value="id") Long transactionId,
        @Valid @RequestBody Transaction transactionDetails) {
      Transaction transaction = transactionRepository.findById(transactionId)
          .orElseThrow(() -> new ResourceNotFoundException("Transaction", "id", transactionId));
      transaction.setType(transactionDetails.getType());
      transaction.setAmount(transactionDetails.getAmount());
      transaction.setDestination(transactionDetails.getDestination());
      transaction.setNote(transactionDetails.getNote());
      transaction.setStatus(transactionDetails.getStatus());
      Transaction updateTransaction = transactionRepository.save(transaction);
      return updateTransaction;
    }


    // Delete a Transaction
    @DeleteMapping("/transactions/{id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable(value="id") Long transactionId) {
      Transaction transaction = transactionRepository.findById(transactionId)
          .orElseThrow(() -> new ResourceNotFoundException("Transaction", "id", transactionId));

      transactionRepository.delete(transaction);

      return ResponseEntity.ok().build();
    }
}
