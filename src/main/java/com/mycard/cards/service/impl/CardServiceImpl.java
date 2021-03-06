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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@CacheConfig(cacheNames = "CardService")
public class CardServiceImpl implements CardService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CardServiceImpl.class);

    private final CardRepository cardRepository;
    private final CardClassService cardClassService;
    private final ModelMapper modelMapper;

    public CardServiceImpl(CardRepository cardRepository, CardClassService cardClassService, ModelMapper modelMapper) {
        this.cardRepository = cardRepository;
        this.cardClassService = cardClassService;
        this.modelMapper = modelMapper;
    }

    public Page<Card> getCardPage(Pageable pageable) {
        return cardRepository.findAll(pageable);
    }

    public Card saveCard(Card card) {
        final Long bin = card.getBin();
        final CardClass cardClass = cardClassService.getCardClass(bin)
                .orElseThrow(() -> new RuntimeException(String.format("Could not find Card Class with bin %s", bin)));
        card.setCardClass(cardClass);

        LOGGER.info("Saving new card!");
        return cardRepository.save(card);
    }

    public Optional<Card> updateCard(Card card) {
        final Optional<Card> optionalCardFromDB = this.getUserCard(
                new CardId(card.getBin(), card.getNumber()),
                card.getUserId()
        );

        if (optionalCardFromDB.isEmpty()) {
            return optionalCardFromDB;
        }

        final Card cardFromDB = optionalCardFromDB.get();
        cardFromDB.setBillDay(card.getBillDay());
        LOGGER.info("Updating card");
        return Optional.of(cardRepository.save(cardFromDB));
    }

    public List<Card> getUserCardList(Long userId) {
        return cardRepository.findAllByUserId(userId);
    }

    @Override
    public Optional<Card> getCardById(CardId id) {
        return cardRepository.findById(id);
    }

    public Optional<Card> getUserCard(CardId id, Long userId) {
        return cardRepository.findByBinAndNumberAndUserId(id.getBin(), id.getNumber(), userId);
    }

    private List<CardDTO> cardListToCardDTOList(List<Card> userCardList) {
        return this.modelMapper.map(
                userCardList,
                new TypeToken<List<CardDTO>>() {
                }.getType());
    }

    @Cacheable(key = "{#userId}")
    @HystrixCommand(threadPoolKey = "userCardDTOListThreadPool")
    public List<CardDTO> getUserCardDTOList(Long userId) {
        return cardListToCardDTOList(getUserCardList(userId));
    }

    @Cacheable(key = "{#cardId.bin, #cardId.number, #userId}")
    @HystrixCommand(threadPoolKey = "userCardDTOThreadPool")
    public Optional<CardDTO> getUserCardDTO(CardId cardId, Long userId) {
        return this.getUserCard(cardId, userId)
                .map(transformCardToCardDTOFunction());
    }

    @Cacheable(key = "{#id.bin, #id.number}")
    @HystrixCommand(threadPoolKey = "cardDTOByIdThreadPool")
    public Optional<CardDTO> getCardDTOById(CardId id) {
        return getCardById(id)
                .map(this::transformCardToCardDTO);
    }

    @CacheEvict(key = "{#cardDTO.userId}")
    @HystrixCommand(threadPoolKey = "saveCardDTOThreadPool")
    public CardDTO saveCardDTO(PostCardDTO cardDTO) {
        return transformCardToCardDTO(this.saveCard(this.modelMapper.map(cardDTO, Card.class)));
    }

    @Caching(
            evict = {
                    @CacheEvict(key = "{#cardDTO.userId}")
            },
            put = {
                    @CachePut(key = "{#cardDTO.bin, #cardDTO.number, #cardDTO.userId}"),
                    @CachePut(key = "{#cardDTO.bin, #cardDTO.number}")
            }
    )
    @HystrixCommand(threadPoolKey = "updateCardThreadPool")
    public Optional<CardDTO> updateCardDTO(CardDTO cardDTO) {
        return this.updateCard(this.modelMapper.map(cardDTO, Card.class))
                .map(transformCardToCardDTOFunction());
    }

    @HystrixCommand(threadPoolKey = "allCardDTOPageThreadPool")
    public Page<CardDTO> getCardDTOPage(Pageable pageable) {
        return getCardPage(pageable)
                .map(transformCardToCardDTOFunction());
    }

    @HystrixCommand(threadPoolKey = "cardDTOPageByBillDtThreadPool")
    public Page<CardDTO> getCardDTOPageByBillDt(Byte billDay, Pageable pageable) {
        return cardRepository.findAllByBillDay(billDay, pageable)
                .map(transformCardToCardDTOFunction());
    }

    private Function<Card, CardDTO> transformCardToCardDTOFunction() {
        return this::transformCardToCardDTO;
    }

    private CardDTO transformCardToCardDTO(Card card) {
        return modelMapper.map(card, CardDTO.class);
    }

}
