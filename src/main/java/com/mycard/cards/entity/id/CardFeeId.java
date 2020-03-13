package com.mycard.cards.entity.id;

import com.mycard.cards.enumeration.CardCompetence;
import com.mycard.cards.enumeration.CardFeature;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
public @Data
class CardFeeId implements Serializable {
    private static final long serialVersionUID = 863940382789411599L;

    @Column(name = "bin")
    private Long bin;

    @Column(name = "feature")
    private CardFeature feature;

    @Column(name = "competence")
    private CardCompetence competence;
}
