package com.mindhub.homebanking.Repository;

import com.mindhub.homebanking.Models.Card;
import com.mindhub.homebanking.Models.CardColor;
import com.mindhub.homebanking.Models.CardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CardRepository extends JpaRepository<Card,Long> {
    Card findBynumber(String number);
    Card findByCvv(int cvv);
    Card findByType(CardType type);
    Card findByColor(CardColor color);

}
