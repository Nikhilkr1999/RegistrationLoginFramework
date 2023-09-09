package com.humanassist.registration.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FOUND)
public class AlreadyRegisteredException extends Exception{

    public AlreadyRegisteredException(String message) {
        super(message);
    }

}
