package com.mindhub.homebanking;

import com.mindhub.homebanking.Models.*;
import com.mindhub.homebanking.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;


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
	double cuentaRamdon = Math.random();
	String numeroCuenta = "vin" + Double.toString(cuentaRamdon);

	@Autowired
	private PasswordEncoder passwordEncoder;


	@Bean
	public CommandLineRunner initData(ClientRepository repository , AccountRepository accountRepository , TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository) {
		return (args) -> {
			// save a couple of customers
			Client client1 = new Client("Melba", "Morel","melba@mindhub.com", passwordEncoder.encode("asd234"));
			Client client2 = new Client("Mariana","Cuba","mari04@gmail.com",passwordEncoder.encode("asd124"));

			Account account1= new Account("VIN 0001", now,5000,true,AccountType.SAVINGS);
			Account account2= new Account("VIN 0002",now.plusDays(1),7500,true, AccountType.CURRENT);

			Account accountResgister = new Account(numeroCuenta, now, 0, true, AccountType.SAVINGS);

			Transaction transaction1 = new Transaction(100.55, TransactionType.CREDIT,"varios",now,1000.0,true);
			Transaction transaction2 = new Transaction(1500.98, TransactionType.DEBIT, "otros",now,1000.0,true);
			Transaction transaction3 = new Transaction(1350.78, TransactionType.DEBIT,"clothes",now.plusDays(1),13000.0,true);
			Transaction transaction4 = new Transaction(2000, TransactionType.CREDIT,"ingreso", now.plusDays(1),1400.0,true);
			Transaction transaction5 = new Transaction(300,TransactionType.DEBIT, "candies",now,5000.0,true);

			Loan loan1 = new Loan( "person", 100000, List.of( 6,12,24), 1.10);
			Loan loan2 = new Loan("mortgage", 500000, List.of(12,24,36,48,60),1.20);
			Loan loan3 = new Loan("automotive", 300000,List.of(6,12,24,36),1.30);

			loanRepository.save(loan1);
			loanRepository.save(loan2);
			loanRepository.save(loan3);

			ClientLoan clientLoan1 = new ClientLoan(400000, 60,420000.0);
			ClientLoan clientLoan2 = new ClientLoan(50000,12,52000.0);

			Card card1 = new Card("Melba Morel", CardType.CREDIT, CardColor.GOLD, "3609-2212-0521-1007",642, LocalDate.now().minusYears(5), LocalDate.now().minusDays(1),true);
			Card card2 = new Card("Melba Morel", CardType.DEBIT,CardColor.TITANIUM,"4789-5678-9231-2204", 569, LocalDate.now(), LocalDate.now().plusYears(5),true);

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

			client1.addCard(card1);
			client1.addCard(card2);
			cardRepository.save(card1);
			cardRepository.save(card2);

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
