package com.paulomarchon.picpay.usuario;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByIdentidade(String identidade);
    boolean existsByEmail(String email);
}
