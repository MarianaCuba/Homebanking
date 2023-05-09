package com.mindhub.homebanking.Dtos;

public class LoanApplicationDTO {
    private long id; // id del prestamo
    private double amount;
    private int payments;
    private String numberAccountDestinate;


    public LoanApplicationDTO (long id, double amount, int payments, String numberAccountDestinate) {
        this.id = id;
        this.amount = amount;
        this.payments = payments;
        this.numberAccountDestinate = numberAccountDestinate;
    }

    public long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public int getPayments() {
        return payments;
    }

    public String getNumberAccountDestinate() {
        return numberAccountDestinate;
    }
}
