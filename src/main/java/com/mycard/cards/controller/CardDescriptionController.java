package com.mycard.cards.controller;

import com.mycard.cards.entity.CardDescription;
import com.mycard.cards.repository.CardDescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/card-description")
@RestController
public class CardDescriptionController {

    @Autowired
    private CardDescriptionRepository cardDescriptionRepository;

    @GetMapping
    public ResponseEntity<Iterable<CardDescription>> getCardDescriptionList() {
        return ResponseEntity.ok().body(cardDescriptionRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<CardDescription> postCardDescription(@RequestBody CardDescription cardDescription) {
        return ResponseEntity.ok().body(cardDescriptionRepository.save(cardDescription));
    }

}
