package com.paulomarchon.picpay.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class UsuarioComEmailJaCadastradoException extends RuntimeException {
    public UsuarioComEmailJaCadastradoException() {
        super("Email informado ja foi cadastrado!");
    }
}
