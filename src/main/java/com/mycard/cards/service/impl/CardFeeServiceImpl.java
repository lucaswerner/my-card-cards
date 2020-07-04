package com.mycard.cards.service.impl;

import com.mycard.cards.dto.CardFeeDTO;
import com.mycard.cards.dto.PostCardFeeDTO;
import com.mycard.cards.entity.CardClass;
import com.mycard.cards.entity.CardFee;
import com.mycard.cards.entity.id.CardFeeId;
import com.mycard.cards.repository.CardFeeRepository;
import com.mycard.cards.service.CardClassService;
import com.mycard.cards.service.CardFeeService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
@CacheConfig(cacheNames = "CardFeeService")
public class CardFeeServiceImpl implements CardFeeService {

    private final CardFeeRepository cardFeeRepository;
    private final CardClassService cardClassService;
    private final ModelMapper modelMapper;

    public CardFeeServiceImpl(CardFeeRepository cardFeeRepository, CardClassService cardClassService, ModelMapper modelMapper) {
        this.cardFeeRepository = cardFeeRepository;
        this.cardClassService = cardClassService;
        this.modelMapper = modelMapper;
    }

    public Optional<CardFee> getCardFee(CardFeeId cardFeeId) {
        return cardFeeRepository.findById(cardFeeId);
    }

    public Page<CardFee> getCardFeePage(Pageable pageable) {
        return cardFeeRepository.findAll(pageable);
    }

    public CardFee saveCardFee(CardFee cardFee) {
        final Long bin = cardFee.getCompositeId().getBin();
        final CardClass cardClass = cardClassService.getCardClass(bin)
                .orElseThrow(() -> new RuntimeException(String.format("Could not find Card Class with bin %s", bin)));
        cardFee.setCardFeeClass(cardClass);

        return cardFeeRepository.save(cardFee);
    }

    public Optional<CardFee> updateCardFee(CardFee cardFee) {
        final Optional<CardFee> optionalCardFeeFromDB = this.getCardFee(cardFee.getCompositeId());

        if (optionalCardFeeFromDB.isEmpty())
            return optionalCardFeeFromDB;

        final CardFee cardFeeFromDB = optionalCardFeeFromDB.get();

        cardFeeFromDB.setValue(cardFee.getValue());

        return Optional.of(cardFeeRepository.save(cardFeeFromDB));
    }

    @HystrixCommand(threadPoolKey = "getCardFeeDTOPageThreadPool")
    public Page<CardFeeDTO> getCardFeeDTOPage(Pageable pageable) {
        return getCardFeePage(pageable)
                .map(transformCardFeeToCardFeeDTOFunction());
    }

    @Cacheable(key = "{#cardFeeId.bin, #cardFeeId.feature, #cardFeeId.competence}")
    @HystrixCommand(threadPoolKey = "getCardFeeDTOThreadPool")
    public Optional<CardFeeDTO> getCardFeeDTO(CardFeeId cardFeeId) {
        return getCardFee(cardFeeId)
                .map(transformCardFeeToCardFeeDTOFunction());
    }

    @HystrixCommand(threadPoolKey = "saveCardFeeDTOThreadPool")
    public CardFeeDTO saveCardFeeDTO(PostCardFeeDTO postCardFeeDTO) {
        return transformCardFeeToCardFeeDTO(saveCardFee(modelMapper.map(postCardFeeDTO, CardFee.class)));
    }

    @CachePut(key = "{#cardFeeDTO.bin, #cardFeeDTO.feature, #cardFeeDTO.competence}")
    @HystrixCommand(threadPoolKey = "updateCardFeeDTOThreadPool")
    public Optional<CardFeeDTO> updateCardFeeDTO(CardFeeDTO cardFeeDTO) {
        return updateCardFee(modelMapper.map(cardFeeDTO, CardFee.class))
                .map(transformCardFeeToCardFeeDTOFunction());
    }

    private Function<CardFee, CardFeeDTO> transformCardFeeToCardFeeDTOFunction() {
        return this::transformCardFeeToCardFeeDTO;
    }

    private CardFeeDTO transformCardFeeToCardFeeDTO(CardFee cardFee) {
        return modelMapper.map(cardFee, CardFeeDTO.class);
    }
}
