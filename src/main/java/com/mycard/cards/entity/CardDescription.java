package com.mycard.cards.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@RedisHash("CardDescription")
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "card_description")
@Entity
public @Data
class CardDescription implements Serializable {

    @Id
    @javax.persistence.Id
    private String id;
    private String description;

}