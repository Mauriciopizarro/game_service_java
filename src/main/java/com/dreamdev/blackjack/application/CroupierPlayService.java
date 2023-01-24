package com.dreamdev.blackjack.application;

import com.dreamdev.blackjack.application.implementations.GameImplementation;
import com.dreamdev.blackjack.domain.Game;
import com.dreamdev.blackjack.domain.exceptions.GameFinishedError;
import com.dreamdev.blackjack.domain.exceptions.NotCroupierTurnError;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CroupierPlayService {

    public final GameImplementation gameRepo;

    public void croupierPlay(String gameId) throws GameFinishedError, NotCroupierTurnError {
        Optional<Game> game = gameRepo.findById(gameId);
        game.get().croupierPlay();
        gameRepo.saveGame(game.get());
    }
}
