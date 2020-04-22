package com.mycard.cards.controller;

import com.mycard.cards.dto.CardDTO;
import com.mycard.cards.dto.PostCardDTO;
import com.mycard.cards.dto.PrincipalDTO;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    @ApiOperation(value = "GetUserCardList")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong")
    })
    public ResponseEntity<List<CardDTO>> getUserCardList(
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam("pageNumber") Integer pageNumber,
            Authentication authentication
    ) {
        final List<Card> userCardList = cardService.getUserCardList(
                ((PrincipalDTO) authentication.getPrincipal()).getId(),
                PageRequest.of(pageNumber, pageSize)
        );
        return ResponseEntity.ok().body(this.modelMapper.map(userCardList, new TypeToken<List<CardDTO>>() {
        }.getType()));
    }

    @GetMapping("/{bin}/{number}")
    @ApiOperation(value = "GetUserCard")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 204, message = "Could not find card")
    })
    public ResponseEntity<CardDTO> getUserCard(
            @PathVariable("bin") Long bin,
            @PathVariable("number") Long number,
            Authentication authentication
    ) {
        return getCard(bin, number, ((PrincipalDTO) authentication.getPrincipal()).getId());
    }

    @PostMapping
    @ApiOperation(value = "PostCard")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong")
    })
    public ResponseEntity<CardDTO> saveCard(@Valid @RequestBody PostCardDTO postCardDTO, HttpServletRequest request) {
        final Card savedCard = this.cardService.saveCard(this.modelMapper.map(postCardDTO, Card.class));
        final CardId compositeId = savedCard.getCardId();

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

    @GetMapping("/{bin}/{number}/{userId}")
    @ApiOperation(value = "GetCard")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
    })
    public ResponseEntity<CardDTO> getCard(
            @PathVariable("bin") Long bin,
            @PathVariable("number") Long number,
            @PathVariable("userId") Long userId
    ) {
        final Optional<Card> optionalCard = this.cardService.getUserCard(new CardId(bin, number), userId);

        return optionalCard
                .map(card -> ResponseEntity.ok().body(this.modelMapper.map(card, CardDTO.class)))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }
}
