package com.dreamdev.blackjack.infrastructure.controllers;

import com.dreamdev.blackjack.application.CreateGameService;
import com.dreamdev.blackjack.domain.Game;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/game")
@RequiredArgsConstructor
public class CreateGameController {

    Logger logger = LoggerFactory.getLogger(CreateGameController.class);

    @Autowired
    private CreateGameService gameService;

    @PostMapping("/create")
    public Map<String, Object> startGame(@RequestBody CreateGameRequestData requestData){
        Map<String, Object> responseJson = new HashMap<>();
        Game game = gameService.createGame(requestData.name, requestData.id);
        responseJson.put("status", "200_OK");
        responseJson.put("id", game.id);
        return responseJson;
    }
}
