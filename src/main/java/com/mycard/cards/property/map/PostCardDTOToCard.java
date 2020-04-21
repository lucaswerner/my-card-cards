package com.mycard.cards.property.map;

import com.mycard.cards.dto.PostCardDTO;
import com.mycard.cards.entity.Card;
import org.modelmapper.PropertyMap;

public class PostCardDTOToCard extends PropertyMap<PostCardDTO, Card> {
    @Override
    protected void configure() {
        map().getCardId().setBin(source.getBin());
        map().getCardId().setNumber(source.getNumber());
        map().setValidFrom(source.getValidFrom());
        map().setExpiration(source.getExpiration());
        map().setFeature(source.getFeature());
        map().setUserId(source.getUserId());
    }
}
