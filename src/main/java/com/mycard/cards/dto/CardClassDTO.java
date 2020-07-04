package com.mycard.cards.dto;

import com.mycard.cards.enumeration.CardBrand;
import lombok.Data;

import java.io.Serializable;

public @Data
class CardClassDTO implements Serializable {
    private static final long serialVersionUID = 392905909542240106L;
    private Long bin;
    private String name;
    private CardBrand cardBrand;
}
