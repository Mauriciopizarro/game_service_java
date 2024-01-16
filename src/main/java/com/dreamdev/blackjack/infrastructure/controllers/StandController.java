package com.dreamdev.blackjack.infrastructure.controllers;
import com.dreamdev.blackjack.application.StandService;
import com.dreamdev.blackjack.domain.exceptions.GameFinishedError;
import com.dreamdev.blackjack.domain.exceptions.IncorrectPlayerTurn;
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
public class StandController {
    @Autowired
    public StandService standService;

    @PostMapping("/stand/{gameId}/{playerId}")
    public Map<String, String> standPlayer(@PathVariable String gameId, @PathVariable String playerId){
        Map<String, String> response = new HashMap<>();
        try {
            standService.stand(gameId, playerId);
        }
        catch (IncorrectPlayerTurn e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Is not a turn to player entered or id may be incorrect ");
        }
        catch (GameFinishedError e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The game_id entered is finished");
        }

        response.put("message", "Stood player "+ playerId);
        return response;
    }
}
