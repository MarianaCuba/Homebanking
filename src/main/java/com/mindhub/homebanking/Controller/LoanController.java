package com.mindhub.homebanking.Controller;

import com.mindhub.homebanking.Dtos.ClientDTO;
import com.mindhub.homebanking.Dtos.ClientLoanDTO;
import com.mindhub.homebanking.Dtos.LoanApplicationDTO;
import com.mindhub.homebanking.Dtos.LoanDTO;
import com.mindhub.homebanking.Models.*;
import com.mindhub.homebanking.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.accessibility.Accessible;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestController
public class LoanController {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private ClientLoanRepository clientLoanRepository;

    @RequestMapping("/api/loans")
    public List<LoanDTO> getLoans(){
        return loanRepository.findAll().stream().map(loan -> new LoanDTO(loan)).collect(Collectors.toList());
    }
    @Transactional
    @RequestMapping(path = "/api/loans", method = RequestMethod.POST)
    public ResponseEntity<Object> newLoans (Authentication authentication, @RequestBody LoanApplicationDTO loanApplicationDTO){

        Client client = clientRepository.findByEmail(authentication.getName());
        Loan loan = loanRepository.findById(loanApplicationDTO.getId()).orElse(null);
        Account account = accountRepository.findByNumber(loanApplicationDTO.getNumberAccountDestinate());
        ClientLoan clientLoan1 = clientLoanRepository.findById(loan.getId()).orElse(null);

        List<Loan> loans = new ArrayList<>();
/*        for (Loan loan2 : clientLoanRepository.getReferenceById(client.getId()).getLoan()) {
            int longitud = clientLoanRepository.getReferenceById(client.getId()).getLoan()).size();

            loans.add(clientLoanRepository.getReferenceById(client.getId()).getLoan());
            System.out.println("holaaaaaaaaaaaaaaaaaaaaaaaaaaa" + loans);
        }*/


        if (loan == null){
            return new ResponseEntity<>("The loan doesn't exist", FORBIDDEN);
        }
        if (account == null){
            return new ResponseEntity<>("The account doesn't exist", FORBIDDEN);
        }
        if (loanApplicationDTO.getAmount() > loan.getMaxAamount()){
            return new ResponseEntity<>("the amount exceeds the maximum of the loan", FORBIDDEN);
        }
        if (loanApplicationDTO.getAmount()<=0){
            return new ResponseEntity<>("they cannot be negative or zero", FORBIDDEN);
        }
        if (!loan.getPayments().contains(loanApplicationDTO.getPayments())){
            return new ResponseEntity<>("cuotas no validas", FORBIDDEN);
        }
        if (!client.getAccounts().contains(account)){
            return new ResponseEntity<>("this account is not yours", FORBIDDEN);
        }
        if (String.valueOf(loanApplicationDTO.getPayments()).isBlank()){
            return new ResponseEntity<>("esta en blanco o vacio", FORBIDDEN);
        }
        if (String.valueOf(loanApplicationDTO.getAmount()).isBlank()){
            return new ResponseEntity<>("Please enter an account", FORBIDDEN);
        }
        if (clientLoan1 != null){
            return new ResponseEntity<>("ya tenes uno de este tipo ", FORBIDDEN);
        }



        ClientLoan clientLoan = new ClientLoan(loanApplicationDTO.getAmount() + (loanApplicationDTO.getAmount()*0.2), loanApplicationDTO.getPayments());
        clientLoanRepository.save(clientLoan);

        Transaction transaction = new Transaction(loanApplicationDTO.getAmount(), TransactionType.CREDIT,loan.getName()+"loan approved", LocalDateTime.now());

        account.setBalance(account.getBalance() + loanApplicationDTO.getAmount());
        account.addTransaction(transaction);

        loan.addClientLoan(clientLoan);
        client.addClientLoan(clientLoan);
        clientRepository.save(client);


        return new ResponseEntity<>("Successful loan",CREATED);
    }
}
