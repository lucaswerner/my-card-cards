package com.mycard.cards.property.map;

import com.mycard.cards.dto.PostCardFeeDTO;
import com.mycard.cards.entity.CardFee;
import org.modelmapper.PropertyMap;

public class PostCardFeeDTOToCardFee extends PropertyMap<PostCardFeeDTO, CardFee> {
    @Override
    protected void configure() {
        map().getCompositeId().setBin(source.getBin());
        map().getCompositeId().setCompetence(source.getCompetence());
        map().getCompositeId().setFeature(source.getFeature());
        map().setValue(source.getValue());
    }
}
