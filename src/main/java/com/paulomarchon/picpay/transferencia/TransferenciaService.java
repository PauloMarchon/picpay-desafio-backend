package com.paulomarchon.picpay.transferencia;

import com.paulomarchon.picpay.exception.SaldoInsuficienteException;
import com.paulomarchon.picpay.exception.TransacaoNaoPermitidaException;
import com.paulomarchon.picpay.transferencia.payload.RequisicaoDeTransferenciaDto;
import com.paulomarchon.picpay.transferencia.payload.TransferenciaResponse;
import com.paulomarchon.picpay.usuario.TipoUsuario;
import com.paulomarchon.picpay.usuario.Usuario;
import com.paulomarchon.picpay.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransferenciaService {
    private final TransferenciaDao transferenciaDao;
    private final UsuarioService usuarioService;

    public TransferenciaService(@Qualifier("transferencia-jpa") TransferenciaDao transferenciaDao, UsuarioService usuarioService) {
        this.transferenciaDao = transferenciaDao;
        this.usuarioService = usuarioService;
    }

    public TransferenciaResponse realizarTransferencia(RequisicaoDeTransferenciaDto requisicaoDeTransferenciaDto) {
        Usuario pagador = usuarioService.buscarUsuarioPorId(requisicaoDeTransferenciaDto.pagadorId());
        Usuario beneficiario = usuarioService.buscarUsuarioPorId(requisicaoDeTransferenciaDto.beneficiarioId());

        if (usuarioNaoPodeRealizarTransacao(pagador))
            throw new TransacaoNaoPermitidaException();

        if (usuarioNaoPossuiSaldoSuficienteParaRealizarTransacao(pagador, requisicaoDeTransferenciaDto.valor()))
            throw new SaldoInsuficienteException();

        try{
            usuarioService.subtrairDoSaldoDoUsuario(pagador, requisicaoDeTransferenciaDto.valor());
            usuarioService.adicionarAoSaldoDoUsuario(beneficiario, requisicaoDeTransferenciaDto.valor());

            Transferencia transferencia = new Transferencia(
                    pagador,
                    beneficiario,
                    requisicaoDeTransferenciaDto.valor()
            );

            transferenciaDao.salvarTransferencia(transferencia);

            return new TransferenciaResponse(
                    pagador.getNomeCompleto(),
                    beneficiario.getNomeCompleto(),
                    requisicaoDeTransferenciaDto.valor().toEngineeringString(),
                    TransferenciaStatus.SUCESSO,
                    "Transferencia realizada com sucesso!"
            );

        } catch (Exception e) {
            return new TransferenciaResponse(
                    pagador.getNomeCompleto(),
                    beneficiario.getNomeCompleto(),
                    requisicaoDeTransferenciaDto.valor().toEngineeringString(),
                    TransferenciaStatus.FALHA,
                    "Falha ao tentar realizar a transferencia!"
            );
        }
    }

    public boolean usuarioNaoPossuiSaldoSuficienteParaRealizarTransacao(Usuario pagador, BigDecimal valor) {
        return pagador.getSaldo().compareTo(valor) < 0;
    }

    public boolean usuarioNaoPodeRealizarTransacao(Usuario pagador){
        return pagador.getTipoUsuario() == TipoUsuario.PESSOA_JURIDICA;
    }
}
