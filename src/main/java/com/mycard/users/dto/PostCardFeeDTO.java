package com.mycard.users.dto;

import com.mycard.users.enumeration.CardCompetence;
import com.mycard.users.enumeration.CardFeature;
import lombok.Data;

public @Data
class PostCardFeeDTO {
    private Long bin;
    private CardFeature feature;
    private CardCompetence competence;
    private double value;
}
