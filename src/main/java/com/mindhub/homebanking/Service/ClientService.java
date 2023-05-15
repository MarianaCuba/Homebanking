package com.mindhub.homebanking.Service;

import com.mindhub.homebanking.Dtos.ClientDTO;
import com.mindhub.homebanking.Models.Client;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ClientService {
    void saveClient(Client client);
    List<ClientDTO> getClients();
    ClientDTO getClient(Authentication authentication);
    Client findByEmail(String email);

}
