package com.mycard.cards.property.map;

import com.mycard.cards.dto.CardDTO;
import com.mycard.cards.entity.Card;
import org.modelmapper.PropertyMap;

public class CardToCardDTO extends PropertyMap<Card, CardDTO> {
    @Override
    protected void configure() {
        map().setBin(source.getBin());
        map().setNumber(source.getNumber());
        map().setValidFrom(source.getValidFrom());
        map().setExpiration(source.getExpiration());
        map().setBillDay(source.getBillDay());
        map().setUserId(source.getUserId());
    }
}
