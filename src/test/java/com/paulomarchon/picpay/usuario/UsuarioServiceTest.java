package com.paulomarchon.picpay.usuario;

import com.paulomarchon.picpay.exception.IdentidadeInvalidaException;
import com.paulomarchon.picpay.exception.UsuarioComEmailJaCadastradoException;
import com.paulomarchon.picpay.exception.UsuarioComIdentidadeJaCadastradaException;
import com.paulomarchon.picpay.usuario.payload.CadastroUsuarioDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {
    @Mock
    private UsuarioDao usuarioDao;
    private UsuarioService emTeste;
    private final UsuarioDtoMapper usuarioDtoMapper = new UsuarioDtoMapper();

    @BeforeEach
    void setUp() {
        emTeste = new UsuarioService(usuarioDao, usuarioDtoMapper);
    }

    @Test
    void deveBuscarTodosUsuariosComSucesso(){
        //When
        emTeste.buscarTodosUsuarios();
        //Then
        verify(usuarioDao).buscarTodosUsuarios();
    }

    @Test
    void deveCadastrarUsuarioComSucesso(){
        //Given
        String identidade = "12345678912";
        String email = "email@email.com";

        when(usuarioDao.identidadeDeUsuarioJaFoiRegistrado(identidade)).thenReturn(false);
        when(usuarioDao.emailDeUsuarioJaFoiRegistrado(email)).thenReturn(false);

        CadastroUsuarioDto cadastro = new CadastroUsuarioDto("Nome Completo", identidade, email, "123456");

        //When
        emTeste.cadastrarUsuario(cadastro);

        //Then
        ArgumentCaptor<Usuario> usuarioArgumentCaptor = ArgumentCaptor.forClass(Usuario.class);
        verify(usuarioDao).cadastrarUsuario(usuarioArgumentCaptor.capture());
        Usuario usuarioCapturado = usuarioArgumentCaptor.getValue();

        assertThat(usuarioCapturado.getId()).isNull();
        assertThat(usuarioCapturado.getNomeCompleto()).isEqualTo(cadastro.nomeCompleto());
        assertThat(usuarioCapturado.getEmail()).isEqualTo(cadastro.email());
        assertThat(usuarioCapturado.getIdentidade()).isEqualTo(cadastro.identidade());
        assertThat(usuarioCapturado.getSenha()).isEqualTo(cadastro.senha());
        assertThat(usuarioCapturado.getSaldo()).isZero();
        assertThat(usuarioCapturado.getTipoUsuario()).isEqualTo(TipoUsuario.PESSOA_FISICA);
    }

    @Test
    void deveCadastrarUsuarioComoPessoaFisicaComSucesso(){
        //Given
        String identidade = "12345678912";
        String email = "email@email.com";

        when(usuarioDao.identidadeDeUsuarioJaFoiRegistrado(identidade)).thenReturn(false);
        when(usuarioDao.emailDeUsuarioJaFoiRegistrado(email)).thenReturn(false);

        CadastroUsuarioDto cadastro = new CadastroUsuarioDto("Nome Completo", identidade, email, "123456");

        //When
        emTeste.cadastrarUsuario(cadastro);

        //Then
        ArgumentCaptor<Usuario> usuarioArgumentCaptor = ArgumentCaptor.forClass(Usuario.class);
        verify(usuarioDao).cadastrarUsuario(usuarioArgumentCaptor.capture());
        Usuario usuarioCapturado = usuarioArgumentCaptor.getValue();

        assertThat(usuarioCapturado.getTipoUsuario()).isEqualTo(TipoUsuario.PESSOA_FISICA);

    }

    @Test
    void deveCadastrarUsuarioComoPessoaJuridicaComSucesso(){
        //Given
        String identidade = "12345678912345";
        String email = "email@email.com";

        when(usuarioDao.identidadeDeUsuarioJaFoiRegistrado(identidade)).thenReturn(false);
        when(usuarioDao.emailDeUsuarioJaFoiRegistrado(email)).thenReturn(false);

        CadastroUsuarioDto cadastro = new CadastroUsuarioDto("Nome Completo", identidade, email, "123456");

        //When
        emTeste.cadastrarUsuario(cadastro);

        //Then
        ArgumentCaptor<Usuario> usuarioArgumentCaptor = ArgumentCaptor.forClass(Usuario.class);
        verify(usuarioDao).cadastrarUsuario(usuarioArgumentCaptor.capture());
        Usuario usuarioCapturado = usuarioArgumentCaptor.getValue();

        assertThat(usuarioCapturado.getTipoUsuario()).isEqualTo(TipoUsuario.PESSOA_JURIDICA);
    }
    //Prefira nomes descritivos que indiquem o comportamento sendo testado. Use o formato nomeDoMetodo_Scenario_Expectation.

    @Test
    void deveLancarExcecaoAoCadastrarUsuarioComIdentidadeJaCadastrada(){
        //Given
        String identidade = "12345678909";

        when(usuarioDao.identidadeDeUsuarioJaFoiRegistrado(identidade)).thenReturn(true);

        CadastroUsuarioDto cadastro = new CadastroUsuarioDto("Nome Completo", identidade, "email@email.com", "123456");

        //When
        assertThatThrownBy(() -> emTeste.cadastrarUsuario(cadastro)).isInstanceOf(UsuarioComIdentidadeJaCadastradaException.class).hasMessage("CPF/CNPJ informado ja foi cadastrado!");

        //Then
        verify(usuarioDao, never()).cadastrarUsuario(any());
    }

    @Test
    void deveLancarExcecaoAoCadastrarUsuarioComEmailJaCadastrado(){
        //Given
        String email = "email@email.com";

        when(usuarioDao.emailDeUsuarioJaFoiRegistrado(email)).thenReturn(true);

        CadastroUsuarioDto cadastro = new CadastroUsuarioDto("Nome Completo", "12345678909", email, "123456");

        //When
        assertThatThrownBy(() -> emTeste.cadastrarUsuario(cadastro)).isInstanceOf(UsuarioComEmailJaCadastradoException.class).hasMessage("Email informado ja foi cadastrado!");

        //Then
        verify(usuarioDao, never()).cadastrarUsuario(any());
    }

    @Test
    void deveLancarExcecaoAoCadastrarUsuarioComIdentidadeComFormatoInvalido(){
        //Given
        String identidade = "123456345656";

        when(usuarioDao.identidadeDeUsuarioJaFoiRegistrado(identidade)).thenReturn(false);

        CadastroUsuarioDto cadastro = new CadastroUsuarioDto("Nome Completo", identidade, "email@email.com", "123456");

        //When
        assertThatThrownBy(() -> emTeste.cadastrarUsuario(cadastro)).isInstanceOf(IdentidadeInvalidaException.class).hasMessage("A identidade informada e invalida!");

        //Then
        verify(usuarioDao, never()).cadastrarUsuario(any());
    }
}
