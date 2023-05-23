package com.mindhub.homebanking.Controller;


import com.mindhub.homebanking.Dtos.CardDTO;
import com.mindhub.homebanking.Dtos.PaymentDTO;
import com.mindhub.homebanking.Models.*;
import com.mindhub.homebanking.Service.CardService;
import com.mindhub.homebanking.Service.ClientService;
import com.mindhub.homebanking.Service.TransactionService;
import com.mindhub.homebanking.Util.CardUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import java.util.Set;


import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.springframework.http.HttpStatus.*;

@CrossOrigin(origins = {"*"})
@RestController
public class CardController {

    @Autowired
    private CardService cardService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private TransactionService transactionService;





    @GetMapping("api/clients/current/cards")
    public List<CardDTO> getAccounts(Authentication authentication) {
        return cardService.getCards(authentication);
    }
/*    @Override
    public List<AccountDTO> getAccountsAuthentication (Authentication authentication){
        return new ClientDTO(clientRepository.findByEmail(authentication.getName())).getAccounts().stream().collect(toList());
    }*/

    @PostMapping("api/clients/current/cards")
    public ResponseEntity<Object> addCard (
            Authentication authentication,
            @RequestParam CardType type, @RequestParam CardColor color) {

        Client selectClient = clientService.findByEmail(authentication.getName());

        Set<Card> cards= selectClient.getCards().stream().filter(card -> card.getType() == type && card.getActive()).collect(toSet());

              if (type == null) {
                  return new ResponseEntity<>("Missing type", HttpStatus.FORBIDDEN);
        }
              if (color == null) {
            return new ResponseEntity<>("Missing color", HttpStatus.FORBIDDEN);
        }
            if (cards.size() >= 3){
                return new ResponseEntity<>("You can't have more than 3 cards", HttpStatus.FORBIDDEN);
            }
            if (cards.stream().anyMatch(card -> card.getColor() == color && card.getActive())) {
                return new ResponseEntity<>("You can't have same card", HttpStatus.FORBIDDEN);
            }


        String randomCard;
        int randomCvv = CardUtil.randomCardCvv();

        do {
            randomCard = CardUtil.generaRandomCardNumber();
        } while (cardService.findByNumber(randomCard) != null);



        Card newCard = new Card(selectClient.getFirstName() +" "+selectClient.getLastName(),type, color,randomCard,randomCvv, LocalDate.now(),LocalDate.now().plusYears(5),true);
        selectClient.addCard(newCard);
        cardService.saveCard(newCard);


        return new ResponseEntity<>(CREATED);
    }

    @PutMapping("api/clients/current/cards/{id}")
    public ResponseEntity<Object> deleteCard (Authentication authentication, @PathVariable long id){
        Client client = clientService.findByEmail(authentication.getName());
        Card card = cardService.findById(id);

        if (!client.getCards().contains(card)) {
            return new ResponseEntity<>("this card is not yours",HttpStatus.FORBIDDEN);
        }
        if (card == null){
            return new ResponseEntity<>("this card not found",HttpStatus.FORBIDDEN);
        }
        if (!card.getActive()){
            return new ResponseEntity<>("this card is inactive",HttpStatus.FORBIDDEN);
        }

        card.setActive(false);
        cardService.saveCard(card);

        return new ResponseEntity<>("delete", HttpStatus.ACCEPTED);
    }

    @Transactional
    @PostMapping ("api/clients/current/cardpay")
    public ResponseEntity<Object> newPayCard (@RequestBody PaymentDTO paymentDTO){
       // verifico al cliente
        Card cardClient = cardService.findByNumber(paymentDTO.getNumber()); // verifico el numero de tarjeta
        Client client = cardClient.getClient();
        List<Account> accounts = client.getAccounts().stream().collect(toList());
        List<Account> balanceAccount= accounts.stream().filter(account -> account.getBalance() > paymentDTO.getAmount()).collect(toList());

        Account account = balanceAccount.stream().findFirst().orElse(null);
       // Optional<Account> account = client.getAccounts().stream().filter(account1 -> account1.getBalance()>= paymentDTO.getAmount()).findFirst(); // filtro las cuentas para encontrar una que tenga un monto mayor al valor que quiero pagar.
        //Account account = client.getAccounts().stream().filter( account1 -> account1.getBalance() >= paymentDTO.getAmount() ).collect(toList()).get(0);

        if (client == null){
            return new ResponseEntity<>("not is client",FORBIDDEN);
        }
        if (account == null){
            return new ResponseEntity<>("not is client",FORBIDDEN);
        }
        if (client.getCards().stream().filter(card1 -> card1.getNumber().equalsIgnoreCase(paymentDTO.getNumber())).collect(toList()).size() == 0) {
            return new ResponseEntity<>("This card is not yours",FORBIDDEN);
        }
        if (cardClient.getCvv() != paymentDTO.getCvv()){
            return new ResponseEntity<>("This card is not yours",FORBIDDEN);
        }
        if (cardClient.getType() != paymentDTO.getTypeCard()){
            return new ResponseEntity<>("o.O",FORBIDDEN);
        }


        account.setBalance(account.getBalance()- paymentDTO.getAmount()); // resto a la cuenta el monto ingresado a pagar.

        //creo la transacction
        Transaction transaction = new Transaction(paymentDTO.getAmount(),TransactionType.DEBIT, "descrpcion",LocalDateTime.now(),account.getBalance(),true);
        account.addTransaction(transaction);
        transactionService.saveTransaction(transaction);


    return new ResponseEntity<>("pay exitoso",ACCEPTED);
    }

}
