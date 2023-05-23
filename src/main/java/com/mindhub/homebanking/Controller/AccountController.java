package com.mindhub.homebanking.Controller;

import com.mindhub.homebanking.Dtos.AccountDTO;
import com.mindhub.homebanking.Dtos.ClientDTO;
import com.mindhub.homebanking.Models.*;
import com.mindhub.homebanking.Repository.AccountRepository;
import com.mindhub.homebanking.Repository.ClientRepository;
import com.mindhub.homebanking.Service.AccountService;
import com.mindhub.homebanking.Service.ClientService;
import com.mindhub.homebanking.Util.AccountUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;
@RestController

public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private ClientService clientService;

    @GetMapping("api/clients/current/accounts")
    public List<AccountDTO> getAccounts(Authentication authentication) {
        return accountService.getAccountsAuthentication(authentication);
    }

    @GetMapping("api/clients/current/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id) {
        return accountService.getAccountId(id);

    }


    @PostMapping("api/clients/current/accounts")
    public ResponseEntity<Object> newAccount(Authentication authentication , @RequestParam AccountType accountType) {

        Client clientAuth= clientService.findByEmail(authentication.getName());
        List<Account> accountsActive= clientAuth.getAccounts().stream().filter(account -> account.isActive()).collect(toList());
        String accountAleatory;
        do {
            accountAleatory = AccountUtil.generaRandom();
        } while (accountService.findByNumber(accountAleatory) != null);

        if (accountsActive.size() <= 2) {
            Account accountCreation = new Account(accountAleatory, LocalDateTime.now(), 0,true, AccountType.SAVINGS);
            clientService.findByEmail(authentication.getName()).addAccount(accountCreation);
            accountService.saveAccount(accountCreation);
        } else {
            return new ResponseEntity<>("You cannot have more than three accounts.", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/api/clients/current/deleteaccount")
    public ResponseEntity<Object> deleteAccount (Authentication authentication , @RequestParam long id){
        Client client = clientService.findByEmail(authentication.getName());
        Account account = accountService.findById(id);

        if (account == null){
            return new ResponseEntity<>("this account not exist",HttpStatus.FORBIDDEN);
        } else if (account.getBalance()>0) {
            return new ResponseEntity<>("this account has funds",HttpStatus.FORBIDDEN);
        }else if (!account.isActive()){
            return new ResponseEntity<>("this account is inactive",HttpStatus.FORBIDDEN);
        }

        if (client == null){
            return new ResponseEntity<>("not exist",HttpStatus.FORBIDDEN);
        }

        account.setActive(false);
        accountService.saveAccount(account);


        return new ResponseEntity<>("delete", HttpStatus.ACCEPTED);
    }

}
