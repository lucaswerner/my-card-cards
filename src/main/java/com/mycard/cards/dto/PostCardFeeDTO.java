package com.mycard.cards.dto;

import com.mycard.cards.enumeration.CardCompetence;
import com.mycard.cards.enumeration.CardFeature;
import lombok.Data;

public @Data
class PostCardFeeDTO {
    private Long bin;
    private CardFeature feature;
    private CardCompetence competence;
    private double value;
}
