package com.mindhub.homebanking.Controller;

import com.mindhub.homebanking.Dtos.AccountDTO;
import com.mindhub.homebanking.Dtos.ClientDTO;
import com.mindhub.homebanking.Models.Account;
import com.mindhub.homebanking.Repository.AccountRepository;
import com.mindhub.homebanking.Repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;
@RestController

public class AccountController {
    @Autowired
    private AccountRepository repository;

    @RequestMapping("/api/accounts")
    public List<AccountDTO> getAccounts(){
        return repository.findAll().stream().map(account -> new AccountDTO(account)).collect(toList());
    }
    @RequestMapping("/api/accounts/{id}")
    public AccountDTO getAccount (@PathVariable Long id){
        return repository.findById(id).map(account -> new AccountDTO(account)).orElse(null);
    }

}
