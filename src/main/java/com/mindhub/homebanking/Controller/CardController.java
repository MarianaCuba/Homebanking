package com.mindhub.homebanking.Controller;

import com.mindhub.homebanking.Dtos.AccountDTO;
import com.mindhub.homebanking.Dtos.CardDTO;
import com.mindhub.homebanking.Dtos.ClientDTO;
import com.mindhub.homebanking.Models.*;
import com.mindhub.homebanking.Repository.CardRepository;
import com.mindhub.homebanking.Repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static com.mindhub.homebanking.Models.Card.generaRandomCardNumber;
import static com.mindhub.homebanking.Models.Card.randomCardCvv;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
public class CardController {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ClientRepository clientRepository;



    @RequestMapping("api/clients/current/cards")
    public List<CardDTO> getAccounts(Authentication authentication) {
        return new ClientDTO(clientRepository.findByEmail(authentication.getName())).getCards().stream().collect(toList());
    }

    @RequestMapping(path = "api/clients/current/cards", method = RequestMethod.POST)
    public ResponseEntity<Object> addCard (
            Authentication authentication,
            @RequestParam CardType type, @RequestParam CardColor color) {

        Client selectClient = clientRepository.findByEmail(authentication.getName());

        Set<Card> cards= selectClient.getCards().stream().filter(card -> card.getType() == type).collect(toSet());

              if (type == null) {
                  return new ResponseEntity<>("Missing type", HttpStatus.FORBIDDEN);
        }
              if (color == null) {
            return new ResponseEntity<>("Missing color", HttpStatus.FORBIDDEN);
        }
            if (cards.size() >= 3){
                return new ResponseEntity<>("You can't have more than 3 cards", HttpStatus.FORBIDDEN);
            }
            if (cards.stream().anyMatch(card -> card.getColor() == color)) {
                return new ResponseEntity<>("You can't have same card", HttpStatus.FORBIDDEN);
            }


        String randomCard;
        int randomCvv = randomCardCvv();

        do {
            randomCard = generaRandomCardNumber();
        } while (cardRepository.findBynumber(randomCard) != null);



        Card newCard = new Card(selectClient.getFirstName() +" "+selectClient.getLastName(),type, color,randomCard,randomCvv, LocalDate.now(),LocalDate.now().plusYears(5));
        selectClient.addCard(newCard);
        cardRepository.save(newCard);


        return new ResponseEntity<>(CREATED);
    }


}
