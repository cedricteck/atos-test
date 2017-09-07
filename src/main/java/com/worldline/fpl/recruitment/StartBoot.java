package com.worldline.fpl.recruitment;

import com.worldline.fpl.recruitment.dao.AccountRepository;
import com.worldline.fpl.recruitment.dao.TransactionRepository;
import com.worldline.fpl.recruitment.entity.Account;
import com.worldline.fpl.recruitment.entity.Transaction;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

/**
 * Application entry point
 * 
 * @author A525125
 *
 */
@SpringBootApplication
@Slf4j
public class StartBoot {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	public static void main(String[] args) {
		log.info("Start application ... ");
		SpringApplication.run(StartBoot.class, args);
	}

	@Bean
	InitializingBean sendDatabase() {
		return () -> {
			Account account1 = new Account();
			account1.setType("SAVING");
			account1.setNumber("01000251215");
			account1.setActive(true);
			account1.setCreationDate("2017-09-07");
			account1.setBalance(BigDecimal.valueOf(4210.42));
			accountRepository.save(account1);

			Account account2 = new Account();
			account2.setType("CURRENT");
			account2.setNumber("01000251216");
			account2.setActive(true);
			account2.setCreationDate("2017-09-07");
			account2.setBalance(BigDecimal.valueOf(25.12));
			accountRepository.save(account2);

			Transaction transaction = new Transaction();
			transaction.setAccount(account1);
			transaction.setBalance(BigDecimal.valueOf(42.12));
			transaction.setNumber("12151885120");
			transactionRepository.save(transaction);

			Transaction transaction2 = new Transaction();
			transaction2.setAccount(account1);
			transaction2.setBalance(BigDecimal.valueOf(456.00));
			transaction2.setNumber("12151885121");
			transactionRepository.save(transaction2);

			Transaction transaction3 = new Transaction();
			transaction3.setAccount(account1);
			transaction3.setBalance(BigDecimal.valueOf(-12.12));
			transaction3.setNumber("12151885122");
			transactionRepository.save(transaction3);


		};
	}
}

