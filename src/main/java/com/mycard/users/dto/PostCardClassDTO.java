package com.mycard.users.dto;

import com.mycard.users.enumeration.CardBrand;
import lombok.Data;

public @Data
class PostCardClassDTO {
    private String name;
    private CardBrand cardBrand;
}
