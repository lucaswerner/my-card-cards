package com.mycard.users.property.map;

import com.mycard.users.dto.CardFeeDTO;
import com.mycard.users.entity.CardFee;
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
