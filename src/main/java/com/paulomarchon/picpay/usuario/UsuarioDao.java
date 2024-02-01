package com.paulomarchon.picpay.usuario;

import com.paulomarchon.picpay.usuario.payload.UsuarioDto;

import java.util.List;
import java.util.Optional;

public interface UsuarioDao {
    List<Usuario> buscarTodosUsuarios();
    Optional<Usuario> buscarUsuarioPorId(Long id);
    Usuario cadastrarUsuario(Usuario usuario);
    boolean identidadeDeUsuarioJaFoiRegistrado(String identidade);
    boolean emailDeUsuarioJaFoiRegistrado(String email);
}
