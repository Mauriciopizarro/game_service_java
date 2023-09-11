package com.dreamdev.blackjack.infrastructure.controllers;

import com.dreamdev.blackjack.application.StatusService;
import com.dreamdev.blackjack.application.exceptions.EmptyQueryResultException;
import com.dreamdev.blackjack.application.exceptions.IncorrectGameId;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/game")
@RequiredArgsConstructor
public class StatusGameController {
    @Autowired
    public StatusService statusService;


    @GetMapping("/status/{gameId}")
    public Map<String, Object> gameStatus(@PathVariable String gameId){
        try {
            return statusService.gameStatus(gameId);
        }
        catch (IncorrectGameId e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Incorrect game_id", e);
        }
        catch (EmptyQueryResultException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found, game_id incorrect or nonexistent");
        }
    }

}
