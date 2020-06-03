package com.mycard.cards.property.map;

import com.mycard.cards.dto.CardDTO;
import com.mycard.cards.entity.Card;
import org.modelmapper.PropertyMap;

public class CardToCardDTO extends PropertyMap<Card, CardDTO> {
    @Override
    protected void configure() {
        map().setNumber(source.getNumber());
        map().setBin(source.getBin());
        map().setFeature(source.getFeature());
        map().setExpiration(source.getExpiration());
        map().setValidFrom(source.getValidFrom());
        map().setUserId(source.getUserId());
    }
}
