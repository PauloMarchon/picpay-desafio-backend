package com.paulomarchon.picpay.transferencia.payload;

import com.paulomarchon.picpay.transferencia.TransferenciaStatus;

public record TransferenciaResponse(
        String pagador,
        String beneficiario,
        String valor,
        TransferenciaStatus status,
        String mensagem
) {
}
