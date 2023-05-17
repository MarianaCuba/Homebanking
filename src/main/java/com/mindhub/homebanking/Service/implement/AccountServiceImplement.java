package com.mindhub.homebanking.Service.implement;

import com.mindhub.homebanking.Dtos.AccountDTO;
import com.mindhub.homebanking.Dtos.ClientDTO;
import com.mindhub.homebanking.Models.Account;
import com.mindhub.homebanking.Repository.AccountRepository;
import com.mindhub.homebanking.Repository.ClientRepository;
import com.mindhub.homebanking.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class AccountServiceImplement implements AccountService {
 @Autowired
 private AccountRepository accountRepository;
 @Autowired
 private ClientRepository clientRepository;


    @Override
   public void saveAccount(Account account){
        accountRepository.save(account);
    }

    @Override
    public List<AccountDTO> getAccountsAuthentication (Authentication authentication){
        return new ClientDTO(clientRepository.findByEmail(authentication.getName())).getAccounts().stream().collect(toList());
    }
    @Override
    public AccountDTO getAccountId(long id){
        return accountRepository.findById(id).map(account -> new AccountDTO(account)).orElse(null);
    }
    @Override
    public Account findByNumber(String number) {
        return accountRepository.findByNumber(number);
    }
}
