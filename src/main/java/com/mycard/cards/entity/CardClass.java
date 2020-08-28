package com.mycard.cards.entity;

import com.mycard.cards.enumeration.CardBrand;
import com.mycard.cards.enumeration.CardCompetence;
import com.mycard.cards.enumeration.CardFeature;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "card_class")
public class CardClass implements Serializable {

    private static final long serialVersionUID = -9107050725091792944L;

    @Id
    @Column(name = "bin")
    private Long bin;

    @Size(min = 3, max = 20)
    @Column(name = "name", nullable = false)
    private String name;

    @Column(nullable = false, name = "brand")
    private CardBrand cardBrand;

    @Column(nullable = false, name = "feature")
    private CardFeature feature;

    @Column(name = "competence")
    private CardCompetence competence;
}
