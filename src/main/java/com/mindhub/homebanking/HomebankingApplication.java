package com.mindhub.homebanking;

import com.mindhub.homebanking.Models.Account;
import com.mindhub.homebanking.Repository.AccountRepository;
import com.mindhub.homebanking.Repository.ClientRepository;
import com.mindhub.homebanking.Models.Client;
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
	public CommandLineRunner initData(ClientRepository repository , AccountRepository accountRepository) {
		return (args) -> {
			// save a couple of customers
			Client cliente1 = new Client("Melba", "Morel","melba@mindhub.com");
			repository.save(cliente1);
			Client client2 = new Client("Mariana","Cuba","mari@gmail.com");
			repository.save(client2);
			Account account1= new Account("vin001", now,5000,cliente1 );
			accountRepository.save(account1);
			Account account2= new Account("vin002",now.plusDays(1),7500, cliente1);
			accountRepository.save(account2);
			Account account3= new Account("vin003",now,5000,client2);
			accountRepository.save(account3);

		};
	}

}
