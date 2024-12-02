/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facade;

import Entidades.Produto;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author felip
 */
@Stateless
public class ProdutoFacade extends AbstractFacade<Produto> {

    @PersistenceContext(unitName = "projetotestPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProdutoFacade() {
        super(Produto.class);
    }

    // Total de produtos cadastrados
    public Long totalProdutosCadastrados() {
        String jpql = "SELECT COUNT(p) FROM Produto p";
        return em.createQuery(jpql, Long.class).getSingleResult();
    }

// Produtos com estoque abaixo de 5
    public List<Produto> listarProdutosEstoqueBaixo() {
        String jpql = "SELECT p FROM Produto p WHERE p.estoque < 5";
        return em.createQuery(jpql, Produto.class).getResultList();
    }

// Quantidade total de estoque
    public Double totalEstoque() {
        String jpql = "SELECT SUM(p.estoque) FROM Produto p";
        return em.createQuery(jpql, Double.class).getSingleResult();
    }

    public List<Produto> listaFiltrar(String filtro, String... atributos) {
        String hql = "from Produto obj where ";
        for (String atributo : atributos) {
            if ("categoria".equals(atributo)) {
                hql += "lower(obj.categoria.catagoria) like :filtro OR ";
            } else {
                hql += "lower(obj." + atributo + ") like :filtro OR ";
            }
        }
        hql = hql.substring(0, hql.length() - 3);  // Remove o último "OR"
        Query q = getEntityManager().createQuery(hql);
        q.setParameter("filtro", "%" + filtro.toLowerCase() + "%");
        return q.getResultList();
    }

    public boolean produtoTemItensVenda(Produto produto) {
        // Exemplo usando JPQL para contar itens de venda associados ao produto
        String jpql = "SELECT COUNT(i) FROM ItensVenda i WHERE i.produto = :produto";
        Long count = em.createQuery(jpql, Long.class)
                .setParameter("produto", produto)
                .getSingleResult();
        return count > 0; // Retorna true se há itens associados, false caso contrário
    }

    public boolean produtoTemItensCompra(Produto produto) {
        // Exemplo usando JPQL para contar itens de compra associados ao produto
        String jpql = "SELECT COUNT(i) FROM ItensCompra i WHERE i.produto = :produto";
        Long count = em.createQuery(jpql, Long.class)
                .setParameter("produto", produto)
                .getSingleResult();
        return count > 0; // Retorna true se há itens associados, false caso contrário
    }

    public List<Produto> listarProdutosAtivos() {
        TypedQuery<Produto> query = em.createQuery("SELECT p FROM Produto p WHERE p.ativo = true", Produto.class);
        return query.getResultList();
    }

    public List<Produto> listarProdutosInativos() {
        TypedQuery<Produto> query = em.createQuery("SELECT p FROM Produto p WHERE p.ativo = false", Produto.class);
        return query.getResultList();
    }

    public List<Produto> listaFiltrada(String filtro) {
        String jpql = "SELECT p FROM Produto p WHERE LOWER(p.categoria.categoria) LIKE :filtro";
        return em.createQuery(jpql, Produto.class)
                .setParameter("filtro", "%" + filtro.toLowerCase() + "%")
                .getResultList();
    }


}
