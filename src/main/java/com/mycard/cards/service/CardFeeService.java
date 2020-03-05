package com.mycard.cards.service;

import com.mycard.cards.entity.CardFee;
import com.mycard.cards.entity.id.CardFeeId;

import java.util.List;
import java.util.Optional;

public interface CardFeeService {
    Optional<CardFee> getCardFee(CardFeeId cardFeeId);

    List<CardFee> getCardFeeList();

    CardFee saveCardFee(CardFee cardFee);

    Optional<CardFee> updateCardFee(CardFee cardFee);
}
