package com.mycard.users.controller;

import com.mycard.users.dto.CardClassDTO;
import com.mycard.users.dto.PostCardClassDTO;
import com.mycard.users.entity.CardClass;
import com.mycard.users.service.CardClassService;
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
@RequestMapping("card-classes")
@Api(value = "card class")
public class CardClassController {

    @Autowired
    private CardClassService cardClassService;

    @Autowired
    private ModelMapper modelMapper;

    public CardClassController(CardClassService cardClassService, ModelMapper modelMapper) {
        this.cardClassService = cardClassService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @ApiOperation(value = "GetCardClassList")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong")
    })
    public ResponseEntity<List<CardClassDTO>> getCardClassList() {
        final List<CardClass> cardClassList = this.cardClassService.getCardClassList();
        return ResponseEntity.ok().body(this.modelMapper.map(cardClassList, new TypeToken<List<CardClassDTO>>() {
        }.getType()));
    }

    @GetMapping("/{bin}")
    @ApiOperation(value = "GetCardClass")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 204, message = "Could not find card class")
    })
    public ResponseEntity<CardClassDTO> getCardClass(@PathVariable("bin") Long bin) {
        final Optional<CardClass> optionalCardClass = this.cardClassService.getCardClass(bin);

        return optionalCardClass
                .map(cardClass -> ResponseEntity.ok().body(this.modelMapper.map(cardClass, CardClassDTO.class)))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PostMapping
    @ApiOperation(value = "PostCardClass")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong")
    })
    public ResponseEntity<CardClassDTO> postCardClass(@Valid @RequestBody PostCardClassDTO postCardClassDTO, HttpServletRequest request) {
        final CardClass savedCardClass = this.cardClassService.saveCardClass(this.modelMapper.map(postCardClassDTO, CardClass.class));
        final URI location = URI.create(String.format(request.getRequestURI() + "/%s", savedCardClass.getBin()));

        return ResponseEntity.created(location).body(this.modelMapper.map(savedCardClass, CardClassDTO.class));
    }

    @PutMapping
    @ApiOperation(value = "PutCardClass")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 409, message = "Unable to find Card Class for update")
    })
    public ResponseEntity<CardClassDTO> putCardClass(@Valid @RequestBody CardClassDTO cardClassDTO) {
        final Optional<CardClass> optionalCardClass = this.cardClassService.updateCardClass(this.modelMapper.map(cardClassDTO, CardClass.class));

        return optionalCardClass
                .map(cardClass -> ResponseEntity.ok().body(this.modelMapper.map(cardClass, CardClassDTO.class)))
                .orElseGet(() -> ResponseEntity.status(409).build());
    }
}
