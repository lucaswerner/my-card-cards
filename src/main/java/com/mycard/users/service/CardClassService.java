package com.mycard.users.service;

import com.mycard.users.entity.CardClass;

import java.util.List;
import java.util.Optional;

public interface CardClassService {
    Optional<CardClass> getCardClass(Long id);

    List<CardClass> getCardClassList();

    CardClass saveCardClass(CardClass cardClass);

    Optional<CardClass> updateCardClass(CardClass cardClass);
}
