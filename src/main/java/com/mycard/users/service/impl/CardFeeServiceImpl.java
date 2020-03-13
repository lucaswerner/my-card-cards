package com.mycard.users.service.impl;

import com.mycard.users.entity.CardClass;
import com.mycard.users.entity.CardFee;
import com.mycard.users.entity.id.CardFeeId;
import com.mycard.users.repository.CardFeeRepository;
import com.mycard.users.service.CardClassService;
import com.mycard.users.service.CardFeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CardFeeServiceImpl implements CardFeeService {

    @Autowired
    private CardFeeRepository cardFeeRepository;

    @Autowired
    private CardClassService cardClassService;

    public CardFeeServiceImpl(CardFeeRepository cardFeeRepository, CardClassService cardClassService) {
        this.cardFeeRepository = cardFeeRepository;
        this.cardClassService = cardClassService;
    }

    @Override
    public Optional<CardFee> getCardFee(CardFeeId cardFeeId) {
        return cardFeeRepository.findById(cardFeeId);
    }

    @Override
    public List<CardFee> getCardFeeList() {
        return cardFeeRepository.findAll();
    }

    @Override
    public CardFee saveCardFee(CardFee cardFee) {
        final Long bin = cardFee.getCompositeId().getBin();
        final CardClass cardClass = cardClassService.getCardClass(bin)
                .orElseThrow(() -> new RuntimeException(String.format("Could not find Card Class with bin %s", bin)));
        cardFee.setCardFeeClass(cardClass);

        return cardFeeRepository.save(cardFee);
    }

    @Override
    public Optional<CardFee> updateCardFee(CardFee cardFee) {
        final Optional<CardFee> optionalCardFeeFromDB = this.getCardFee(cardFee.getCompositeId());

        if (optionalCardFeeFromDB.isEmpty())
            return optionalCardFeeFromDB;

        final CardFee cardFeeFromDB = optionalCardFeeFromDB.get();

        cardFeeFromDB.setValue(cardFee.getValue());

        return Optional.of(cardFeeRepository.save(cardFeeFromDB));
    }
}