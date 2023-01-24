package com.dreamdev.blackjack.application.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Incorrect game_id")
public class IncorrectGameId extends Exception{
}
