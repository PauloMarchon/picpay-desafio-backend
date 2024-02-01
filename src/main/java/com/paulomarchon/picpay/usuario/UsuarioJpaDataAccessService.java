package com.paulomarchon.picpay.usuario;

import com.paulomarchon.picpay.usuario.payload.UsuarioDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("usuario-jpa")
public class UsuarioJpaDataAccessService implements UsuarioDao{
    private final UsuarioRepository usuarioRepository;

    public UsuarioJpaDataAccessService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public List<Usuario> buscarTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Usuario cadastrarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public boolean identidadeDeUsuarioJaFoiRegistrado(String identidade) {
        return usuarioRepository.existsByIdentidade(identidade);
    }

    @Override
    public boolean emailDeUsuarioJaFoiRegistrado(String email) {
        return usuarioRepository.existsByEmail(email);
    }
}
