package com.mindhub.homebanking.Service.implement;

import com.mindhub.homebanking.Dtos.CardDTO;
import com.mindhub.homebanking.Dtos.ClientDTO;
import com.mindhub.homebanking.Models.Card;
import com.mindhub.homebanking.Repository.CardRepository;
import com.mindhub.homebanking.Repository.ClientRepository;
import com.mindhub.homebanking.Service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;
@Service
public class CardServiceImplement implements CardService {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CardRepository cardRepository;

    @Override
    public void saveCard(Card card) {
        cardRepository.save(card);
    }

    @Override
    public List<CardDTO> getCards(Authentication authentication) {
        return new ClientDTO(clientRepository.findByEmail(authentication.getName())).getCards().stream().collect(toList());
    }

    @Override
    public Card findByNumber(String number) {
        return cardRepository.findBynumber(number);
    }

    @Override
    public Card findByCvv(int cvv) {
        return cardRepository.findByCvv(cvv);
    }


}
