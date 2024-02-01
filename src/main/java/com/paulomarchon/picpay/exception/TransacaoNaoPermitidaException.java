package com.paulomarchon.picpay.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class TransacaoNaoPermitidaException extends RuntimeException{
    public TransacaoNaoPermitidaException(){
        super("Lojistas nao podem realizar transacoes");
    }
}
