package com.mycard.cards.controller;

import com.mycard.cards.dto.CardFeeDTO;
import com.mycard.cards.dto.PostCardFeeDTO;
import com.mycard.cards.entity.CardFee;
import com.mycard.cards.entity.id.CardFeeId;
import com.mycard.cards.enumeration.CardCompetence;
import com.mycard.cards.enumeration.CardFeature;
import com.mycard.cards.service.CardFeeService;
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
@RequestMapping("card-fees")
@Api(value = "card fee")
public class CardFeeController {

    @Autowired
    private CardFeeService cardFeeService;

    @Autowired
    private ModelMapper modelMapper;

    public CardFeeController(CardFeeService cardFeeService, ModelMapper modelMapper) {
        this.cardFeeService = cardFeeService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @ApiOperation(value = "GetCardFeeList")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong")
    })
    public ResponseEntity<List<CardFeeDTO>> getCardFeeList() {
        final List<CardFee> cardFeeList = this.cardFeeService.getCardFeeList();
        return ResponseEntity.ok().body(this.modelMapper.map(cardFeeList, new TypeToken<List<CardFeeDTO>>() {
        }.getType()));
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
        final Optional<CardFee> optionalCardFee = this.cardFeeService.getCardFee(new CardFeeId(bin, feature, competence));

        return optionalCardFee
                .map(cardFee -> ResponseEntity.ok().body(this.modelMapper.map(cardFee, CardFeeDTO.class)))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PostMapping
    @ApiOperation(value = "PostCardFee")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong")
    })
    public ResponseEntity<CardFeeDTO> postCardFee(@Valid @RequestBody PostCardFeeDTO postCardFeeDTO, HttpServletRequest request) {
        final CardFee savedCardFee = this.cardFeeService.saveCardFee(this.modelMapper.map(postCardFeeDTO, CardFee.class));
        final CardFeeId cardCompositeId = savedCardFee.getCompositeId();
        final URI location = URI.create(String.format(
                request.getRequestURI() + "/%s/%s/%s",
                cardCompositeId.getBin(),
                cardCompositeId.getFeature(),
                cardCompositeId.getCompetence()));

        return ResponseEntity.created(location).body(this.modelMapper.map(savedCardFee, CardFeeDTO.class));
    }

    @PutMapping
    @ApiOperation(value = "PutCardFee")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 409, message = "Unable to find Card Fee for update")
    })
    public ResponseEntity<CardFeeDTO> putCardFee(@Valid @RequestBody CardFeeDTO cardFeeDTO) {
        final Optional<CardFee> optionalCardFee = this.cardFeeService.updateCardFee(this.modelMapper.map(cardFeeDTO, CardFee.class));

        return optionalCardFee
                .map(cardFee -> ResponseEntity.ok().body(this.modelMapper.map(cardFee, CardFeeDTO.class)))
                .orElseGet(() -> ResponseEntity.status(409).build());
    }
}
