package com.mycard.users.service.impl;

import com.mycard.users.entity.Card;
import com.mycard.users.entity.CardClass;
import com.mycard.users.entity.id.CardId;
import com.mycard.users.repository.CardRepository;
import com.mycard.users.service.CardClassService;
import com.mycard.users.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private CardClassService cardClassService;

    public CardServiceImpl(CardRepository cardRepository, CardClassService cardClassService) {
        this.cardRepository = cardRepository;
        this.cardClassService = cardClassService;
    }

    @Override
    public Optional<Card> getCard(CardId id) {
        return cardRepository.findById(id);
    }

    @Override
    public List<Card> getCardList() {
        return cardRepository.findAll();
    }

    @Override
    public Card saveCard(Card card) {
        final Long bin = card.getCompositeId().getBin();
        final CardClass cardClass = cardClassService.getCardClass(bin)
                .orElseThrow(() -> new RuntimeException(String.format("Could not find Card Class with bin %s", bin)));
        card.setCardClass(cardClass);

        return cardRepository.save(card);
    }

    @Override
    public Optional<Card> updateCard(Card card) {
        final Optional<Card> optionalCardFromDB = this.getCard(card.getCompositeId());

        if (optionalCardFromDB.isEmpty()) {
            return optionalCardFromDB;
        }

        final Card cardFromDB = optionalCardFromDB.get();
        cardFromDB.setFeature(card.getFeature());
        return Optional.of(cardRepository.save(cardFromDB));
    }
}
