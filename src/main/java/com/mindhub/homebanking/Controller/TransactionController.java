package com.mindhub.homebanking.Controller;


import com.mindhub.homebanking.Models.Account;
import com.mindhub.homebanking.Models.Client;
import com.mindhub.homebanking.Models.Transaction;
import com.mindhub.homebanking.Models.TransactionType;
import com.mindhub.homebanking.Repository.AccountRepository;
import com.mindhub.homebanking.Repository.ClientRepository;
import com.mindhub.homebanking.Repository.TransactionRepository;
import com.mindhub.homebanking.Service.AccountService;
import com.mindhub.homebanking.Service.ClientService;
import com.mindhub.homebanking.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TransactionController {
/*    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;*/

    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionService transactionService;

    @Transactional
    @PostMapping("/api/clients/current/transactions")
    public ResponseEntity<Object> newTransaction (
            Authentication authentication, @RequestParam Double amount, @RequestParam String description,
            @RequestParam String initialAccount, @RequestParam String destinateAccount){

        Client client = clientService.findByEmail(authentication.getName());
        Account accountInitial = accountService.findByNumber(initialAccount.toUpperCase());
        Account accountDestinate = accountService.findByNumber(destinateAccount.toUpperCase());

        // amount :
        if (amount == null || amount == 0 || amount.isNaN()){
             return new ResponseEntity<>("Please enter an amount",HttpStatus.FORBIDDEN);
        } else if (amount < 0) {
            return new ResponseEntity<>("Please enter an positive amount ",HttpStatus.FORBIDDEN);
        }

        //description :
        if (description.isBlank()){
            return new ResponseEntity<>("Please enter an description",HttpStatus.FORBIDDEN);
        }
        //initialAccount :
        if (initialAccount.isBlank()){
            return new ResponseEntity<>("Please enter an account ",HttpStatus.FORBIDDEN);
        } else if (accountInitial == null) {
            return new ResponseEntity<>("This account doesn't exist ",HttpStatus.FORBIDDEN);
        } else if (client.getAccounts().stream().filter(account -> account.getNumber().equalsIgnoreCase(initialAccount)).collect(Collectors.toList()).size()==0) {
            return new ResponseEntity<>("this account is not yours",HttpStatus.FORBIDDEN);
        } else if (accountInitial.getBalance() < amount) {
            return new ResponseEntity<>("insufficient balance  ",HttpStatus.FORBIDDEN);
        }


        //destinateAccount :
        if (destinateAccount.isBlank()){
            return new ResponseEntity<>("Please enter an account ",HttpStatus.FORBIDDEN);
        } else if (accountDestinate == null) {
            return new ResponseEntity<>("This account doesn't exist ",HttpStatus.FORBIDDEN);
        } else if (initialAccount.equalsIgnoreCase(destinateAccount)) {
            return new ResponseEntity<>("you can't send money to the same account ",HttpStatus.FORBIDDEN);
        }

        Transaction debitTransaction = new Transaction( amount,TransactionType.DEBIT, description, LocalDateTime.now());
        Transaction creditTransaction = new Transaction( amount, TransactionType.CREDIT, description,LocalDateTime.now());

        accountInitial.addTransaction(debitTransaction);
        accountDestinate.addTransaction(creditTransaction);

        accountInitial.setBalance(accountInitial.getBalance()-amount);
        accountDestinate.setBalance(accountDestinate.getBalance()+amount);

        transactionService.saveTransaction(debitTransaction);
        transactionService.saveTransaction(creditTransaction);


        return new ResponseEntity<>("Transaction ok (Y) ",HttpStatus.CREATED);
    }

}
