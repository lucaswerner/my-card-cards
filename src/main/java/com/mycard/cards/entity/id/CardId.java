package com.mycard.cards.entity.id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
public @Data
class CardId implements Serializable {

    private static final long serialVersionUID = 7438509752685591763L;

    private Long bin;

    private Long number;
}
