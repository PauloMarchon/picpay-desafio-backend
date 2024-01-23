package com.paulomarchon.picpay.usuario;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paulomarchon.picpay.usuario.payload.CadastroUsuarioDto;
import com.paulomarchon.picpay.usuario.payload.UsuarioDto;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
@ExtendWith(MockitoExtension.class)
public class UsuarioControllerTest {
    @MockBean
    private UsuarioService usuarioService;
    @MockBean
    private UsuarioDtoMapper usuarioDtoMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp(){

    }

    @Test
    void UsuarioController_BuscarTodosUsuarios_ComSucesso() throws Exception{
        UsuarioDto usuario1 = new UsuarioDto("Nome Primeiro", "12345678909", "primeiro@email.com", TipoUsuario.PESSOA_FISICA.name(), BigDecimal.ZERO);
        UsuarioDto usuario2 = new UsuarioDto("Nome Segundo", "1545558909", "segundo@email.com", TipoUsuario.PESSOA_FISICA.name(), BigDecimal.ZERO);
        UsuarioDto usuario3 = new UsuarioDto("Nome Terceiro", "12342345678909", "terceiro@email.com", TipoUsuario.PESSOA_JURIDICA.name(), BigDecimal.ZERO);

        when(usuarioService.buscarTodosUsuarios()).thenReturn(Arrays.asList(usuario1, usuario2, usuario3));

        ResultActions result = mockMvc.perform(get("/api/v1/usuario")
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())

                .andExpect(jsonPath("$[0].nomeCompleto", CoreMatchers.is(usuario1.nomeCompleto())))
                .andExpect(jsonPath("$[0].identidade", CoreMatchers.is(usuario1.identidade())))
                .andExpect(jsonPath("$[0].email", CoreMatchers.is(usuario1.email())))
                .andExpect(jsonPath("$[0].tipoUsuario", CoreMatchers.is(usuario1.tipoUsuario())))
                .andExpect(jsonPath("$[0].saldo", CoreMatchers.is(0)))

                .andExpect(jsonPath("$[1].nomeCompleto", CoreMatchers.is(usuario2.nomeCompleto())))
                .andExpect(jsonPath("$[1].identidade", CoreMatchers.is(usuario2.identidade())))
                .andExpect(jsonPath("$[1].email", CoreMatchers.is(usuario2.email())))
                .andExpect(jsonPath("$[1].tipoUsuario", CoreMatchers.is(usuario2.tipoUsuario())))
                .andExpect(jsonPath("$[1].saldo", CoreMatchers.is(0)))

                .andExpect(jsonPath("$[2].nomeCompleto", CoreMatchers.is(usuario3.nomeCompleto())))
                .andExpect(jsonPath("$[2].identidade", CoreMatchers.is(usuario3.identidade())))
                .andExpect(jsonPath("$[2].email", CoreMatchers.is(usuario3.email())))
                .andExpect(jsonPath("$[2].tipoUsuario", CoreMatchers.is(usuario3.tipoUsuario())))
                .andExpect(jsonPath("$[2].saldo", CoreMatchers.is(0)));
    }

    @Test
    void UsuarioController_CadastrarUsuario_RetornarCreated() throws Exception{
        CadastroUsuarioDto cadastroUsuarioDto = new CadastroUsuarioDto("Nome Completo", "12345678909", "email@email.com", "123456");

        UsuarioDto respostaEsperada = new UsuarioDto(
                cadastroUsuarioDto.nomeCompleto(),
                cadastroUsuarioDto.identidade(),
                cadastroUsuarioDto.email(),
                TipoUsuario.PESSOA_FISICA.name(),
                BigDecimal.ZERO
        );

        when(usuarioService.cadastrarUsuario(any(CadastroUsuarioDto.class))).thenReturn(respostaEsperada);

        ResultActions result = mockMvc.perform(post("/api/v1/usuario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cadastroUsuarioDto)));

        result.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nomeCompleto", CoreMatchers.is(respostaEsperada.nomeCompleto())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.identidade", CoreMatchers.is(respostaEsperada.identidade())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(respostaEsperada.email())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tipoUsuario", CoreMatchers.is(respostaEsperada.tipoUsuario())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.saldo", CoreMatchers.is(0)));
    }
}
