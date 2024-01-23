package com.paulomarchon.picpay.usuario.payload;

import java.math.BigDecimal;

public record UsuarioDto(
        String nomeCompleto,
        String identidade,
        String email,
        String tipoUsuario,
        BigDecimal saldo
) {
}
