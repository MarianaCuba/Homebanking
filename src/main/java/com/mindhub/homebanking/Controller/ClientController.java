package com.mindhub.homebanking.Controller;

import com.mindhub.homebanking.Dtos.ClientDTO;
import com.mindhub.homebanking.Models.Account;
import com.mindhub.homebanking.Models.Client;
import com.mindhub.homebanking.Repository.AccountRepository;
import com.mindhub.homebanking.Repository.ClientRepository;
import com.mindhub.homebanking.Service.AccountService;
import com.mindhub.homebanking.Service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
public class ClientController {

  /*  @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;*/
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;

    @GetMapping ("/api/clients")
    public List<ClientDTO> getClients(){
        return clientService.getClients();
    }
/*  @RequestMapping("/api/clients/{id}")
    public ClientDTO getClient (@PathVariable Long id){
        return clientRepository.findById(id).map(client -> new ClientDTO(client)).orElse(null);
    }*/

    @GetMapping("api/clients/current")
    public ClientDTO getClient(Authentication authentication){
        return clientService.getClient(authentication);
    }


    @PostMapping("/api/clients")
        public ResponseEntity<Object> register(
                @RequestParam String firstName, @RequestParam String lastName,

                @RequestParam String email, @RequestParam String password) {

        if (firstName.isBlank()) {
            return new ResponseEntity<>("Missing firstname", HttpStatus.FORBIDDEN);
        }
        if (lastName.isBlank()) {
            return new ResponseEntity<>("Missing lastname", HttpStatus.FORBIDDEN);
        }
        if (email.isBlank()) {
            return new ResponseEntity<>("Missing email", HttpStatus.FORBIDDEN);
        }

        if (password.isBlank()) {
            return new ResponseEntity<>("Missing password", HttpStatus.FORBIDDEN);
        }

        if (clientService.findByEmail(email) != null) {

            return new ResponseEntity<>("Email already in use", HttpStatus.FORBIDDEN);

        }
        if (email.contains("@")) {
            // return new ResponseEntity<>("it is not an email", HttpStatus.FORBIDDEN);


            String accountAleatory;
            do {
                accountAleatory = Account.generaRandom();
            } while (accountService.findByNumber(accountAleatory) != null);


            Client clientRegister = new Client(firstName, lastName, email, passwordEncoder.encode(password));
            clientService.saveClient(clientRegister);
            Account accountRegister = new Account(accountAleatory, LocalDateTime.now(), 0);
            clientRegister.addAccount(accountRegister);
            accountService.saveAccount(accountRegister);


            return new ResponseEntity<>(HttpStatus.CREATED);

        }
        return new ResponseEntity<>("error",HttpStatus.FORBIDDEN);
    }
    }
