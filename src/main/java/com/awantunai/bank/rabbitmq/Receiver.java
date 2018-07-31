package com.awantunai.bank.rabbitmq;

import java.util.concurrent.CountDownLatch;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    private CountDownLatch latch = new CountDownLatch(1);
    private String session = "";

    public void receiveMessage(String message) {
        String [] split_message = message.split(";");

        // switch (split_message[0]) {
        //     case "login":
        //
        //     break;
        //
        // }
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }


}
