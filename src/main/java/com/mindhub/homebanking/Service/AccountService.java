package com.mindhub.homebanking.Service;

import com.mindhub.homebanking.Dtos.AccountDTO;
import com.mindhub.homebanking.Models.Account;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface AccountService {
    void saveAccount (Account account);

    List<AccountDTO> getAccountsAuthentication(Authentication authentication);

    AccountDTO getAccountId(long id);

    Account findByNumber( String number);
}
