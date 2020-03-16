package com.mycard.cards.service.impl;

import com.mycard.cards.entity.Card;
import com.mycard.cards.entity.CardClass;
import com.mycard.cards.entity.id.CardId;
import com.mycard.cards.repository.CardRepository;
import com.mycard.cards.service.CardClassService;
import com.mycard.cards.service.CardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CardServiceImpl implements CardService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CardServiceImpl.class);

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

        LOGGER.info("Saving new card!");
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
        LOGGER.info("Updating card");
        return Optional.of(cardRepository.save(cardFromDB));
    }
}
