package com.awantunai.bank;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import com.awantunai.bank.rabbitmq.Receiver;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import com.awantunai.bank.repository.UserRepository;
import com.awantunai.bank.repository.AccountRepository;
import com.awantunai.bank.repository.AdminRepository;
import com.awantunai.bank.model.User;
import com.awantunai.bank.model.Account;
import com.awantunai.bank.model.Admin;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
@EnableJpaAuditing
public class BankApplication {

		public static final String topicExchangeName = "spring-boot-exchange";

    static final String queueName = "spring-boot";

    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(topicExchangeName);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("awantunai.#");
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
            MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

		public static void main(String[] args) {
			SpringApplication.run(BankApplication.class, args);
		}

		// @Bean
		// CommandLineRunner init(AccountRepository accountRepository,
		// 					   AdminRepository adminRepository, UserRepository userRepository) throws Exception{
		// 	SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMdd");
		// 	Date date=formatter.parse("19940917");
		// 	return args ->
		// 		Arrays.asList("edwinn", "facrii", "wicaksono")
		// 			.forEach(id -> {
		// 				adminRepository.save(new Admin(id, id, Long.valueOf(1), id, 1));
		// 		    User user = userRepository.save(new User(id, id, date, "123321", "Homeee"));
		// 		    accountRepository.save(new Account(user, "1233211233", "123321", 5000));
		// 			});
		// }
}
