package com.mycard.cards.property.map;

import com.mycard.cards.dto.CardFeeDTO;
import com.mycard.cards.entity.CardFee;
import org.modelmapper.PropertyMap;

public class CardFeeToCardFeeDTO extends PropertyMap<CardFee, CardFeeDTO> {
    @Override
    protected void configure() {
        map().setBin(source.getCompositeId().getBin());
        map().setFeature(source.getCompositeId().getFeature());
        map().setCompetence(source.getCompositeId().getCompetence());
        map().setValue(source.getValue());
    }
}
