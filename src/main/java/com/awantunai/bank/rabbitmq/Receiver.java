package com.awantunai.bank.rabbitmq;

import java.util.concurrent.CountDownLatch;
import org.springframework.stereotype.Component;
import com.awantunai.bank.service.AdminService;
import com.awantunai.bank.service.UserService;
import com.awantunai.bank.service.AccountService;
import com.awantunai.bank.service.TransactionService;

@Component
public class Receiver {

    private CountDownLatch latch = new CountDownLatch(1);
    private String session = "";
    private final AdminService adminService;
    private final UserService userService;
    private final AccountService accountService;
    private final TransactionService transactionService;

    public Receiver(AdminService adminService,
                     UserService userService,
                     AccountService accountService,
                     TransactionService transactionService)
    {
        this.adminService = adminService;
        this.userService = userService;
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    public void receiveMessage(String message) throws Exception{
        String [] split_message = message.split(";");
        switch (split_message[0]) {
            case "login":
                this.session = adminService.login(split_message[1], split_message[2]);
            break;
            case "create admin":
                if (this.session.equals("")) {
                  break;
                }
                adminService.createAdmin(split_message[1], split_message[2], Long.parseLong(split_message[3]));
            break;
                case "create user":
                if (this.session.equals("")) {
                  break;
                }
                userService.createUser(split_message[1], split_message[2], split_message[3], split_message[4], split_message[5]);
            break;
                case "create account":
                if (this.session.equals("")) {
                  break;
                }
                accountService.createAccount(split_message[1], split_message[2], Integer.parseInt(split_message[3]), Long.parseLong(split_message[4]));
            break;
                case "transfer":
                if (this.session.equals("")) {
                  break;
                }
                transactionService.transfer(split_message[1], Integer.parseInt(split_message[2]), split_message[3], split_message[4]);
            break;
                case "deposit":
                if (this.session.equals("")) {
                  break;
                }
                transactionService.deposit(split_message[1], Integer.parseInt(split_message[2]));
            break;
                case "withdraw":
                if (this.session.equals("")) {
                  break;
                }
                transactionService.withdraw(split_message[1], Integer.parseInt(split_message[2]));
            break;
            case "logout":
                this.session = "";
            break;


        }
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }


}
