package com.mycard.cards.dto;

import com.mycard.cards.enumeration.CardCompetence;
import com.mycard.cards.enumeration.CardFeature;
import lombok.Data;

import java.io.Serializable;

public @Data
class CardFeeDTO implements Serializable {
    private static final long serialVersionUID = -812726217817621855L;
    private Long bin;
    private CardFeature feature;
    private CardCompetence competence;
    private double value;
}
