package com.mycard.cards.config;

import com.mycard.cards.property.map.*;
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
        modelMapper.addMappings(new CardDTOToCard());
        modelMapper.addMappings(new PostCardDTOToCard());

        return modelMapper;
    }
}
