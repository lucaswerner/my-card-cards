package com.mycard.users.service;

import com.mycard.users.entity.Card;
import com.mycard.users.entity.id.CardId;

import java.util.List;
import java.util.Optional;

public interface CardService {
    Optional<Card> getCard(CardId id);

    List<Card> getCardList();

    Card saveCard(Card card);

    Optional<Card> updateCard(Card card);
}
