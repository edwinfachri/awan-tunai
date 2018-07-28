package com.awantunai.bank;

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

	public static void main(String[] args) {
		SpringApplication.run(BankApplication.class, args);
	}

	@Bean
	CommandLineRunner init(AccountRepository accountRepository,
						   AdminRepository adminRepository, UserRepository userRepository) throws Exception{
		SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMdd");
		Date date=formatter.parse("19940917");
		return args ->
			Arrays.asList("edwinn", "facrii", "wicaksono")
				.forEach(id -> {
					adminRepository.save(new Admin(id, id, Long.valueOf(1), id, 1));
			    User user = userRepository.save(new User(id, id, date, "123321", "Homeee"));
			    accountRepository.save(new Account(user, "1233211233", "123321", 5000));
				});
	}
}
