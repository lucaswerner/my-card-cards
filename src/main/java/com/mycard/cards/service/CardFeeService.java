package com.mycard.cards.service;

import com.mycard.cards.dto.CardFeeDTO;
import com.mycard.cards.dto.PostCardFeeDTO;
import com.mycard.cards.entity.CardFee;
import com.mycard.cards.entity.id.CardFeeId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CardFeeService {
    Optional<CardFee> getCardFee(CardFeeId cardFeeId);

    Page<CardFee> getCardFeePage(Pageable pageable);

    CardFee saveCardFee(CardFee cardFee);

    Optional<CardFee> updateCardFee(CardFee cardFee);

    Page<CardFeeDTO> getCardFeeDTOPage(Pageable pageable);

    Optional<CardFeeDTO> getCardFeeDTO(CardFeeId cardFeeId);

    CardFeeDTO saveCardFeeDTO(PostCardFeeDTO postCardFeeDTO);

    Optional<CardFeeDTO> updateCardFeeDTO(CardFeeDTO cardFeeDTO);
}
