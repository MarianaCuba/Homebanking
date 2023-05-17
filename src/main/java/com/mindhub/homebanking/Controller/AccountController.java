package com.mindhub.homebanking.Controller;

import com.mindhub.homebanking.Dtos.AccountDTO;
import com.mindhub.homebanking.Dtos.ClientDTO;
import com.mindhub.homebanking.Models.Account;
import com.mindhub.homebanking.Models.Client;
import com.mindhub.homebanking.Repository.AccountRepository;
import com.mindhub.homebanking.Repository.ClientRepository;
import com.mindhub.homebanking.Service.AccountService;
import com.mindhub.homebanking.Service.ClientService;
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
 /*   @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;
    */
    @Autowired
    private AccountService accountService;
    @Autowired
    private ClientService clientService;

  /*  @RequestMapping("/api/accounts")
    public List<AccountDTO> getAccounts(){
        return accountRepository.findAll().stream().map(account -> new AccountDTO(account)).collect(toList());
    }*/

    @GetMapping("api/clients/current/accounts")
    public List<AccountDTO> getAccounts(Authentication authentication) {
        return accountService.getAccountsAuthentication(authentication);
    }

    @GetMapping("api/clients/current/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id) {
        return accountService.getAccountId(id);

    }


    @PostMapping("api/clients/current/accounts")
    public ResponseEntity<Object> newAccount(Authentication authentication) {

        String accountAleatory;
        do {
            accountAleatory = Account.generaRandom();
        } while (accountService.findByNumber(accountAleatory) != null);

        if (clientService.findByEmail(authentication.getName()).getAccounts().size() <= 2) {
            Account accountCreation = new Account(accountAleatory, LocalDateTime.now(), 0);
            clientService.findByEmail(authentication.getName()).addAccount(accountCreation);
            accountService.saveAccount(accountCreation);
        } else {
            return new ResponseEntity<>("You cannot have more than three accounts.", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
