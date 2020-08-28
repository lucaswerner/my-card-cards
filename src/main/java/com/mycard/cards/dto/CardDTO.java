package com.mycard.cards.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public @Data
class CardDTO implements Serializable {
    private static final long serialVersionUID = -8765111780619787072L;

    private Long bin;
    private Long number;
    private LocalDate expiration;
    private LocalDateTime validFrom;
    private Byte billDay;
    private Long userId;
}
