package com.paulomarchon.picpay.transferencia;

import com.paulomarchon.picpay.usuario.Usuario;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "transferencia")
public class Transferencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pagador_id", nullable = false)
    private Usuario pagador;
    @ManyToOne
    @JoinColumn(name = "beneficiario_id", nullable = false)
    private Usuario benefeciario;
    private BigDecimal valor;

    public Transferencia() {
    }

    public Transferencia(Usuario pagador, Usuario benefeciario, BigDecimal valor) {
        this.pagador = pagador;
        this.benefeciario = benefeciario;
        this.valor = valor;
    }

    public Long getId() {
        return id;
    }

    public Usuario getPagador() {
        return pagador;
    }

    public void setPagador(Usuario pagador) {
        this.pagador = pagador;
    }

    public Usuario getBenefeciario() {
        return benefeciario;
    }

    public void setBenefeciario(Usuario benefeciario) {
        this.benefeciario = benefeciario;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
