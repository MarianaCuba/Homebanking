package com.mindhub.homebanking.Dtos;

import com.mindhub.homebanking.Models.ClientLoan;

public class ClientLoanDTO {

    private long clientLoanId; // id del clientLoan
    private long loanId;
    private String name;
    private double amount;
    private int payments;

    private Double amountTotal;

    public ClientLoanDTO(ClientLoan clientLoan) {
        this.clientLoanId = clientLoan.getId();
        this.loanId = clientLoan.getLoan().getId();
        this.amount = clientLoan.getAmount();
        this.payments = clientLoan.getPayments();
        this.name=clientLoan.getLoan().getName();
        this.amountTotal =clientLoan.getAmountTotal();
    }

    public long getClientLoanId() {
        return clientLoanId;
    }

    public long getLoanId() {
        return loanId;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public int getPayments() {
        return payments;
    }

    public Double getAmountTotal() {
        return amountTotal;
    }
}
