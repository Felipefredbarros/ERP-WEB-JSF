/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author felip
 */
@Entity
@Table(name = "produto")
public class Produto implements Serializable, ClassePai {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prod_id")
    private Long id;
    @ManyToOne
    @JoinColumn(nullable = true, name = "categoria_id")
    private Categoria categoria;
    @ManyToOne
    @JoinColumn(nullable = true, name = "marca_id")
    private Marca marca;
    @Column(name = "prod_numeracao", nullable = false)
    private String numeracao;
    @Column(name = "prod_cor", nullable = false)
    private String cor;
    @Column(name = "prod_ncm", nullable = false)
    private String ncm;
    @Column(name = "prod_valorUnitarioVenda")
    private Double valorUnitarioVenda;
    @Column(name = "prod_valorUnitarioCompra")
    private Double valorUnitarioCompra = 0d;
    @Column(name = "prod_quantidade", nullable = false)
    private Double estoque = 0d;
    @Column(name = "prod_cont")
    private Double cont;
    @Column(name = "prod_ativo")
    private Boolean ativo = true;


    public Boolean getAtivo() {
        return ativo;
    }
    public String getStatus() {
        if(ativo == true){
            return "Ativo";
        }
        else{
            return "Inativo";
        }
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getNumeracao() {
        return numeracao;
    }

    public void setNumeracao(String numeracao) {
        this.numeracao = numeracao;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getNcm() {
        return ncm;
    }

    public void setNcm(String ncm) {
        this.ncm = ncm;
    }

    public Double getValorUnitarioVenda() {
        return valorUnitarioVenda;
    }

    public void setValorUnitarioVenda(Double valorUnitarioVenda) {
        this.valorUnitarioVenda = valorUnitarioVenda;
    }

    public Double getValorUnitarioCompra() {
        return valorUnitarioCompra;
    }

    public void setValorUnitarioCompra(Double valorUnitarioCompra) {
        this.valorUnitarioCompra = valorUnitarioCompra;
    }

    public Double getEstoque() {
        return estoque;
    }

    public void setEstoque(Double estoque) {
        this.estoque = estoque;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }
    
    
    

    public Double getCont() {
        return cont;
    }

    public void setCont(Double cont) {
        this.cont = cont;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
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
        final Produto other = (Produto) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Tipo " + getCategoria().getCategoria() + " Numeração " + getNumeracao() + " Cor " + getCor();
    }

    public String getTexto() {
        return "Categoria: " + getCategoria().getCategoria()+" "+ getMarca().getMarca() + ", numeração: " + getNumeracao() + ", cor: " + getCor();
    }
}
