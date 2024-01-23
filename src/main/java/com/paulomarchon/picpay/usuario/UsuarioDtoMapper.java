package com.paulomarchon.picpay.usuario;

import com.paulomarchon.picpay.usuario.payload.UsuarioDto;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UsuarioDtoMapper implements Function<Usuario, UsuarioDto> {
    @Override
    public UsuarioDto apply(Usuario usuario) {
        return new UsuarioDto(
                usuario.getNomeCompleto(),
                usuario.getIdentidade(),
                usuario.getEmail(),
                usuario.getTipoUsuario().name(),
                usuario.getSaldo()
        );
    }
}
