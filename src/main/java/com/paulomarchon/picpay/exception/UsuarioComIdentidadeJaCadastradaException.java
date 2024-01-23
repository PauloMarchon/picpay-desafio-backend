package com.paulomarchon.picpay.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class UsuarioComIdentidadeJaCadastradaException extends RuntimeException {
    public UsuarioComIdentidadeJaCadastradaException() {
        super("CPF/CNPJ informado ja foi cadastrado!");
    }
}
