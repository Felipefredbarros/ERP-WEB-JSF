/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facade;

import Entidades.Compra;
import Entidades.ItensCompra;
import Entidades.Produto;
import Entidades.Venda;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author felip
 */
@Stateless
public class CompraFacade extends AbstractFacade<Compra> {

    @PersistenceContext(unitName = "projetotestPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    // Total de compras cadastrados
    public Long totalComprasCadastradas() {
        String jpql = "SELECT COUNT(c) FROM Compra c";
        return em.createQuery(jpql, Long.class).getSingleResult();
    }

    // Valor total somado de todas as compras
    public Double valorComprasVendido() {
        String jpql = "SELECT SUM(c.valorTotal) FROM Compra c";
        Double result = em.createQuery(jpql, Double.class).getSingleResult();
        return result != null ? result : 0.0;  // Se o resultado for null, retorna 0.0
    }

    public void salvarCompra(Compra entity, Boolean edit) {
        if (edit == false){
            for (ItensCompra ic : entity.getItensCompra()) {
                Produto p = ic.getProduto();
                p.setEstoque(p.getEstoque() + ic.getQuantidade());
                p.setValorUnitarioVenda((ic.getValorUnitario() * 0.8) + ic.getValorUnitario());
                p.setValorUnitarioCompra(ic.getValorUnitario());
                getEntityManager().merge(p);
                ic.setDesc(p.getTexto());

            }
        }
        getEntityManager().merge(entity);

    }

    @Override
    public void remover(Compra entity) {
        for (ItensCompra ic : entity.getItensCompra()) {
            Produto p = ic.getProduto();
            p.setEstoque(p.getEstoque() - ic.getQuantidade());
            getEntityManager().merge(p);
        }

        super.remover(entity);
    }

    public Compra findWithItens(Long id) {
        return em.createQuery("SELECT c FROM Compra c LEFT JOIN FETCH c.itensCompra WHERE c.id = :id", Compra.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public List<Compra> listaTodosComItens() {
        return em.createQuery("SELECT DISTINCT c FROM Compra c LEFT JOIN FETCH c.itensCompra", Compra.class).getResultList();
    }

    public CompraFacade() {
        super(Compra.class);
    }

}
