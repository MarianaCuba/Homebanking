package com.mindhub.homebanking.Controller;


import com.mindhub.homebanking.Dtos.LoanApplicationDTO;
import com.mindhub.homebanking.Dtos.LoanDTO;
import com.mindhub.homebanking.Models.*;

import com.mindhub.homebanking.Service.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@CrossOrigin(origins = {"*"})
@RestController
public class LoanController {


    @Autowired
    private LoanService loanService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private ClientLoanService clientLoanService;
    @Autowired
    private TransactionService transactionService;


    @GetMapping("/api/loans")
    public List<LoanDTO> getLoans(){
        return loanService.getLoanDto();
    }
    @Transactional
    @PostMapping("/api/loans")
    public ResponseEntity<Object> newLoans (Authentication authentication, @RequestBody LoanApplicationDTO loanApplicationDTO){

        Client client = clientService.findByEmail(authentication.getName());
        Loan loan = loanService.findById(loanApplicationDTO.getId()).orElse(null);
        Account account = accountService.findByNumber(loanApplicationDTO.getNumberAccountDestinate());
        ClientLoan clientLoan1 = clientLoanService.findById(loan.getId()).orElse(null);


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

        ClientLoan clientLoan = new ClientLoan(loanApplicationDTO.getAmount(), loanApplicationDTO.getPayments(), (loanApplicationDTO.getAmount()*0.2));
        clientLoanService.saveClientLoan(clientLoan);

        Transaction transaction = new Transaction(loanApplicationDTO.getAmount(), TransactionType.CREDIT,loan.getName()+"loan approved", LocalDateTime.now(),account.getBalance() + loanApplicationDTO.getAmount());

        account.setBalance(account.getBalance() + loanApplicationDTO.getAmount());
        account.addTransaction(transaction);

        loan.addClientLoan(clientLoan);
        client.addClientLoan(clientLoan);
        clientService.saveClient(client);


        return new ResponseEntity<>("Successful loan",CREATED);
    }

    @Transactional
    @PostMapping ("/api/loans/pay")
    public ResponseEntity<Object> paymentsLoan( Authentication authentication, @RequestParam Long idLoan, @RequestParam Double amount, @RequestParam String account){
        Client client = clientService.findByEmail(authentication.getName());
        ClientLoan clientLoan = clientLoanService.findById(idLoan).orElse(null);
        Account accountA = accountService.findByNumber(account);


        if(clientLoan == null){
            return new ResponseEntity<>(" o.0",FORBIDDEN);
        }
        if (account.isBlank()){
            return new ResponseEntity<>(" vacio ",FORBIDDEN);
        }
        if (amount <= 0 ){
            return new ResponseEntity<>(" no pueden ser montos negativos ni 0  ",FORBIDDEN);
        }
        if (accountA.getBalance()<amount){
            return new ResponseEntity<>(" no tenes suficientes fondos ",FORBIDDEN);
        }

        accountA.setBalance(accountA.getBalance()-amount);
        clientLoan.setAmountTotal(clientLoan.getAmountTotal()-amount);

        String description = "pay" + clientLoan.getLoan().getName() + "loan";

        Transaction transaction = new Transaction(amount,TransactionType.DEBIT,description,LocalDateTime.now(),accountA.getBalance());
        accountA.addTransaction(transaction);
        transactionService.saveTransaction(transaction);
        double newBalance= accountA.getBalance() - amount;
        accountA.setBalance(newBalance);
        accountService.saveAccount(accountA);

       if (amount < clientLoan.getAmountTotal()){
            clientLoan.setPayments(clientLoan.getPayments()-1);
        }else {
            clientLoan.setPayments(0);
        }

    return new ResponseEntity<>("(Y)",CREATED);
    }

}
