package com.mindhub.homebanking;

import com.mindhub.homebanking.Models.*;
import com.mindhub.homebanking.Repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}


	LocalDateTime now = LocalDateTime.now();




	@Bean
	public CommandLineRunner initData(ClientRepository repository , AccountRepository accountRepository , TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository) {
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

			Loan loan1 = new Loan( "person", 100000, List.of( 6,12,24));
			Loan loan2 = new Loan("mortgage", 500000, List.of(12,24,36,48,60));
			Loan loan3 = new Loan("automotive", 300000,List.of(6,12,24,36));

			loanRepository.save(loan1);
			loanRepository.save(loan2);
			loanRepository.save(loan3);

			ClientLoan clientLoan1 = new ClientLoan(400000, 60, "mortgage");
			ClientLoan clientLoan2 = new ClientLoan(50000,12,"person");

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

			client1.addClientLoan(clientLoan1);
			loan1.addClientLoan(clientLoan1);
			clientLoanRepository.save(clientLoan1);

			client1.addClientLoan(clientLoan2);
			loan2.addClientLoan(clientLoan2);
			clientLoanRepository.save(clientLoan2);


		};
	}

}
