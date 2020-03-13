package com.mycard.cards.config;

import com.mycard.cards.property.map.CardFeeToCardFeeDTO;
import com.mycard.cards.property.map.CardToCardDTO;
import com.mycard.cards.property.map.PostCardDTOToCard;
import com.mycard.cards.property.map.PostCardFeeDTOToCardFee;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // card-fee
        modelMapper.addMappings(new PostCardFeeDTOToCardFee());
        modelMapper.addMappings(new CardFeeToCardFeeDTO());

        // card
        modelMapper.addMappings(new CardToCardDTO());
        modelMapper.addMappings(new PostCardDTOToCard());

        return modelMapper;
    }
}
