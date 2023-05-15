package com.mindhub.homebanking.Service;

import com.mindhub.homebanking.Models.ClientLoan;
import com.mindhub.homebanking.Models.Loan;

import java.util.Optional;

public interface ClientLoanService {

    void saveClientLoan( ClientLoan clientLoan);
    Optional<ClientLoan> findById(long id);

}
