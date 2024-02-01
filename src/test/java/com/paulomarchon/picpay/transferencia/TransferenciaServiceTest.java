package com.paulomarchon.picpay.transferencia;

import com.paulomarchon.picpay.transferencia.payload.RequisicaoDeTransferenciaDto;
import com.paulomarchon.picpay.transferencia.payload.TransferenciaResponse;
import com.paulomarchon.picpay.usuario.TipoUsuario;
import com.paulomarchon.picpay.usuario.Usuario;
import com.paulomarchon.picpay.usuario.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class TransferenciaServiceTest {
    @Mock
    private TransferenciaDao transferenciaDao;
    @Mock
    private UsuarioService usuarioService;
    private TransferenciaService emTeste;
    Usuario usuarioFisico;
    Usuario usuarioJuridico;

    @BeforeEach
    void setUp() {
        emTeste = new TransferenciaService(transferenciaDao, usuarioService);
        usuarioFisico = new Usuario(1L,"Primero Completo", "12345678989", "fisico@email.com", "senha123" ,TipoUsuario.PESSOA_FISICA);
        usuarioJuridico = new Usuario(2L, "Segundo Completo", "23456789876543", "juridico@email.com", "senha123" ,TipoUsuario.PESSOA_JURIDICA);
    }

    @Test
    void deveRealizarUmaTransferenciaComSucesso() {
        //Given
        usuarioFisico.setSaldo(BigDecimal.valueOf(100.00));
        BigDecimal valorDaTransacao = BigDecimal.valueOf(50.00);
        RequisicaoDeTransferenciaDto requisicaoTransferencia = new RequisicaoDeTransferenciaDto(usuarioFisico.getId(), usuarioJuridico.getId(), valorDaTransacao);

        //When
        when(usuarioService.buscarUsuarioPorId(1L)).thenReturn(usuarioFisico);
        when(usuarioService.buscarUsuarioPorId(2L)).thenReturn(usuarioJuridico);

        TransferenciaResponse respostaEsperada = emTeste.realizarTransferencia(requisicaoTransferencia);
        boolean resultadoNaoPossuiSaldo = emTeste.usuarioNaoPossuiSaldoSuficienteParaRealizarTransacao(usuarioFisico, valorDaTransacao);
        boolean resultadoNaoPodeRealizarTransferencia = emTeste.usuarioNaoPodeRealizarTransacao(usuarioFisico);

        //Then
        assertThat(resultadoNaoPossuiSaldo).isFalse();
        assertThat(resultadoNaoPodeRealizarTransferencia).isFalse();
        assertThat(usuarioFisico.getNomeCompleto()).isEqualTo(respostaEsperada.pagador());
        assertThat(usuarioJuridico.getNomeCompleto()).isEqualTo(respostaEsperada.beneficiario());
        assertThat(valorDaTransacao.toEngineeringString()).isEqualTo(respostaEsperada.valor());
        assertThat(TransferenciaStatus.SUCESSO).isEqualTo(respostaEsperada.status());
        assertThat(usuarioFisico.getSaldo().compareTo(BigDecimal.valueOf(50.00))).isGreaterThanOrEqualTo(0);
    }



    @Test
    void deveTestarSeUsuarioNaoPossuiSaldoSuficienteParaRealizarTransacaoComSucesso() {
        //Given
        usuarioFisico.setSaldo(BigDecimal.valueOf(100.00));
        BigDecimal valorDaTransacao = BigDecimal.valueOf(150.00);

        //When
        boolean result = emTeste.usuarioNaoPossuiSaldoSuficienteParaRealizarTransacao(usuarioFisico, valorDaTransacao);

        //Then
        assertThat(usuarioFisico.getSaldo()).isLessThan(valorDaTransacao);
        assertThat(result).isTrue();
    }

    @Test
    void deveTestarSeUsuarioNaoPossuiSaldoSuficienteParaRealizarTransacaoComFalha() {
        //Given
        usuarioFisico.setSaldo(BigDecimal.valueOf(100.00));
        BigDecimal valorDeTransacao = BigDecimal.valueOf(50.00);

        //When
        boolean result = emTeste.usuarioNaoPossuiSaldoSuficienteParaRealizarTransacao(usuarioFisico, valorDeTransacao);

        //Then
        assertThat(usuarioFisico.getSaldo()).isGreaterThanOrEqualTo(valorDeTransacao);
        assertThat(result).isFalse();
    }

    @Test
    void deveTestarSeUsuarioNaoPodeRealizarTransacaoComSucesso() {
        boolean result = emTeste.usuarioNaoPodeRealizarTransacao(usuarioFisico);

        assertThat(result).isFalse();
    }

    @Test
    void deveTestarSeUsuarioNaoPodeRealizarTransacaoComFalha() {
        boolean result = emTeste.usuarioNaoPodeRealizarTransacao(usuarioJuridico);

        assertThat(result).isTrue();
    }
}
