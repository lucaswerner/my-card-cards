package com.mycard.cards.controller;

import com.mycard.cards.dto.CardClassDTO;
import com.mycard.cards.dto.PostCardClassDTO;
import com.mycard.cards.service.CardClassService;
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
@RequestMapping("card-classes")
@Api(value = "card class")
public class CardClassController {

    private CardClassService cardClassService;

    public CardClassController(CardClassService cardClassService) {
        this.cardClassService = cardClassService;
    }

    @GetMapping
    @ApiOperation(value = "GetCardClassList")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong")
    })
    public ResponseEntity<Page<CardClassDTO>> getCardClassList(
            @RequestParam("pageSize") @NotNull Integer pageSize,
            @RequestParam("pageNumber") @NotNull Integer pageNumber
    ) {
        return ResponseEntity.ok().body(cardClassService.getCardClassDTOPage(PageRequest.of(pageNumber, pageSize)));
    }

    @GetMapping("/{bin}")
    @ApiOperation(value = "GetCardClass")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 204, message = "Could not find card class")
    })
    public ResponseEntity<CardClassDTO> getCardClass(@PathVariable("bin") Long bin) {
        return cardClassService.getCardClassDTO(bin)
                .map(cardClassDTO -> ResponseEntity.ok().body(cardClassDTO))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PostMapping
    @ApiOperation(value = "PostCardClass")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong")
    })
    public ResponseEntity<CardClassDTO> postCardClass(
            @Valid @RequestBody PostCardClassDTO postCardClassDTO,
            HttpServletRequest request
    ) {
        final CardClassDTO cardClassDTO = cardClassService.saveCardClassDTO(postCardClassDTO);
        final URI location = URI.create(String.format(request.getRequestURI() + "/%s", cardClassDTO.getBin()));

        return ResponseEntity.created(location).body(cardClassDTO);
    }

    @PutMapping
    @ApiOperation(value = "PutCardClass")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 409, message = "Unable to find Card Class for update")
    })
    public ResponseEntity<CardClassDTO> putCardClass(@Valid @RequestBody CardClassDTO cardClassRequest) {
        return cardClassService.updateCardClassDTO(cardClassRequest)
                .map(cardClassResponse -> ResponseEntity.ok().body(cardClassResponse))
                .orElseGet(() -> ResponseEntity.status(409).build());
    }
}
