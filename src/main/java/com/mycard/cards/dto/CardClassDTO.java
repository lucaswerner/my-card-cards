package com.mycard.cards.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mycard.cards.enumeration.CardBrand;
import lombok.Data;

public @Data
class CardClassDTO {
    private Long bin;
    private String name;
    private CardBrand cardBrand;
}
