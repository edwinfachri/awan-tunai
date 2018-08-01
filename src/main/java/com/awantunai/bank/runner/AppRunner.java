package com.awantunai.bank.runner;

import com.awantunai.bank.service.AdminService;
import com.awantunai.bank.service.UserService;
import com.awantunai.bank.service.AccountService;
import com.awantunai.bank.service.TransactionService;
import com.awantunai.bank.BankApplication;

import com.awantunai.bank.model.Transaction;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;


import com.awantunai.bank.rabbitmq.Receiver;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import java.util.concurrent.TimeUnit;


import java.lang.Long;
import java.util.List;
import java.util.Iterator;

@Component
class AppRunner implements CommandLineRunner {

    private final static Logger logger = LoggerFactory.getLogger(AppRunner.class);

    private final AdminService adminService;
    private final UserService userService;
    private final AccountService accountService;
    private final TransactionService transactionService;
    private final BankApplication bankApplication;

    private final RabbitTemplate rabbitTemplate;
    private final Receiver receiver;

    public AppRunner(AdminService adminService,
                     UserService userService,
                     AccountService accountService,
                     TransactionService transactionService,

                     BankApplication bankApplication,
                     Receiver receiver,
                     RabbitTemplate rabbitTemplate) {
        this.adminService = adminService;
        this.userService = userService;
        this.accountService = accountService;
        this.transactionService = transactionService;
        this.receiver = receiver;
        this.rabbitTemplate = rabbitTemplate;
        this.bankApplication = bankApplication;
    }

    // AppRunner Works as runner to the rabbitmq

    @Override
    public void run(String... args) throws Exception {
        adminService.createAdmin("edwin", "edwin12", Long.valueOf(1));
        Integer adminCount = adminService.countAdmin();
        adminService.createAdmin("edwin", "edwin12", Long.valueOf(1));
        Assert.isTrue(adminService.countAdmin() == adminCount,
                "Create second admin with the same username and assert the data number is not changing");

        Integer userCount = userService.countUser();
        userService.createUser("Edwin", "Wicaksono", "Taman Anyelir", "1994-09-17", "085959336191");
        Assert.isTrue(userService.countUser() > userCount,
                "Create user and assert that the data number is changing");

        accountService.createAccount("1234567890", "123456", 500000, Long.valueOf(1));
        Integer accountCount = accountService.countAccount();
        accountService.createAccount("1234567890", "123456", 500000, Long.valueOf(1));
        Assert.isTrue(accountService.countAccount() == accountCount,
                "Create second account with the same accountnumber and assert the data number is not changing");

        Integer transactionCount = transactionService.countTransaction();
        transactionService.transfer("1234567890", 5000, "1234567890", "coba");
        transactionService.withdraw("1234567890", 1000);
        transactionService.deposit("1234567890", 50);
        Assert.isTrue(transactionService.countTransaction() == transactionCount + 3,
                "Create transaction and assert that the data number is changing");

        Iterator all_transactions = transactionService.findAllTransactions();
        while (all_transactions.hasNext()) {
          Object transaction = all_transactions.next();
          logger.info(transaction.toString());
        }

        Iterator transactions = transactionService.findTransactions("1234567890");
        while (transactions.hasNext()) {
          Object transaction = transactions.next();
          logger.info(transaction.toString());
        }

        rabbitTemplate.convertAndSend(bankApplication.topicExchangeName, "foo.bar.baz", "login;edwin;edwin12");
        rabbitTemplate.convertAndSend(bankApplication.topicExchangeName, "foo.bar.baz", "create admin;rabbitmq;rabbitmq12;1");
        rabbitTemplate.convertAndSend(bankApplication.topicExchangeName, "foo.bar.baz", "create user;coba nama;nama coba;ini alamatnya;1994-09-17;085959336191");
        rabbitTemplate.convertAndSend(bankApplication.topicExchangeName, "foo.bar.baz", "create account;0987654321;123456;500000;1");
        rabbitTemplate.convertAndSend(bankApplication.topicExchangeName, "foo.bar.baz", "transfer;1234567890;5000;0987654321;coba");
        rabbitTemplate.convertAndSend(bankApplication.topicExchangeName, "foo.bar.baz", "deposit;1234567890;500");
        rabbitTemplate.convertAndSend(bankApplication.topicExchangeName, "foo.bar.baz", "withdraw;0987654321;200");
        rabbitTemplate.convertAndSend(bankApplication.topicExchangeName, "foo.bar.baz", "logout");
        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);

    }



}
