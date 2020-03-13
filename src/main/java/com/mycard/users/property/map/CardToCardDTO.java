package com.mycard.users.property.map;

import com.mycard.users.dto.CardDTO;
import com.mycard.users.entity.Card;
import org.modelmapper.PropertyMap;

public class CardToCardDTO extends PropertyMap<Card, CardDTO> {
    @Override
    protected void configure() {
        map().setNumber(source.getCompositeId().getNumber());
        map().setBin(source.getCompositeId().getBin());
        map().setFeature(source.getFeature());
        map().setExpiration(source.getExpiration());
        map().setValidFrom(source.getValidFrom());
    }
}
