package com.dreamdev.blackjack.application.implementations;

import com.dreamdev.blackjack.domain.Game;
import com.dreamdev.blackjack.infrastructure.repositories.GameMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameImplementation {

    @Autowired
    private GameMongoRepository gameRepo;

    public Game saveGame(Game game){
        return gameRepo.save(game);
    }

    public Optional<Game> findById(String id){

        return gameRepo.findById(id);

    }

}
