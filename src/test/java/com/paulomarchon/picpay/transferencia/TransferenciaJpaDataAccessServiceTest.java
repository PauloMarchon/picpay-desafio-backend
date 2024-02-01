package com.paulomarchon.picpay.transferencia;

import com.paulomarchon.picpay.usuario.TipoUsuario;
import com.paulomarchon.picpay.usuario.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.mockito.Mockito.verify;

public class TransferenciaJpaDataAccessServiceTest {
    private TransferenciaJpaDataAccessService emTeste;
    private AutoCloseable autoCloseable;
    @Mock
    private TransferenciaRepository transferenciaRepository;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        emTeste = new TransferenciaJpaDataAccessService(transferenciaRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void deveSalvarTransferencia() {
        //Given
        Usuario usuario1 = new Usuario("Nome Completo", "12345678989", "email@email.com", "senha123", TipoUsuario.PESSOA_FISICA);
        Usuario usuario2 = new Usuario("Nome Completo", "23456789876543", "email@email.com", "senha123", TipoUsuario.PESSOA_JURIDICA);
        Transferencia transferencia = new Transferencia(usuario1, usuario2, BigDecimal.valueOf(100.00));

        //When
        emTeste.salvarTransferencia(transferencia);

        //Then
       verify(transferenciaRepository).save(transferencia);
    }
}
