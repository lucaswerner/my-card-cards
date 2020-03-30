package com.mycard.cards.service.impl;

import com.mycard.cards.entity.CardClass;
import com.mycard.cards.entity.CardFee;
import com.mycard.cards.entity.id.CardFeeId;
import com.mycard.cards.repository.CardFeeRepository;
import com.mycard.cards.service.CardClassService;
import com.mycard.cards.service.CardFeeService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
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
    @HystrixCommand(threadPoolKey = "getCardFeeThreadPool")
    public Optional<CardFee> getCardFee(CardFeeId cardFeeId) {
        return cardFeeRepository.findById(cardFeeId);
    }

    @Override
    @HystrixCommand(threadPoolKey = "getCardFeeListThreadPool")
    public List<CardFee> getCardFeeList() {
        return cardFeeRepository.findAll();
    }

    @Override
    @HystrixCommand(threadPoolKey = "saveCardFeeThreadPool")
    public CardFee saveCardFee(CardFee cardFee) {
        final Long bin = cardFee.getCompositeId().getBin();
        final CardClass cardClass = cardClassService.getCardClass(bin)
                .orElseThrow(() -> new RuntimeException(String.format("Could not find Card Class with bin %s", bin)));
        cardFee.setCardFeeClass(cardClass);

        return cardFeeRepository.save(cardFee);
    }

    @Override
    @HystrixCommand(threadPoolKey = "updateCardFeeThreadPool")
    public Optional<CardFee> updateCardFee(CardFee cardFee) {
        final Optional<CardFee> optionalCardFeeFromDB = this.getCardFee(cardFee.getCompositeId());

        if (optionalCardFeeFromDB.isEmpty())
            return optionalCardFeeFromDB;

        final CardFee cardFeeFromDB = optionalCardFeeFromDB.get();

        cardFeeFromDB.setValue(cardFee.getValue());

        return Optional.of(cardFeeRepository.save(cardFeeFromDB));
    }
}
