package com.paulomarchon.picpay.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class UsuarioNaoEncontradoException extends RuntimeException{
    public UsuarioNaoEncontradoException(){
        super("Usuario nao encontrado");
    }
}
