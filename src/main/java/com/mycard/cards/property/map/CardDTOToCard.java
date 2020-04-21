package com.mycard.cards.property.map;

import com.mycard.cards.dto.CardDTO;
import com.mycard.cards.entity.Card;
import org.modelmapper.PropertyMap;

public class CardDTOToCard extends PropertyMap<CardDTO, Card> {
    @Override
    protected void configure() {
        map().getCardId().setBin(source.getBin());
        map().getCardId().setNumber(source.getNumber());
        map().setValidFrom(source.getValidFrom());
        map().setExpiration(source.getExpiration());
        map().setFeature(source.getFeature());
    }
}
