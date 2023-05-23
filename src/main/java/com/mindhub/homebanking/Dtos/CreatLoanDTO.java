package com.mindhub.homebanking.Dtos;

import java.util.List;

public class CreatLoanDTO {
    private String name;
    private double amount;
    private List<Integer> payments;
    private double interest;

    public CreatLoanDTO (String name, double amount, List<Integer> payments, double interest){
    this.name = name;
    this.amount = amount;
    this.payments = payments;
    this.interest = interest;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public List<Integer> getPayments() {
        return payments;
    }

    public double getInterest() {
        return interest;
    }
}
