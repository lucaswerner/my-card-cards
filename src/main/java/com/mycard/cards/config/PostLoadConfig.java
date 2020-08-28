package com.mycard.cards.config;

import com.mycard.cards.entity.CardClass;
import com.mycard.cards.enumeration.CardBrand;
import com.mycard.cards.enumeration.CardCompetence;
import com.mycard.cards.enumeration.CardFeature;
import com.mycard.cards.repository.CardClassRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Component
public class PostLoadConfig {

    private final CardClassRepository cardClassRepository;

    public PostLoadConfig(CardClassRepository cardClassRepository) {
        this.cardClassRepository = cardClassRepository;
    }

    @PostConstruct
    public void setUp() {
        saveIfNotExistCardClass(CardClass.builder()
                .bin(511268L)
                .name("Gold")
                .cardBrand(CardBrand.MASTERCARD)
                .competence(CardCompetence.NATIONAL)
                .feature(CardFeature.CREDIT)
                .build());

        saveIfNotExistCardClass(CardClass.builder()
                .bin(515691L)
                .name("Platinum")
                .cardBrand(CardBrand.MASTERCARD)
                .competence(CardCompetence.INTERNATIONAL)
                .feature(CardFeature.MULTIPLE)
                .build());

        saveIfNotExistCardClass(CardClass.builder()
                .bin(378812L)
                .name("Normal")
                .cardBrand(CardBrand.VISA)
                .competence(CardCompetence.NATIONAL)
                .feature(CardFeature.MULTIPLE)
                .build());

        saveIfNotExistCardClass(CardClass.builder()
                .bin(370049L)
                .name("Super")
                .cardBrand(CardBrand.VISA)
                .competence(CardCompetence.INTERNATIONAL)
                .feature(CardFeature.MULTIPLE)
                .build());
    }

    private void saveIfNotExistCardClass(CardClass cardClass) {
        final Optional<CardClass> optionalCardClass = cardClassRepository.findById(cardClass.getBin());

        if (optionalCardClass.isEmpty()) {
            cardClassRepository.save(cardClass);
        }
    }
}
