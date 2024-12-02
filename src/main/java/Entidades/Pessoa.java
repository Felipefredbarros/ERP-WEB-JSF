/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author felip
 */
@Entity
@Table(name = "pessoa")
public class Pessoa implements Serializable, ClassePai {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pes_id")
    private Long id;
    @Column(name = "pes_nome", nullable = false)
    private String nome;
    @Column(name = "tipoPessoa")
    private String tipoPessoa;
    @Column(name = "pes_telefone", nullable = false)
    private String telefone;
    @Column(name = "pes_endereco", nullable = false)
    private String endereco;
    @Column(name = "pes_email", nullable = false)
    private String email;
    @Column(name = "pes_rg")
    private String rg;
    @Column(name = "pes_cpfcnpj", nullable = false)
    private String cpfcnpj;
    @Column(name = "pes_salario")
    private Double salario;
    @Column(name = "pes_cargo")
    private String cargo;
    @Column(name = "pes_regiao")
    private String regiao;
    @Column(name = "pes_dia_pagamentos")
    @Temporal(TemporalType.TIMESTAMP)
    private Date diaPagamentos;
    @Column(name = "pes_tipo", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoPessoa tipo;
    @Column(name = "pes_ativo")
    private Boolean ativo = true;

    public Boolean getAtivo() {
        return ativo;
    }

    public String getStatus() {
        if (ativo == true) {
            return "Ativo";
        } else {
            return "Inativo";
        }
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String formatarCPFouCNPJ(String cpfcnpj) {
        if (cpfcnpj == null || cpfcnpj.isEmpty()) {
            return null;
        }

        cpfcnpj = cpfcnpj.replaceAll("\\D", "");

        if (cpfcnpj.length() == 11) {
            return cpfcnpj.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
        } else if (cpfcnpj.length() == 14) {
            return cpfcnpj.replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5");
        }

        return cpfcnpj;
    }

    public String formatarRG(String rg) {
        if (rg == null || rg.isEmpty()) {
            return null;
        }

        if (rg.length() == 9) {
            return rg.replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{1})", "$1.$2.$3-$4");
        }

        return rg;
    }

    public String getCpfcnpjFormatado() {
        return formatarCPFouCNPJ(cpfcnpj);

    }

    public String getRgFormatado() {
        if (rg == null) {
            rg = "Nao tens";
        }
        return formatarRG(rg);
    }

    public void limparCPFCNPJ() {
        if (this.cpfcnpj != null && !this.cpfcnpj.isEmpty()) {
            this.cpfcnpj = this.cpfcnpj.replaceAll("[^\\d]", "");
        }
    }

    public void limparRG() {
        if (this.rg != null && !this.rg.isEmpty()) {
            this.rg = this.rg.replaceAll("[.-]", "");
        }
    }

    public void limparDadosPessoais() {
        limparCPFCNPJ();
        limparRG();
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(String tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCpfcnpj() {
        return cpfcnpj;
    }

    public void setCpfcnpj(String cpfcnpj) {
        this.cpfcnpj = cpfcnpj;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getRegiao() {
        return regiao;
    }

    public void setRegiao(String regiao) {
        this.regiao = regiao;
    }

    public Date getDiaPagamentos() {
        return diaPagamentos;
    }

    public void setDiaPagamentos(Date diaPagamentos) {
        this.diaPagamentos = diaPagamentos;
    }

    public TipoPessoa getTipo() {
        return tipo;
    }

    public void setTipo(TipoPessoa tipo) {
        this.tipo = tipo;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pessoa other = (Pessoa) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Pessoa{" + "id=" + id + '}';
    }

}
