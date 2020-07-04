package com.mycard.cards.controller;

import com.mycard.cards.dto.CardFeeDTO;
import com.mycard.cards.dto.PostCardFeeDTO;
import com.mycard.cards.entity.id.CardFeeId;
import com.mycard.cards.enumeration.CardCompetence;
import com.mycard.cards.enumeration.CardFeature;
import com.mycard.cards.service.CardFeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;

@RestController
@RequestMapping("card-fees")
@Api(value = "card fee")
public class CardFeeController {

    private CardFeeService cardFeeService;

    public CardFeeController(CardFeeService cardFeeService) {
        this.cardFeeService = cardFeeService;
    }

    @GetMapping
    @ApiOperation(value = "CardFeePage")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong")
    })
    public ResponseEntity<Page<CardFeeDTO>> getCardFeePage(
            @RequestParam("pageNumber") @NotNull Integer pageNumber,
            @RequestParam("pageSize") @NotNull Integer pageSize
    ) {
        return ResponseEntity.ok().body(cardFeeService.getCardFeeDTOPage(PageRequest.of(pageNumber, pageSize)));
    }

    @GetMapping("/{bin}/{feature}/{competence}")
    @ApiOperation(value = "GetCardFee")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 204, message = "Could not find card fee")
    })
    public ResponseEntity<CardFeeDTO> getCardFee(
            @PathVariable("bin") Long bin,
            @PathVariable("feature") CardFeature feature,
            @PathVariable("competence") CardCompetence competence) {
        return cardFeeService.getCardFeeDTO(new CardFeeId(bin, feature, competence))
                .map(cardFeeDTO -> ResponseEntity.ok().body(cardFeeDTO))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PostMapping
    @ApiOperation(value = "PostCardFee")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong")
    })
    public ResponseEntity<CardFeeDTO> postCardFee(
            @Valid @RequestBody PostCardFeeDTO postCardFeeDTO,
            HttpServletRequest request
    ) {
        final CardFeeDTO cardFeeDTO = cardFeeService.saveCardFeeDTO(postCardFeeDTO);
        final URI location = URI.create(String.format(
                request.getRequestURI() + "/%s/%s/%s",
                cardFeeDTO.getBin(),
                cardFeeDTO.getFeature(),
                cardFeeDTO.getCompetence()));

        return ResponseEntity.created(location).body(cardFeeDTO);
    }

    @PutMapping
    @ApiOperation(value = "PutCardFee")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 409, message = "Unable to find Card Fee for update")
    })
    public ResponseEntity<CardFeeDTO> putCardFee(@Valid @RequestBody CardFeeDTO cardFeeDTORequest) {
        return cardFeeService.updateCardFeeDTO(cardFeeDTORequest)
                .map(cardFeeDTOResponse -> ResponseEntity.ok().body(cardFeeDTOResponse))
                .orElseGet(() -> ResponseEntity.status(409).build());
    }
}
