package com.dreamdev.blackjack.application;

import com.dreamdev.blackjack.application.exceptions.EmptyPlayerIdException;
import com.dreamdev.blackjack.application.exceptions.EmptyQueryResultException;
import com.dreamdev.blackjack.application.exceptions.IncorrectGameId;
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
public class DealCardService {
    @Autowired
    public GameImplementation gameRepo;

    public void dealCard(String playerId, String gameId) throws IncorrectPlayerTurn, GameFinishedError, EmptyQueryResultException, IncorrectGameId, EmptyPlayerIdException {

        if (gameId.isEmpty()){
            throw new IncorrectGameId();
        }

        if (playerId.isEmpty()){
            throw new EmptyPlayerIdException();
        }

        Optional<Game> game = gameRepo.findById(gameId);

        if (game.stream().findAny().isEmpty()){
            throw new EmptyQueryResultException();
        }

        game.get().dealCardToCurrentTurnPlayer(playerId);
        gameRepo.saveGame(game.get());
    }

}
