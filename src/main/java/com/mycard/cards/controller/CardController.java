package com.mycard.cards.controller;

import com.mycard.cards.dto.CardDTO;
import com.mycard.cards.dto.PostCardDTO;
import com.mycard.cards.dto.PrincipalDTO;
import com.mycard.cards.service.CardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    private CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping
    @ApiOperation(value = "GetUserCardList")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong")
    })
    public ResponseEntity<List<CardDTO>> getUserCardList(Authentication authentication) {
        return ResponseEntity.ok()
                .body(cardService.getUserCardDTOList(
                        CardDTO.builder()
                                .userId(((PrincipalDTO) authentication.getPrincipal()).getId())
                                .build()
                ));
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
        return this.getCard(bin, number, ((PrincipalDTO) authentication.getPrincipal()).getId());
    }

    @PostMapping
    @ApiOperation(value = "PostCard")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong")
    })
    public ResponseEntity<CardDTO> saveCard(@Valid @RequestBody PostCardDTO postCardDTO, HttpServletRequest request) {
        final CardDTO savedCardDTO = this.cardService.saveCardDTO(postCardDTO);

        final URI location = URI.create(String.format(
                request.getRequestURI() + "/%s/%s",
                savedCardDTO.getBin(),
                savedCardDTO.getNumber()));

        return ResponseEntity.created(location).body(savedCardDTO);
    }

    @PutMapping
    @ApiOperation(value = "PutCard")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 409, message = "Unable to find Card for update")
    })
    public ResponseEntity<CardDTO> putCard(@Valid @RequestBody CardDTO cardDTO) {
        return this.cardService.updateCardDTO(cardDTO)
                .map(card -> ResponseEntity.ok().body(card))
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
        final Optional<CardDTO> optionalCard = this.cardService.getUserCardDTO(
                CardDTO.builder()
                        .bin(bin)
                        .number(number)
                        .userId(userId)
                        .build());

        return optionalCard
                .map(card -> ResponseEntity.ok().body(card))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }
}
