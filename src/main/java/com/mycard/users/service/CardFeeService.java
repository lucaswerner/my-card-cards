package com.mycard.users.service;

import com.mycard.users.entity.CardFee;
import com.mycard.users.entity.id.CardFeeId;

import java.util.List;
import java.util.Optional;

public interface CardFeeService {
    Optional<CardFee> getCardFee(CardFeeId cardFeeId);

    List<CardFee> getCardFeeList();

    CardFee saveCardFee(CardFee cardFee);

    Optional<CardFee> updateCardFee(CardFee cardFee);
}
