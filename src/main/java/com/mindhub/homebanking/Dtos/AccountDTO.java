package com.mindhub.homebanking.Dtos;

import com.mindhub.homebanking.Models.Account;
import com.mindhub.homebanking.Models.AccountType;

import javax.persistence.SecondaryTables;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {
    private long id;
    private String number;
    private LocalDateTime creationDate;
    private double balance;
    private boolean active;
    private AccountType accountType;
    private Set<TransactionDTO> transaction;


    public AccountDTO(Account account) {
        this.id = account.getId();
        this.number = account.getNumber();
        this.creationDate = account.getCreationDate();
        this.balance = account.getBalance();
        this.transaction = account.getTransaction().stream().map(transaction -> new TransactionDTO(transaction)).collect(Collectors.toSet());
        this.active = account.isActive();
        this.accountType = account.getAccountType();
    }


    public long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }


    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public double getBalance() {
        return balance;
    }

    public boolean isActive() {
        return active;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public Set<TransactionDTO> getTransaction() {
        return transaction;
    }

}
