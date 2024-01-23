package com.paulomarchon.picpay.usuario;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class UsuarioRepositoryTest {
    @Autowired
    private UsuarioRepository emTeste;
    @Autowired
    ApplicationContext applicationContext;

    @BeforeEach
    void setUp() {
        emTeste.deleteAll();
        System.out.println(applicationContext.getBeanDefinitionCount());
    }

    @Test
    void existsByIdentidade(){
        //Given
        String identidade = "11111111111";
        Usuario usuario = new Usuario(
                "Nome Completo", identidade, "email@email.com", "senha123", TipoUsuario.PESSOA_FISICA
        );
        emTeste.save(usuario);
        // When
        var atual = emTeste.existsByIdentidade(identidade);
        // Then
        assertThat(atual).isTrue();
    }

    @Test
    void existsByEmail(){
        //Given
        String email = "email@email.com";
        Usuario usuario = new Usuario(
                "Nome Completo", "11111111111", email, "senha123", TipoUsuario.PESSOA_FISICA
        );
        emTeste.save(usuario);
        // When
        var atual = emTeste.existsByEmail(email);
        // Then
        assertThat(atual).isTrue();
    }
}
