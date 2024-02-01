package com.paulomarchon.picpay.usuario;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class UsuarioJpaDataAccessServiceTest {
    private UsuarioJpaDataAccessService emTeste;
    private AutoCloseable autoCloseable;
    @Mock private UsuarioRepository usuarioRepository;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        emTeste = new UsuarioJpaDataAccessService(usuarioRepository);
    }

    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }

    @Test
    void deveCadastrarUsuario(){
        //Given
        Usuario usuario = new Usuario(
                "Nome Completo", "11111111111", "email@email.com", "senha123", TipoUsuario.PESSOA_FISICA
        );
        //When
        emTeste.cadastrarUsuario(usuario);
        //Then
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void deveVerificarSeIdentidadeDeUsuarioJaFoiRegistrado(){
        //Given
        String identidade = "11111111111";
        //When
        emTeste.identidadeDeUsuarioJaFoiRegistrado(identidade);
        //Then
        verify(usuarioRepository).existsByIdentidade(identidade);
    }

    @Test
    void deveVerificarSeEmailDeUsuarioJaFoiRegistrado(){
        //Given
        String email = "email@email.com";
        //When
        emTeste.emailDeUsuarioJaFoiRegistrado(email);
        //Then
        verify(usuarioRepository).existsByEmail(email);
    }

    @Test
    void deveSelecionarUsuarioPorId() {
        //Given
        Long id = 1L;
        //When
        emTeste.buscarUsuarioPorId(id);
        //Then
        verify(usuarioRepository).findById(id);
    }
}
