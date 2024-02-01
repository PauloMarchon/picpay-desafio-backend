package com.paulomarchon.picpay.transferencia;

import org.springframework.stereotype.Repository;

@Repository("transferencia-jpa")
public class TransferenciaJpaDataAccessService implements TransferenciaDao{
    private final TransferenciaRepository transferenciaRepository;

    public TransferenciaJpaDataAccessService(TransferenciaRepository transferenciaRepository) {
        this.transferenciaRepository = transferenciaRepository;
    }

    @Override
    public void salvarTransferencia(Transferencia transferencia) {
        transferenciaRepository.save(transferencia);
    }
}
