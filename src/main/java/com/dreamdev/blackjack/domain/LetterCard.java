package com.dreamdev.blackjack.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class LetterCard extends Card {

    public LetterCard(String symbol){
        super(10, symbol);
    }

}
