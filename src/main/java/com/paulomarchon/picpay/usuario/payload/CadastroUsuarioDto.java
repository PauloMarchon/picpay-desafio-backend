package com.paulomarchon.picpay.usuario.payload;

import jakarta.validation.constraints.*;

public record CadastroUsuarioDto(
        @NotBlank String nomeCompleto,
        @Size(min = 11, max = 14) String identidade,
        @Email @NotNull String email,
        String senha
) {
}
