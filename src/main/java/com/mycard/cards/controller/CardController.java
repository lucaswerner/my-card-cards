package com.mycard.cards.controller;

import com.mycard.cards.dto.CardDTO;
import com.mycard.cards.dto.PostCardDTO;
import com.mycard.cards.dto.PrincipalDTO;
import com.mycard.cards.entity.id.CardId;
import com.mycard.cards.service.CardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

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
        return ResponseEntity
                .ok()
                .body(cardService.getUserCardDTOList(((PrincipalDTO) authentication.getPrincipal()).getId()));
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

    @GetMapping("/admin/{bin}/{number}/{userId}")
    @ApiOperation(value = "GetCard")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
    })
    public ResponseEntity<CardDTO> getCard(
            @PathVariable("bin") Long bin,
            @PathVariable("number") Long number,
            @PathVariable("userId") Long userId
    ) {
        return this.cardService.getUserCardDTO(new CardId(bin, number), userId)
                .map(card -> ResponseEntity.ok().body(card))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping("/admin")
    @ApiOperation(value = "GetCardList")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong")
    })
    public ResponseEntity<Page<CardDTO>> getCardList(
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam("pageNumber") Integer pageNumber
    ) {
        return ResponseEntity
                .ok()
                .body(this.cardService.getCardDTOPage(PageRequest.of(pageNumber, pageSize)));
    }

}
