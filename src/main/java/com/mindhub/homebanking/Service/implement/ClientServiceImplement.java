package com.mindhub.homebanking.Service.implement;

import com.mindhub.homebanking.Dtos.ClientDTO;
import com.mindhub.homebanking.Models.Client;
import com.mindhub.homebanking.Repository.ClientRepository;
import com.mindhub.homebanking.Service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ClientServiceImplement implements ClientService {
    @Autowired
    private ClientRepository clientRepository;
    @Override
    public void saveClient(Client client) {
        clientRepository.save(client);
    }
    @Override
    public List<ClientDTO> getClients() {
        return clientRepository.findAll().stream().map(client -> new ClientDTO(client)).collect(toList());
    }
    @Override
    public ClientDTO getClient(Authentication authentication) {
        return new ClientDTO(clientRepository.findByEmail(authentication.getName()));
    }
    @Override
    public Client findByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    public Client getClientAuthentication(Authentication authentication){
        return clientRepository.findByEmail(authentication.getName());
    }
}
