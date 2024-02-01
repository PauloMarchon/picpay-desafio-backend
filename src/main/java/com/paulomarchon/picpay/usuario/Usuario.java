package com.paulomarchon.picpay.usuario;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "usuario",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "identidade"),
        @UniqueConstraint(columnNames = "email")
    })
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeCompleto;
    private String identidade;
    private String email;
    private String senha;
    private BigDecimal saldo;
    @Enumerated(EnumType.STRING)
    private TipoUsuario tipoUsuario;

    public Usuario() {
    }

    public Usuario(String nomeCompleto, String identidade, String email, String senha, TipoUsuario tipoUsuario) {
        this.nomeCompleto = nomeCompleto;
        this.identidade = identidade;
        this.email = email;
        this.senha = senha;
        this.saldo = BigDecimal.ZERO;
        this.tipoUsuario = tipoUsuario;
    }

    public Usuario(String nomeCompleto, String identidade, String email, String senha, BigDecimal saldo, TipoUsuario tipoUsuario) {
        this.nomeCompleto = nomeCompleto;
        this.identidade = identidade;
        this.email = email;
        this.senha = senha;
        this.saldo = saldo;
        this.tipoUsuario = tipoUsuario;
    }

    public Usuario(Long id, String nomeCompleto, String identidade, String email, String senha, TipoUsuario tipoUsuario) {
        this.id = id;
        this.nomeCompleto = nomeCompleto;
        this.identidade = identidade;
        this.email = email;
        this.senha = senha;
        this.saldo = BigDecimal.ZERO;
        this.tipoUsuario = tipoUsuario;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public Long getId() {
        return id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getIdentidade() {
        return identidade;
    }

    public void setIdentidade(String identidade) {
        this.identidade = identidade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
