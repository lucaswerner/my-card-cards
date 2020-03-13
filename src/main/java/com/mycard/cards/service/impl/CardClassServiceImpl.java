package com.mycard.cards.service.impl;

import com.mycard.cards.entity.CardClass;
import com.mycard.cards.repository.CardClassRepository;
import com.mycard.cards.service.CardClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CardClassServiceImpl implements CardClassService {

    @Autowired
    private CardClassRepository cardClassRepository;

    public CardClassServiceImpl(CardClassRepository cardClassRepository) {
        this.cardClassRepository = cardClassRepository;
    }

    @Override
    public Optional<CardClass> getCardClass(Long id) {
        return cardClassRepository.findById(id);
    }

    @Override
    public List<CardClass> getCardClassList() {
        return cardClassRepository.findAll();
    }

    @Override
    public CardClass saveCardClass(CardClass cardClass) {
        return cardClassRepository.save(cardClass);
    }

    @Override
    public Optional<CardClass> updateCardClass(CardClass cardClass) {
        final Optional<CardClass> optionalCardClassFromDB = this.getCardClass(cardClass.getBin());

        if (optionalCardClassFromDB.isEmpty())
            return optionalCardClassFromDB;

        final CardClass cardClassFromDB = optionalCardClassFromDB.get();
        cardClassFromDB.setCardBrand(cardClass.getCardBrand());
        cardClassFromDB.setName(cardClass.getName());

        return Optional.of(cardClassRepository.save(cardClassFromDB));
    }
}
