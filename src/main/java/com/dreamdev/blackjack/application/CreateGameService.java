package com.dreamdev.blackjack.application;

import com.dreamdev.blackjack.application.implementations.GameImplementation;
import com.dreamdev.blackjack.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CreateGameService {

    @Autowired
    public GameImplementation gameRepo;

    public Game createGame(String playerName, String playerId) {

        // empty playerList checks here
        List<Card> deck = createDeck();
        Game game = new Game(deck, "playing");
        List<Player> playerList = new ArrayList<>();
        List<Card> cards = new ArrayList<>();

        // create iterator(hashmap) for more than 1 player
        Player admin = new Player(cards, playerName, playerId, "waiting_turn");
        playerList.add(admin);
        game.addPlayers(playerList);
        game.dealInitialCards();
        return gameRepo.saveGame(game);
    }

    public static List<Card> createDeck(){
        List<Card> cards = new ArrayList<>();
        cards.add(new As());
        cards.add(new As());
        cards.add(new As());
        cards.add(new As());
        cards.add(new LetterCard("J"));
        cards.add(new LetterCard("J"));
        cards.add(new LetterCard("J"));
        cards.add(new LetterCard("J"));
        cards.add(new LetterCard("Q"));
        cards.add(new LetterCard("Q"));
        cards.add(new LetterCard("Q"));
        cards.add(new LetterCard("Q"));
        cards.add(new LetterCard("K"));
        cards.add(new LetterCard("K"));
        cards.add(new LetterCard("K"));
        cards.add(new LetterCard("K"));
        cards.add(new NumberCard(2));
        cards.add(new NumberCard(2));
        cards.add(new NumberCard(2));
        cards.add(new NumberCard(2));
        cards.add(new NumberCard(3));
        cards.add(new NumberCard(3));
        cards.add(new NumberCard(3));
        cards.add(new NumberCard(3));
        cards.add(new NumberCard(4));
        cards.add(new NumberCard(4));
        cards.add(new NumberCard(4));
        cards.add(new NumberCard(4));
        cards.add(new NumberCard(5));
        cards.add(new NumberCard(5));
        cards.add(new NumberCard(5));
        cards.add(new NumberCard(5));
        cards.add(new NumberCard(6));
        cards.add(new NumberCard(6));
        cards.add(new NumberCard(6));
        cards.add(new NumberCard(6));
        cards.add(new NumberCard(7));
        cards.add(new NumberCard(7));
        cards.add(new NumberCard(7));
        cards.add(new NumberCard(7));
        cards.add(new NumberCard(8));
        cards.add(new NumberCard(8));
        cards.add(new NumberCard(8));
        cards.add(new NumberCard(8));
        cards.add(new NumberCard(9));
        cards.add(new NumberCard(9));
        cards.add(new NumberCard(9));
        cards.add(new NumberCard(9));
        cards.add(new NumberCard(10));
        cards.add(new NumberCard(10));
        cards.add(new NumberCard(10));
        cards.add(new NumberCard(10));

        Collections.shuffle(cards);
        return cards;
    }

}
