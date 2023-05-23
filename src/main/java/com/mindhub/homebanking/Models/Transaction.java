package com.mindhub.homebanking.Models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private double amount ;
    private TransactionType type;
    private String description;
    private LocalDateTime date;
    private Double balanceTansaction;
    private boolean active;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account account;

    public Transaction(){

    }

    public Transaction(double amount, TransactionType type, String description, LocalDateTime date, Double balanceTansaction, boolean active) {
        this.amount = amount;
        this.type = type;
        this.description = description;
        this.date = date;
        this.balanceTansaction = balanceTansaction;
        this.active= active;

    }

    public long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Double getBalanceTansaction() {
        return balanceTansaction;
    }

    public void setBalanceTansaction(Double balanceTansaction) {
        this.balanceTansaction = balanceTansaction;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }


    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", amount=" + amount +
                ", type=" + type +
                ", description='" + description + '\'' +
                ", date=" + date +
                '}';
    }
}
