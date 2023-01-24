package com.dreamdev.blackjack.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class NumberCard extends Card {

    public NumberCard(int value){
        super(value, Integer.toString(value));
    }

}
