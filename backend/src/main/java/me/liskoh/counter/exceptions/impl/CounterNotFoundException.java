package me.liskoh.counter.exceptions.impl;

import me.liskoh.counter.exceptions.CounterAppException;
import org.springframework.http.HttpStatus;

public class CounterNotFoundException extends CounterAppException {

    public CounterNotFoundException(long id) {
        super(HttpStatus.NOT_FOUND, String.format("Counter with id %d not found", id));
    }
    
}
