package com.dreamdev.blackjack.domain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Player {

    public List<Card> cards = new ArrayList<>();
    public String name;
    public String id;
    public String status;
    @Field
    public boolean hasHiddenCard;


    public Player(List<Card> cards, String name, String status){
        this.cards = cards;
        this.name = name;
        this.status = status;
    }

    public Player(List<Card> cards, String name, String id, String status){
        this.cards = cards;
        this.name = name;
        this.id = id;
        this.status = status;
    }


    public void receiveCards(List<Card> newCards){
        cards.addAll(newCards);
    }

    public List<String> getCardSymbols(){
        List<String> cardsSymbols = new ArrayList<>();
        for (Card card: cards){
            cardsSymbols.add(card.getSymbol());
        }
        return  cardsSymbols;
    }

    public List<Integer> getPossiblePoints(){
        int totalPoints = 0;
        List<Integer> totalPointsList = new ArrayList<>();
        boolean thereIsAs = false;

        for (Card card: this.cards){
            if (Objects.equals(card.symbol, "A")){
                thereIsAs = true;
            }
            totalPoints += card.getValue();
        }

        totalPointsList.add(totalPoints);
        int totalPointWithAs = totalPoints + 10;

        if (thereIsAs && totalPointWithAs <= 21){
            totalPointsList.add(totalPointWithAs);
        }

        if (totalPointsList.contains(21)){
            totalPointsList.clear();
            totalPointsList.add(21);
        }

        return totalPointsList;
    }

    public int getTotalPoints(){
        int totalPoints = 0;
        for (Card card: cards){
            totalPoints += card.getValue();
        }
        return totalPoints;
    }

    public boolean isOver21Points(){
        return this.getTotalPoints() > 21;
    }

    public boolean isStand(){
        return status.equals("waiting_croupier");
    }

    public void stand(){
        status = "waiting_croupier";
    }

    public void setAsWinner(){
        status = "winner";
    }

    public void setAsPlaying(){
        status = "playing";
    }

    public void setAsLooser(){
        status = "looser";
    }

    public boolean isOverLimit(){
        return this.getTotalPoints() > 21;
    }

    public Map<String, Object> getStatus(){
        Map<String, Object> dict = new HashMap<>();

        dict.put("id", id);
        dict.put("name", name);
        dict.put("cards", getCardSymbols());
        dict.put("totalPoints", getPossiblePoints());
        dict.put("status", status);
        dict.put("isStand", isStand());

        return dict;
    }

}