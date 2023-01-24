package com.dreamdev.blackjack.infrastructure.controllers;

import com.dreamdev.blackjack.application.DealCardService;
import com.dreamdev.blackjack.application.exceptions.EmptyPlayerIdException;
import com.dreamdev.blackjack.application.exceptions.EmptyQueryResultException;
import com.dreamdev.blackjack.application.exceptions.IncorrectGameId;
import com.dreamdev.blackjack.domain.exceptions.GameFinishedError;
import com.dreamdev.blackjack.domain.exceptions.IncorrectPlayerTurn;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/game")
@RequiredArgsConstructor
public class DealCardController {

    public final DealCardService dealCardService;

    @PostMapping("/deal_card/{gameId}/{playerId}")
    public Map<String, String> dealCard(@PathVariable String gameId, @PathVariable String playerId){

        Map<String, String> response = new HashMap<>();

        try {
            dealCardService.dealCard(playerId, gameId);
        }
        catch (IncorrectPlayerTurn e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Is not a turn to player entered or id may be incorrect ");
        }
        catch (GameFinishedError e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The game_id entered is finished");
        }
        catch (EmptyQueryResultException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "game_id nonexistent");
        }
        catch (IncorrectGameId e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Incorrect game_id");
        }
        catch (EmptyPlayerIdException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Incorrect player_id");
        }
        response.put("message", "Card dealt to player "+ playerId);
        return response;
    }
}
