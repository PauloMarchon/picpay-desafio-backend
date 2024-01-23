package com.paulomarchon.picpay.usuario;

import com.paulomarchon.picpay.exception.IdentidadeInvalidaException;
import com.paulomarchon.picpay.exception.UsuarioComEmailJaCadastradoException;
import com.paulomarchon.picpay.exception.UsuarioComIdentidadeJaCadastradaException;
import com.paulomarchon.picpay.usuario.payload.CadastroUsuarioDto;
import com.paulomarchon.picpay.usuario.payload.UsuarioDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {
    private final UsuarioDao usuarioDao;
    private final UsuarioDtoMapper usuarioDtoMapper;

    public UsuarioService(@Qualifier("usuario-jpa") UsuarioDao usuarioDao, UsuarioDtoMapper usuarioDtoMapper) {
        this.usuarioDao = usuarioDao;
        this.usuarioDtoMapper = usuarioDtoMapper;
    }

    public List<UsuarioDto> buscarTodosUsuarios() {
        return usuarioDao.buscarTodosUsuarios()
                .stream()
                .map(usuarioDtoMapper)
                .collect(Collectors.toList());
    }
    public UsuarioDto cadastrarUsuario(CadastroUsuarioDto cadastroUsuarioDto) {
        if (usuarioDao.identidadeDeUsuarioJaFoiRegistrado(cadastroUsuarioDto.identidade()))
            throw new UsuarioComIdentidadeJaCadastradaException();

        if (usuarioDao.emailDeUsuarioJaFoiRegistrado(cadastroUsuarioDto.email()))
            throw new UsuarioComEmailJaCadastradoException();

        TipoUsuario tipoDoUsuario = defineTipoDoUsuario(cadastroUsuarioDto.identidade());

        Usuario usuario = new Usuario(
                cadastroUsuarioDto.nomeCompleto(),
                cadastroUsuarioDto.identidade(),
                cadastroUsuarioDto.email(),
                cadastroUsuarioDto.senha(),
                tipoDoUsuario
        );
        usuarioDao.cadastrarUsuario(usuario);

        return usuarioDtoMapper.apply(usuario);
    }

    private TipoUsuario defineTipoDoUsuario(String identidade) {
        int quantidadeDeDigitos = identidade.length();

        return switch (quantidadeDeDigitos) {
            case 11 -> TipoUsuario.PESSOA_FISICA;
            case 14 -> TipoUsuario.PESSOA_JURIDICA;
            default -> throw new IdentidadeInvalidaException();
        };
    }
}
