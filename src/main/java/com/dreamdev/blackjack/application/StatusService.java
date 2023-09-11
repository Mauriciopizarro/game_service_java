package com.dreamdev.blackjack.application;

import com.dreamdev.blackjack.application.exceptions.EmptyQueryResultException;
import com.dreamdev.blackjack.application.exceptions.IncorrectGameId;
import com.dreamdev.blackjack.application.implementations.GameImplementation;
import com.dreamdev.blackjack.domain.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StatusService {
    @Autowired
    public GameImplementation gameRepo;
    //Logger logger = LoggerFactory.getLogger(StatusService.class);

    public Map<String, Object> gameStatus(String gameId) throws IncorrectGameId, EmptyQueryResultException {

        if (gameId.isEmpty()){
            throw new IncorrectGameId();
        }

        Optional<Game> game = gameRepo.findById(gameId);

        if (game.stream().findAny().isEmpty()){
            throw new EmptyQueryResultException();
        }

        return game.get().getStatus();
    }
}
