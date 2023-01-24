package com.dreamdev.blackjack.infrastructure.controllers;

import com.dreamdev.blackjack.application.StartGameService;
import com.dreamdev.blackjack.application.exceptions.IncorrectGameId;
import com.dreamdev.blackjack.domain.Game;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/game")
@RequiredArgsConstructor
public class StartGameController {

    private final StartGameService gameService;

    @PostMapping("/start")
    public Map<String, Object> startGame(){
        try {
            Map<String, String> player = new HashMap<>();
            Map<String, Object> responseJson = new HashMap<>();
            Map<String, String> adminJson = new HashMap<>();

            player.put("name", "Maurjjx");
            player.put("id", "100");

            Game game = gameService.startGame(player, "999");

            adminJson.put("id", player.get("id"));
            adminJson.put("name", player.get("name"));

            responseJson.put("game_id", game.getId());
            responseJson.put("status", game.getGameStatus());
            responseJson.put("game_admin", adminJson);

            return responseJson;
        }
        catch (IncorrectGameId e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Incorrect game_id", e);
        }
    }
}
