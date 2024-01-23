package com.paulomarchon.picpay.usuario;

import java.util.List;

public interface UsuarioDao {
    List<Usuario> buscarTodosUsuarios();
    Usuario cadastrarUsuario(Usuario usuario);
    boolean identidadeDeUsuarioJaFoiRegistrado(String identidade);
    boolean emailDeUsuarioJaFoiRegistrado(String email);
}
