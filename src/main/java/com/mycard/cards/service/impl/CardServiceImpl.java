package com.mycard.cards.service.impl;

import com.mycard.cards.dto.CardDTO;
import com.mycard.cards.dto.PostCardDTO;
import com.mycard.cards.entity.Card;
import com.mycard.cards.entity.CardClass;
import com.mycard.cards.entity.id.CardId;
import com.mycard.cards.repository.CardRepository;
import com.mycard.cards.service.CardClassService;
import com.mycard.cards.service.CardService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = "CardService")
public class CardServiceImpl implements CardService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CardServiceImpl.class);

    private CardRepository cardRepository;
    private CardClassService cardClassService;
    private ModelMapper modelMapper;

    public CardServiceImpl(CardRepository cardRepository, CardClassService cardClassService, ModelMapper modelMapper) {
        this.cardRepository = cardRepository;
        this.cardClassService = cardClassService;
        this.modelMapper = modelMapper;
    }

    //    @Cacheable(cacheNames = CACHE_NAME, key = "#id")
    @HystrixCommand(threadPoolKey = "getCardThreadPool")
    public Optional<Card> getCard(CardId id) {
        return cardRepository.findById(id);
    }

    //    @Cacheable(cacheNames = CACHE_NAME, key = "#root.method.name")
    @HystrixCommand(threadPoolKey = "getCardListThreadPool")
    public List<Card> getCardList() {
        return cardRepository.findAll();
    }

    public Card saveCard(Card card) {
        final Long bin = card.getCardId().getBin();
        final CardClass cardClass = cardClassService.getCardClass(bin)
                .orElseThrow(() -> new RuntimeException(String.format("Could not find Card Class with bin %s", bin)));
        card.setCardClass(cardClass);

        LOGGER.info("Saving new card!");
        return cardRepository.save(card);
    }

    public Optional<Card> updateCard(Card card) {
        final Optional<Card> optionalCardFromDB = this.getUserCard(card.getCardId(), card.getUserId());

        if (optionalCardFromDB.isEmpty()) {
            return optionalCardFromDB;
        }

        final Card cardFromDB = optionalCardFromDB.get();
        cardFromDB.setFeature(card.getFeature());
        LOGGER.info("Updating card");
        return Optional.of(cardRepository.save(cardFromDB));
    }

    public List<Card> getUserCardList(Long userId) {
        return cardRepository.findAllByUserId(userId);
    }

    public Optional<Card> getUserCard(CardId id, Long userId) {
        return cardRepository.findByCardIdAndUserId(id, userId);
    }

    @Cacheable(key = "{#cardDTO.userId}")
    @HystrixCommand(threadPoolKey = "userCardDTOListThreadPool")
    public List<CardDTO> getUserCardDTOList(CardDTO cardDTO) {
        return this.modelMapper.map(
                this.getUserCardList(cardDTO.getUserId()),
                new TypeToken<List<CardDTO>>() {
                }.getType());
    }

    @Cacheable(key = "{#cardDTO.bin, #cardDTO.number, #cardDTO.userId}")
    @HystrixCommand(threadPoolKey = "userCardDTOThreadPool")
    public Optional<CardDTO> getUserCardDTO(CardDTO cardDTO) {
        return this.getUserCard(
                new CardId(cardDTO.getBin(), cardDTO.getNumber()),
                cardDTO.getUserId()
        )
                .map(card -> this.modelMapper.map(card, CardDTO.class));
    }

    @CacheEvict(key = "{#cardDTO.userId}")
    @HystrixCommand(threadPoolKey = "saveCardDTOThreadPool")
    public CardDTO saveCardDTO(PostCardDTO cardDTO) {
        return this.modelMapper.map(
                this.saveCard(this.modelMapper.map(cardDTO, Card.class)),
                CardDTO.class
        );
    }

    @Caching(
            evict = {
                    @CacheEvict(key = "{#cardDTO.userId}", beforeInvocation = true)
            },
            put = {
                    @CachePut(key = "{#cardDTO.bin, #cardDTO.number, #cardDTO.userId}")
            }
    )
    @HystrixCommand(threadPoolKey = "updateCardThreadPool")
    public Optional<CardDTO> updateCardDTO(CardDTO cardDTO) {
        return this.updateCard(this.modelMapper.map(cardDTO, Card.class))
                .map(card -> this.modelMapper.map(card, CardDTO.class));
    }

}
