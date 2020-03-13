package com.mycard.cards.dto;

import com.mycard.cards.enumeration.CardBrand;
import lombok.Data;

public @Data
class PostCardClassDTO {
    private String name;
    private CardBrand cardBrand;
}
