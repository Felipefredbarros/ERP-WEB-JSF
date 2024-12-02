/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Facade;

import Entidades.Pessoa;
import Entidades.TipoPessoa;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author felip
 */
@Stateless
public class PessoaFacade extends AbstractFacade<Pessoa> {

    @PersistenceContext(unitName = "projetotestPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PessoaFacade() {
        super(Pessoa.class);
    }

//ATIVOS
    public List<Pessoa> listaPorTipoAtivo(TipoPessoa tipo) {
        Query q = getEntityManager().createQuery("from Pessoa as p where p.tipo = :tipo and p.ativo = true order by p.id desc");
        q.setParameter("tipo", tipo);
        return q.getResultList();
    }

    public List<Pessoa> listaClienteAtivo() {
        return listaPorTipoAtivo(TipoPessoa.CLIENTE);
    }

    public List<Pessoa> listaFornecedorAtivo() {
        return listaPorTipoAtivo(TipoPessoa.FORNECEDOR);
    }

    public List<Pessoa> listaFuncionarioAtivo() {
        return listaPorTipoAtivo(TipoPessoa.FUNCIONARIO);
    }

//INATIVOS
    public List<Pessoa> listaPorTipoInativo(TipoPessoa tipo) {
        Query q = getEntityManager().createQuery("from Pessoa as p where p.tipo = :tipo and p.ativo = false order by p.id desc");
        q.setParameter("tipo", tipo);
        return q.getResultList();
    }

    public List<Pessoa> listaClienteInativo() {
        return listaPorTipoInativo(TipoPessoa.CLIENTE);
    }

    public List<Pessoa> listaFornecedorInativo() {
        return listaPorTipoInativo(TipoPessoa.FORNECEDOR);
    }

    public List<Pessoa> listaFuncionarioInativo() {
        return listaPorTipoInativo(TipoPessoa.FUNCIONARIO);
    }

    public boolean pessoaTemVendas(Pessoa pessoa) {
        if (pessoa == null || pessoa.getId() == null) {
            return false;
        }

        String jpql;
        if (pessoa.getTipo() == TipoPessoa.CLIENTE) {
            jpql = "SELECT COUNT(v) FROM Venda v WHERE v.cliente.id = :pessoaId"; // Supondo que a chave estrangeira do cliente seja 'cliente.id'
        } else if (pessoa.getTipo() == TipoPessoa.FUNCIONARIO) {
            jpql = "SELECT COUNT(v) FROM Venda v WHERE v.funcionario.id = :pessoaId"; // Supondo que a chave estrangeira do funcionário seja 'funcionario.id'
        } else if (pessoa.getTipo() == TipoPessoa.FORNECEDOR) {
            jpql = "SELECT COUNT(v) FROM Compra v WHERE v.fornecedor.id = :pessoaId"; // Supondo que a chave estrangeira do funcionário seja 'funcionario.id'
        } else {
            return false; // Retorna false para tipos que não são cliente ou funcionário ou fornecedor
        }

        Query query = em.createQuery(jpql);
        query.setParameter("pessoaId", pessoa.getId());
        Long count = (Long) query.getSingleResult();
        return count > 0;
    }

    public List<Pessoa> listaFornecedoresFisicos() {
        List<Pessoa> fornecedores = listaFornecedorAtivo();
        List<Pessoa> fornecedoresFisicos = new ArrayList<>();

        for (Pessoa fornecedor : fornecedores) {
            if ("FISICA".equals(fornecedor.getTipoPessoa())) {
                fornecedoresFisicos.add(fornecedor);
            }
        }
        return fornecedoresFisicos;
    }

    public List<Pessoa> listaFornecedoresJuridicos() {
        List<Pessoa> fornecedores = listaFornecedorAtivo();
        List<Pessoa> fornecedoresJuridicos = new ArrayList<>();

        for (Pessoa fornecedor : fornecedores) {
            if ("JURIDICA".equals(fornecedor.getTipoPessoa())) {
                fornecedoresJuridicos.add(fornecedor);
            }
        }
        return fornecedoresJuridicos;
    }

    public List<Pessoa> listaFiltrandoPorTipo(TipoPessoa tipo, String filtro, String... atributos) {
        String hql = "from Pessoa as p where p.tipo = :tipo AND p.ativo = true AND (";
        for (String atributo : atributos) {
            hql += "lower(p." + atributo + ") like :filtro OR ";
        }
        hql = hql.substring(0, hql.length() - 4) + ")"; // Remove o último " OR "
        Query q = getEntityManager().createQuery(hql);
        q.setParameter("tipo", tipo);
        q.setParameter("filtro", "%" + filtro.toLowerCase() + "%");
        return q.getResultList();
    }

    public List<Pessoa> listaClienteFiltrando(String filtro, String... atributos) {
        return listaFiltrandoPorTipo(TipoPessoa.CLIENTE, filtro, atributos);
    }

    public List<Pessoa> listaFornecedorFiltrando(String filtro, String... atributos) {
        return listaFiltrandoPorTipo(TipoPessoa.FORNECEDOR, filtro, atributos);
    }

    public List<Pessoa> listaFuncionarioFiltrando(String filtro, String... atributos) {
        return listaFiltrandoPorTipo(TipoPessoa.FUNCIONARIO, filtro, atributos);
    }

}
