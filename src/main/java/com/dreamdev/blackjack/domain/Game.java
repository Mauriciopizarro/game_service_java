package com.dreamdev.blackjack.domain;

import com.dreamdev.blackjack.domain.exceptions.GameFinishedError;
import com.dreamdev.blackjack.domain.exceptions.IncorrectPlayerTurn;
import com.dreamdev.blackjack.domain.exceptions.NotCroupierTurnError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "Game")
public class Game {

    @Id
    public String id;

    public List<Player> turnOrder = new ArrayList<>();
    public List<Card> deck = new ArrayList<>();
    public String gameStatus;
    public int turnPosition = 0;

    //Logger logger = LoggerFactory.getLogger(Game.class);


    public Game(List<Card> deck, String status, String gameId){
        this.deck = deck;
        this.gameStatus = status;
        this.id = gameId;
    }


    public List<Player> players(){
        List<Player> players = new ArrayList<>(turnOrder);
        players.remove(players.size() - 1); // Always should be Croupier in last position
        return players;
    }

    public Player croupier(){
        return turnOrder.get(turnOrder.size()-1);
    }

    public List<Card> getCards(int quantityCards){
        List<Card> cards = new ArrayList<>();

        for (int i=0; i < quantityCards; i++){
            int randomIndex = new Random().nextInt(deck.size());
            Card card = deck.get(randomIndex);
            cards.add(card);
            deck.remove(randomIndex);
        }

        return cards;
    }

    public void changeTurn(){
        turnPosition += 1;
        Player player = turnOrder.get(turnPosition);
        player.setAsPlaying();
    }

    public boolean allPlayerOverLimit(){
        for (Player player: players()){
            if (!player.isOver21Points()){
                return false;
            }
        }
        croupier().setAsWinner();
        gameStatus = "finished";
        return true;
    }

    public boolean checkCroupierVictory(){
        if (this.allPlayerOverLimit()){
            return true;
        }
        int croupierPoints = croupier().getTotalPoints();
        if (croupierPoints < 17){
            return false;
        }
        for (Player player: players()){
            if ((player.getTotalPoints() > croupierPoints) && (!player.isOverLimit())){
                return false;
            }
        }
        croupier().setAsWinner();
        for (Player player: players()){
            if (player.getTotalPoints() == croupierPoints){
                player.setAsWinner();
                continue;
            }
            player.setAsLooser();
        }
        gameStatus = "finished";
        return true;
    }

    public boolean checkCroupierDefeat(){
        if (croupier().isOverLimit()){
            for (Player player: players()){
                if (player.getTotalPoints() <= 21){
                    player.setAsWinner();
                }
            }
            croupier().setAsLooser();
            gameStatus = "finished";
            return true;
        }

        return false;
    }

    public String getPlayerIdOfCurrentTurn(){
        Player player = turnOrder.get(turnPosition);

        if (player instanceof Croupier){
            return player.getName();
        }
        return player.getId();
    }

    public boolean isPlayerTurn(String playerId){
        return playerId.equals(getPlayerIdOfCurrentTurn());
    }

    public void dealCardToCurrentTurnPlayer(String playerId) throws GameFinishedError, IncorrectPlayerTurn {

        if (this.gameStatus.equals("finished")){
            throw new GameFinishedError();
        }

        if(!isPlayerTurn(playerId)){
            throw new IncorrectPlayerTurn();
        }

        Player player = turnOrder.get(turnPosition);
        player.receiveCards(getCards(1));

        if (player.isOverLimit()){
            player.setAsLooser();
            this.changeTurn();
        }

        this.allPlayerOverLimit();

    }

    public void standCurrentTurnPlayer(String playerId) throws GameFinishedError, IncorrectPlayerTurn {
        if (this.gameStatus.equals("finished")){
            throw new GameFinishedError();
        }

        if(!isPlayerTurn(playerId)){
            throw new IncorrectPlayerTurn();
        }

        Player player = turnOrder.get(turnPosition);
        player.stand();
        this.changeTurn();

    }

    public boolean isThereWinner(){
        if (this.checkCroupierDefeat()){
            return true;
        }
        return this.checkCroupierVictory();
    }

    public void croupierPlay() throws GameFinishedError, NotCroupierTurnError{
        if (this.gameStatus.equals("finished")){
            throw new GameFinishedError();
        }
        String currentPlayerName = getPlayerIdOfCurrentTurn(); // if is croupier returns name not id
        if (!currentPlayerName.equals("Croupier")){
            throw new NotCroupierTurnError();
        }
        croupier().setHasHiddenCard(false);
        while (!this.isThereWinner()){
            croupier().receiveCards(getCards(1));
        }
    }

    public Map<String, Object> getStatus(){
        Map<String, Object> dict = new HashMap<>();
        List<Map<String, Object>> playersStatusList = new ArrayList<>();
        for (Player player: players()){
            playersStatusList.add(player.getStatus());
        }

        dict.put("players_quantity", this.players().size());
        dict.put("status_game", this.gameStatus);
        dict.put("players", playersStatusList);
        dict.put("croupier", croupier().getStatus());

        return dict;
    }

    public void addPlayers(List<Player> players){
        List<Card> cards = new ArrayList<>();
        Croupier croupier = new Croupier(cards, "Croupier", "waiting_turn", true);
        turnOrder.add(croupier);
        for (Player player: players){
            turnOrder.add(0, player);
        }
    }

    public void dealInitialCards(){
        for (Player player: turnOrder){
            player.receiveCards(this.getCards(2));
        }
        turnOrder.get(0).setAsPlaying();
    }

}
