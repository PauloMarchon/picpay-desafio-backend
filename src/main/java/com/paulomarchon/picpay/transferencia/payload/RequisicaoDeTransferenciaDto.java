package com.paulomarchon.picpay.transferencia.payload;

import com.paulomarchon.picpay.usuario.Usuario;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record RequisicaoDeTransferenciaDto(
        @NotNull Long pagadorId,
        @NotNull Long beneficiarioId,
        @NotNull @Min(1) BigDecimal valor
) {
}
