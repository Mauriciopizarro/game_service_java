package com.dreamdev.blackjack.infrastructure.controllers;

import com.dreamdev.blackjack.application.CroupierPlayService;
import com.dreamdev.blackjack.domain.exceptions.GameFinishedError;
import com.dreamdev.blackjack.domain.exceptions.NotCroupierTurnError;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/game")
public class CroupierPlayController {
    @Autowired
    public CroupierPlayService croupierService;

    @PostMapping("/croupier_play/{gameId}")
    public Map<String, String> croupierPlay(@PathVariable String gameId){
        Map<String, String> response = new HashMap<>();
        try {
            croupierService.croupierPlay(gameId);
        }
        catch (GameFinishedError e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The game_id entered is finished");
        } catch (NotCroupierTurnError e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Is not a Croupier turn");
        }
        response.put("message", "Croupier has played");
        return response;
    }
}
