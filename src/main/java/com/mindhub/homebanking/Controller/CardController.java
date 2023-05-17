package com.mindhub.homebanking.Controller;

import com.mindhub.homebanking.Dtos.AccountDTO;
import com.mindhub.homebanking.Dtos.CardDTO;
import com.mindhub.homebanking.Dtos.ClientDTO;
import com.mindhub.homebanking.Models.*;
import com.mindhub.homebanking.Repository.CardRepository;
import com.mindhub.homebanking.Repository.ClientRepository;
import com.mindhub.homebanking.Service.AccountService;
import com.mindhub.homebanking.Service.CardService;
import com.mindhub.homebanking.Service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static com.mindhub.homebanking.Models.Card.generaRandomCardNumber;
import static com.mindhub.homebanking.Models.Card.randomCardCvv;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.springframework.http.HttpStatus.CREATED;

@CrossOrigin(origins = {"*"})
@RestController
public class CardController {
/*    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ClientRepository clientRepository;*/
    @Autowired
    private CardService cardService;
    @Autowired
    private ClientService clientService;





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
        int randomCvv = randomCardCvv();

        do {
            randomCard = generaRandomCardNumber();
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

}
