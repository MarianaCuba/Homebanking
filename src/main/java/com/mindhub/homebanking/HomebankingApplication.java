package com.mindhub.homebanking;

import com.mindhub.homebanking.Models.Account;
import com.mindhub.homebanking.Models.Transaction;
import com.mindhub.homebanking.Models.TransactionType;
import com.mindhub.homebanking.Repository.AccountRepository;
import com.mindhub.homebanking.Repository.ClientRepository;
import com.mindhub.homebanking.Models.Client;
import com.mindhub.homebanking.Repository.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

//	LocalDateTime day = LocalDateTime.now();
	LocalDateTime now = LocalDateTime.now();
/*	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd ");
	String formatDateTime = now.format(formatter);*/


	@Bean
	public CommandLineRunner initData(ClientRepository repository , AccountRepository accountRepository , TransactionRepository transactionRepository) {
		return (args) -> {
			// save a couple of customers
			Client client1 = new Client("Melba", "Morel","melba@mindhub.com");
			Client client2 = new Client("Mariana","Cuba","mari@gmail.com");

			Account account1= new Account("vin001", now,5000);
			Account account2= new Account("vin002",now.plusDays(1),7500);

			Transaction transaction1 = new Transaction(100.55, TransactionType.CREDIT,"varios",now);
			Transaction transaction2 = new Transaction(1500.98, TransactionType.DEBIT, "otros",now);
			Transaction transaction3 = new Transaction(1350.78, TransactionType.DEBIT,"clothes",now.plusDays(1));
			Transaction transaction4 = new Transaction(2000, TransactionType.CREDIT,"ingreso", now.plusDays(1));
			Transaction transaction5 = new Transaction(300,TransactionType.DEBIT, "candies",now);

			account1.addTransaction(transaction1);
			account1.addTransaction(transaction2);
			account1.addTransaction(transaction3);
			account2.addTransaction(transaction4);
			account2.addTransaction(transaction5);

			client1.addAccount(account1);
			client1.addAccount(account2);

			repository.save(client1);
			repository.save(client2);

			accountRepository.save(account1);
			accountRepository.save(account2);

			transactionRepository.save(transaction1);
			transactionRepository.save(transaction2);
			transactionRepository.save(transaction3);
			transactionRepository.save(transaction4);
			transactionRepository.save(transaction5);



		};
	}

}
