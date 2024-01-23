package com.paulomarchon.picpay.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class IdentidadeInvalidaException extends RuntimeException{
    public IdentidadeInvalidaException() {
        super("A identidade informada e invalida!");
    }
}
