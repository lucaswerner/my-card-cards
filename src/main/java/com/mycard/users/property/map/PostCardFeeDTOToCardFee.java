package com.mycard.users.property.map;

import com.mycard.users.dto.PostCardFeeDTO;
import com.mycard.users.entity.CardFee;
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
