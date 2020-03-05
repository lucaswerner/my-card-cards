package com.mycard.cards.service;

import com.mycard.cards.entity.CardClass;

import java.util.List;
import java.util.Optional;

public interface CardClassService {
    Optional<CardClass> getCardClass(Long id);

    List<CardClass> getCardClassList();

    CardClass saveCardClass(CardClass cardClass);

    Optional<CardClass> updateCardClass(CardClass cardClass);
}
