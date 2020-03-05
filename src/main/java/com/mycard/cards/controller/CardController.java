package com.mycard.cards.controller;

import com.mycard.cards.dto.CardDTO;
import com.mycard.cards.dto.PostCardDTO;
import com.mycard.cards.entity.Card;
import com.mycard.cards.entity.id.CardId;
import com.mycard.cards.service.CardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("cards")
@Api(tags = "card")
public class CardController {

    @Autowired
    private CardService cardService;

    @Autowired
    private ModelMapper modelMapper;

    public CardController(CardService cardService, ModelMapper modelMapper) {
        this.cardService = cardService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @ApiOperation(value = "GetCardList")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong")
    })
    public ResponseEntity<List<CardDTO>> getCardList() {
        final List<Card> cardList = cardService.getCardList();
        return ResponseEntity.ok().body(this.modelMapper.map(cardList, new TypeToken<List<CardDTO>>() {
        }.getType()));
    }

    @GetMapping("/{bin}/{number}")
    @ApiOperation(value = "GetCard")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 204, message = "Could not find card")
    })
    public ResponseEntity<CardDTO> getCard(@PathVariable("bin") Long bin, @PathVariable("number") Long number) {
        final Optional<Card> optionalCard = this.cardService.getCard(new CardId(bin, number));

        return optionalCard
                .map(card -> ResponseEntity.ok().body(this.modelMapper.map(card, CardDTO.class)))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PostMapping
    @ApiOperation(value = "PostCard")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong")
    })
    public ResponseEntity<CardDTO> saveCard(@Valid @RequestBody PostCardDTO postCardDTO, HttpServletRequest request) {
        final Card savedCard = this.cardService.saveCard(this.modelMapper.map(postCardDTO, Card.class));
        final CardId compositeId = savedCard.getCompositeId();

        final URI location = URI.create(String.format(
                request.getRequestURI() + "/%s/%s",
                compositeId.getBin(),
                compositeId.getNumber()));

        return ResponseEntity.created(location).body(this.modelMapper.map(savedCard, CardDTO.class));
    }

    @PutMapping
    @ApiOperation(value = "PutCard")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 409, message = "Unable to find Card for update")
    })
    public ResponseEntity<CardDTO> putCard(@Valid @RequestBody CardDTO cardDTO) {
        final Optional<Card> optionalCard = this.cardService.updateCard(this.modelMapper.map(cardDTO, Card.class));

        return optionalCard
                .map(card -> ResponseEntity.ok().body(this.modelMapper.map(card, CardDTO.class)))
                .orElseGet(() -> ResponseEntity.status(409).build());
    }
}
