package com.mycard.cards.service.impl;

import com.mycard.cards.dto.CardClassDTO;
import com.mycard.cards.dto.PostCardClassDTO;
import com.mycard.cards.entity.CardClass;
import com.mycard.cards.repository.CardClassRepository;
import com.mycard.cards.service.CardClassService;
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
@CacheConfig(cacheNames = "CardClassService")
public class CardClassServiceImpl implements CardClassService {

    private final CardClassRepository cardClassRepository;
    private final ModelMapper modelMapper;

    public CardClassServiceImpl(CardClassRepository cardClassRepository, ModelMapper modelMapper) {
        this.cardClassRepository = cardClassRepository;
        this.modelMapper = modelMapper;
    }

    public Optional<CardClass> getCardClass(Long bin) {
        return cardClassRepository.findById(bin);
    }

    public Page<CardClass> getCardClassPage(Pageable pageable) {
        return cardClassRepository.findAll(pageable);
    }

    public CardClass saveCardClass(CardClass cardClass) {
        return cardClassRepository.save(cardClass);
    }

    public Optional<CardClass> updateCardClass(CardClass cardClass) {
        final Optional<CardClass> optionalCardClassFromDB = this.getCardClass(cardClass.getBin());

        if (optionalCardClassFromDB.isEmpty())
            return optionalCardClassFromDB;

        final CardClass cardClassFromDB = optionalCardClassFromDB.get();
        cardClassFromDB.setName(cardClass.getName());

        return Optional.of(cardClassRepository.save(cardClassFromDB));
    }

    @HystrixCommand(threadPoolKey = "getCardClassDTOPageThreadPool")
    public Page<CardClassDTO> getCardClassDTOPage(Pageable pageable) {
        return getCardClassPage(pageable)
                .map(transformCardClassCardClassDTOFunction());
    }

    @Cacheable(key = "{#bin}")
    @HystrixCommand(threadPoolKey = "getCardClassDTOThreadPool")
    public Optional<CardClassDTO> getCardClassDTO(Long bin) {
        return getCardClass(bin)
                .map(transformCardClassCardClassDTOFunction());
    }

    @HystrixCommand(threadPoolKey = "saveCardClassDTOThreadPool")
    public CardClassDTO saveCardClassDTO(PostCardClassDTO postCardClassDTO) {
        return transformCardClassToCardClassDTO(saveCardClass(modelMapper.map(postCardClassDTO, CardClass.class)));
    }

    @CachePut(key = "{#cardClassDTO.bin}")
    @HystrixCommand(threadPoolKey = "updateCardClassDTOThreadPool")
    public Optional<CardClassDTO> updateCardClassDTO(CardClassDTO cardClassDTO) {
        return updateCardClass(modelMapper.map(cardClassDTO, CardClass.class))
                .map(transformCardClassCardClassDTOFunction());
    }

    private Function<CardClass, CardClassDTO> transformCardClassCardClassDTOFunction() {
        return this::transformCardClassToCardClassDTO;
    }

    private CardClassDTO transformCardClassToCardClassDTO(CardClass cardClass) {
        return modelMapper.map(cardClass, CardClassDTO.class);
    }
}
