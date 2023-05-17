package com.mindhub.homebanking.Dtos;

import com.mindhub.homebanking.Models.Transaction;
import com.mindhub.homebanking.Models.TransactionType;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

public class TransactionDTO {

    private long id;
    private double amount ;
    private TransactionType type;
    private String description;
    private LocalDateTime date;
    private Double balanceTransaction;

    public TransactionDTO(Transaction transaction) {
        this.id = transaction.getId();
        this.amount = transaction.getAmount();
        this.type = transaction.getType();
        this.description = transaction.getDescription();
        this.date = transaction.getDate();
        this.balanceTransaction= transaction.getBalanceTansaction();
    }

    public long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }
    public TransactionType getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Double getBalanceTransaction() {
        return balanceTransaction;
    }
}
