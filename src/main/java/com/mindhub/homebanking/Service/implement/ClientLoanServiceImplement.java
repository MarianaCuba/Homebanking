package com.mindhub.homebanking.Service.implement;

import com.mindhub.homebanking.Models.ClientLoan;
import com.mindhub.homebanking.Repository.ClientLoanRepository;
import com.mindhub.homebanking.Service.ClientLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientLoanServiceImplement implements ClientLoanService {

    @Autowired
    private ClientLoanRepository clientLoanRepository;

    @Override
    public void saveClientLoan(ClientLoan clientLoan) {
        clientLoanRepository.save(clientLoan);
    }

    @Override
    public Optional<ClientLoan> findById(long id) {
        return clientLoanRepository.findById(id);
    }
}
