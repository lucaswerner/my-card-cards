package com.mycard.users.entity.id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
public @Data
class CardId implements Serializable {

    private static final long serialVersionUID = 7438509752685591763L;

    @Column(nullable = false, name = "bin")
    private Long bin;

    @Column(nullable = false, name = "number")
    private Long number;
}
