package com.mycard.cards.service;

import com.mycard.cards.entity.Card;
import com.mycard.cards.entity.id.CardId;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CardService {
    Optional<Card> getCard(CardId id);

    List<Card> getCardList();

    Card saveCard(Card card);

    Optional<Card> updateCard(Card card);

    List<Card> getUserCardList(Long userId, Pageable pageable);

    Optional<Card> getUserCard(CardId id, Long userId);
}
