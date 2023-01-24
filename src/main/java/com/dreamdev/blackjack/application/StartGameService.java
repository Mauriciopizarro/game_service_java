package com.dreamdev.blackjack.application;

import com.dreamdev.blackjack.application.exceptions.IncorrectGameId;
import com.dreamdev.blackjack.application.implementations.GameImplementation;
import com.dreamdev.blackjack.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class StartGameService {

    public final GameImplementation gameRepo;


    public Game startGame(Map<String, String> players , String gameId) throws IncorrectGameId {

        if (gameId.isEmpty()){
            throw new IncorrectGameId();
        }

        // empty playerList checks here
        List<Card> deck = createDeck();
        Game game = new Game(deck, "started", gameId);
        List<Player> playerList = new ArrayList<>();
        List<Card> cards = new ArrayList<>();

        // create iterator(hashmap) for more than 1 player
        Player player = new Player(cards, (String) players.get("name"), (String) players.get("id"), "waiting_turn"); //Replace player for dict sent by rabbitMQ
        playerList.add(player);

        game.addPlayers(playerList);
        game.dealInitialCards();
        gameRepo.saveGame(game);
        return game;
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
