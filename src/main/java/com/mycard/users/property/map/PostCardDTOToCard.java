package com.mycard.users.property.map;

import com.mycard.users.dto.PostCardDTO;
import com.mycard.users.entity.Card;
import org.modelmapper.PropertyMap;

public class PostCardDTOToCard extends PropertyMap<PostCardDTO, Card> {
    @Override
    protected void configure() {
        map().getCompositeId().setBin(source.getBin());
        map().getCompositeId().setNumber(source.getNumber());
        map().setValidFrom(source.getValidFrom());
        map().setExpiration(source.getExpiration());
        map().setFeature(source.getFeature());
    }
}
