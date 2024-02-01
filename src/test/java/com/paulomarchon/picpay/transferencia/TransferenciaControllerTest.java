package com.paulomarchon.picpay.transferencia;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paulomarchon.picpay.exception.SaldoInsuficienteException;
import com.paulomarchon.picpay.transferencia.payload.RequisicaoDeTransferenciaDto;
import com.paulomarchon.picpay.transferencia.payload.TransferenciaResponse;
import com.paulomarchon.picpay.usuario.TipoUsuario;
import com.paulomarchon.picpay.usuario.Usuario;
import com.paulomarchon.picpay.usuario.UsuarioService;
import com.paulomarchon.picpay.usuario.payload.UsuarioDto;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransferenciaController.class)
@ExtendWith(MockitoExtension.class)
public class TransferenciaControllerTest {
    @MockBean
    private TransferenciaService transferenciaService;
    @MockBean
    private UsuarioService usuarioService;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    Usuario pagador;
    Usuario beneficiario;

    @BeforeEach
    void setUp() {
        pagador = new Usuario(1L,"Nome Pagador", "12345678989", "fisico@email.com", "senha123" ,TipoUsuario.PESSOA_FISICA);
        beneficiario = new Usuario(2L, "Nome Beneficiario", "23456789876543", "juridico@email.com", "senha123" ,TipoUsuario.PESSOA_JURIDICA);
    }

    @Test
    void TransferenciaController_RealizarTransferencia_RetornarOkComCorpoDaTransferencia() throws Exception {
        pagador.setSaldo(BigDecimal.valueOf(500.00));
        BigDecimal valorTransacao = BigDecimal.valueOf(100.00);

        RequisicaoDeTransferenciaDto transferenciaDto = new RequisicaoDeTransferenciaDto(pagador.getId(), beneficiario.getId(), valorTransacao);

        TransferenciaResponse respostaEsperada = new TransferenciaResponse(
                pagador.getNomeCompleto(),
                beneficiario.getNomeCompleto(),
                valorTransacao.toEngineeringString(),
                TransferenciaStatus.SUCESSO,
                "Transferencia realizada com sucesso!"
        );

        when(usuarioService.buscarUsuarioPorId(1L)).thenReturn(pagador);
        when(usuarioService.buscarUsuarioPorId(2L)).thenReturn(beneficiario);
        when(transferenciaService.realizarTransferencia(any(RequisicaoDeTransferenciaDto.class))).thenReturn(respostaEsperada);

        ResultActions result = mockMvc.perform(post("/api/v1/transferencia")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transferenciaDto)));

        result.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pagador", CoreMatchers.is(respostaEsperada.pagador())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.beneficiario", CoreMatchers.is(respostaEsperada.beneficiario())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.valor", CoreMatchers.is(respostaEsperada.valor())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(respostaEsperada.status().name())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", CoreMatchers.is("Transferencia realizada com sucesso!")));
    }

    @Test
    void TransferenciaController_RealizarTransferencia_RetornarBadRequest() throws Exception {
        pagador.setSaldo(BigDecimal.valueOf(200.00));
        BigDecimal valorTransacao = BigDecimal.valueOf(500.00);

        RequisicaoDeTransferenciaDto transferenciaDto = new RequisicaoDeTransferenciaDto(pagador.getId(), beneficiario.getId(), valorTransacao);

        TransferenciaResponse respostaEsperada = new TransferenciaResponse(
                pagador.getNomeCompleto(),
                beneficiario.getNomeCompleto(),
                valorTransacao.toEngineeringString(),
                TransferenciaStatus.FALHA,
                "Falha ao tentar realizar a transferencia!"
        );

        when(usuarioService.buscarUsuarioPorId(1L)).thenReturn(pagador);
        when(usuarioService.buscarUsuarioPorId(2L)).thenReturn(beneficiario);
        when(transferenciaService.realizarTransferencia(any(RequisicaoDeTransferenciaDto.class))).thenReturn(respostaEsperada);

        ResultActions result = mockMvc.perform(post("/api/v1/transferencia")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transferenciaDto)));

        result.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pagador", CoreMatchers.is(respostaEsperada.pagador())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.beneficiario", CoreMatchers.is(respostaEsperada.beneficiario())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.valor", CoreMatchers.is(respostaEsperada.valor())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(respostaEsperada.status().name())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", CoreMatchers.is("Falha ao tentar realizar a transferencia!")));
    }
}

