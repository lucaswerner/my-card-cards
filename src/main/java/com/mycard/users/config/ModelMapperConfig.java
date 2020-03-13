package com.mycard.users.config;

import com.mycard.users.property.map.CardFeeToCardFeeDTO;
import com.mycard.users.property.map.CardToCardDTO;
import com.mycard.users.property.map.PostCardDTOToCard;
import com.mycard.users.property.map.PostCardFeeDTOToCardFee;
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
