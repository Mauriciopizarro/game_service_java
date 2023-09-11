package com.dreamdev.blackjack.application;

import com.dreamdev.blackjack.application.implementations.GameImplementation;
import com.dreamdev.blackjack.domain.Game;
import com.dreamdev.blackjack.domain.exceptions.GameFinishedError;
import com.dreamdev.blackjack.domain.exceptions.IncorrectPlayerTurn;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StandService {
    @Autowired
    public GameImplementation gameRepo;

    public void stand(String gameId, String playerId) throws IncorrectPlayerTurn, GameFinishedError {
        Optional<Game> game = gameRepo.findById(gameId);
        game.get().standCurrentTurnPlayer(playerId);
        gameRepo.saveGame(game.get());
    }
}
