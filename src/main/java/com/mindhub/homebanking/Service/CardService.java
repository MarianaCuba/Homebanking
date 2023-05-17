package com.mindhub.homebanking.Service;

import com.mindhub.homebanking.Dtos.CardDTO;
import com.mindhub.homebanking.Models.Card;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface CardService {
    void saveCard (Card card);

    List<CardDTO> getCards(Authentication authentication);
    Card findByNumber(String number);
    Card findByCvv(int cvv);
    Card findById(long id);
}
