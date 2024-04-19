package me.liskoh.counter.exceptions.impl;

import me.liskoh.counter.exceptions.CounterAppException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends CounterAppException {

    public UserNotFoundException() {
        super(HttpStatus.NOT_FOUND, "User not found");
    }
    
}
