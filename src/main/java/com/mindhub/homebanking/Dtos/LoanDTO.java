package com.mindhub.homebanking.Dtos;

import com.mindhub.homebanking.Models.Loan;

import java.util.List;

public class LoanDTO {
    private long id;
    private double amount;
    private List<Integer> payments;
    private String name;


    public LoanDTO(Loan loan) {
        this.id = loan.getId();
        this.amount = loan.getMaxAmount();
        this.payments = loan.getPayments();
        this.name = loan.getName();

    }

    public long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public List<Integer> getPayments() {
        return payments;
    }

    public String getName() {
        return name;
    }


}
