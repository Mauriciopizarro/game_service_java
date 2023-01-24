package com.dreamdev.blackjack.domain;

import java.util.ArrayList;
import java.util.List;

public class Croupier extends Player{

    //Logger logger = LoggerFactory.getLogger(Croupier.class);

    public Croupier(List<Card> cards, String name, String status, boolean hasHiddenCard){
        super(cards, name, status);
        this.hasHiddenCard = hasHiddenCard;
    }

    @Override
    public List<String> getCardSymbols() {
        List<String> cardsValues = super.getCardSymbols();

        if (this.hasHiddenCard){
            cardsValues.set(1, "hidden_card");
        }

        return cardsValues;
    }

    @Override
    public List<Integer> getPossiblePoints() {

        List<Integer> totalPointsList = new ArrayList<>();

        if(!this.hasHiddenCard){
            return super.getPossiblePoints();
        }

        if (cards.get(0) instanceof As){
            totalPointsList.add(0, cards.get(0).getValue());
            totalPointsList.add(1, cards.get(0).getValue() + 10);
            return totalPointsList;
        }

        totalPointsList.add(0, cards.get(0).getValue());

        return totalPointsList;
    }
}
