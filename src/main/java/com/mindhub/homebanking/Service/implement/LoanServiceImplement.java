package com.mindhub.homebanking.Service.implement;

import com.mindhub.homebanking.Dtos.LoanDTO;
import com.mindhub.homebanking.Models.Loan;
import com.mindhub.homebanking.Repository.LoanRepository;
import com.mindhub.homebanking.Service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LoanServiceImplement implements LoanService {
    @Autowired
    private LoanRepository loanRepository;

    @Override
    public Optional<Loan> findById(long id) {
        return loanRepository.findById(id);
    }

    @Override
    public List<LoanDTO> getLoanDto() {
        return loanRepository.findAll().stream().map(loan -> new LoanDTO(loan)).collect(Collectors.toList());
    }

    @Override
    public void saveLoan(Loan loan) {
        loanRepository.save(loan);
    }
}
