package com.mindhub.homebanking.Service;

import com.mindhub.homebanking.Dtos.LoanDTO;
import com.mindhub.homebanking.Models.Loan;

import java.util.List;
import java.util.Optional;

public interface LoanService {

    Optional<Loan> findById(long id);
    List<LoanDTO> getLoanDto ();


}
