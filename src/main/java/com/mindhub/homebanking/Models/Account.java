package com.mindhub.homebanking.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.ManyToAny;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
@Entity
public class Account {

    private String number;
    private LocalDateTime creationDate;
    private double balance;
    private boolean active ;
    private AccountType accountType;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client")
    private Client client;
    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    private Set<Transaction> transactions= new HashSet<>();

    public Account() {

    }

    public Account(String number, LocalDateTime creationDate, double balance, boolean active, AccountType accountType) {
        this.number = number;
        this.creationDate = creationDate;
        this.balance = balance;
        this.active =active;
        this.accountType = accountType;

    }

    @JsonIgnore
    public Client getClient() {
        return client;
    }

    public long getId() {
        return id;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Set<Transaction> getTransaction() {
        return transactions;
    }
    public void addTransaction(Transaction transaction){
        transaction.setAccount(this);
        transactions.add(transaction);
    }
    public static String generaRandom(){
        int cuentaRandom = (int) (Math.random()*899999+100000);
        String numberAccount = "vin " + Integer.toString(cuentaRandom);
        return numberAccount;
    }

    @Override
    public String toString() {
        return "Account{" +
                "number='" + number + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", balance=" + balance +
                ", id=" + id +
                ", client=" + client +
                '}';
    }
}
