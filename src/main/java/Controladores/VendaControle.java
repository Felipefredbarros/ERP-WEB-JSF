package Controladores;

import Converters.ConverterGenerico;
import Entidades.Compra;
import Entidades.ItensCompra;
import Entidades.ItensVenda;
import Entidades.MetodoPagamento;
import Entidades.Pessoa;
import Entidades.PlanoPagamento;
import Entidades.Produto;
import Entidades.Venda;
import Facade.PessoaFacade;
import Facade.ProdutoFacade;
import Facade.VendaFacade;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import java.awt.Color;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

@ManagedBean
@SessionScoped
public class VendaControle implements Serializable {

    private Venda venda = new Venda();
    private ItensVenda itensVenda = new ItensVenda();
    private Date dataInicio;
    private Date dataFim;
    private List<Venda> listaVendasFiltradas = new ArrayList<>();
    private Boolean edit = false;

    @EJB
    private VendaFacade vendaFacade;

    @EJB
    private PessoaFacade pessoaFacade;

    @EJB
    private ProdutoFacade produtoFacade;

    private ConverterGenerico pessoaConverter;
    private ConverterGenerico produtoConverter;
    private List<MetodoPagamento> metodosPagamentoFiltrados;
    private List<PlanoPagamento> planosPagamentos;

    @ManagedProperty("#{produtoControle}")
    private ProdutoControle produtoControle;

    // Construtor
    public VendaControle() {
        this.planosPagamentos = PlanoPagamento.getPlanosPagamento();
        this.metodosPagamentoFiltrados = MetodoPagamento.getMetodosPagamentoAVista();
    }

    // Métodos de filtro
    public void filtrarPorPeriodo() {
        List<Venda> todasVendas = vendaFacade.listaTodosComItens();
        listaVendasFiltradas = new ArrayList<>();

        for (Venda venda : todasVendas) {
            if ((dataInicio == null || !venda.getDataVenda().before(dataInicio))
                    && (dataFim == null || !venda.getDataVenda().after(dataFim))) {
                listaVendasFiltradas.add(venda);
            }
        }
    }

    public void removerFiltro() {
        dataInicio = null;
        dataFim = null;
        listaVendasFiltradas = vendaFacade.listaTodosComItens();
    }

    public void atualizarPreco() {
        Produto produtoSelecionado = itensVenda.getProduto();
        if (produtoSelecionado != null) {
            itensVenda.setValorUnitario(produtoSelecionado.getValorUnitarioVenda());
        }
    }

    public void editar(Venda ven) {
        edit = true;
        this.venda = vendaFacade.findWithItens(ven.getId());
    }

    public void excluir(Venda ven) {
        vendaFacade.remover(ven);
    }

    public void novo() {
        edit = false;
        venda = new Venda();
        itensVenda = new ItensVenda();
    }

    public List<Venda> getListaVendasFiltradas() {
        return listaVendasFiltradas != null && !listaVendasFiltradas.isEmpty()
                ? listaVendasFiltradas
                : vendaFacade.listaTodosComItens();
    }

    // Atualização de métodos de pagamento
    public void atualizarMetodosPagamento() {
        if (venda.getPlanoPagamento() == PlanoPagamento.A_VISTA) {
            this.metodosPagamentoFiltrados = MetodoPagamento.getMetodosPagamentoAVista();
        } else if (venda.getPlanoPagamento() == PlanoPagamento.PARCELADO_EM_2X
                || venda.getPlanoPagamento() == PlanoPagamento.PARCELADO_EM_3X) {
            this.metodosPagamentoFiltrados = MetodoPagamento.getMetodosPagamentoNaoAVista();
        }
    }

    // Métodos para manipulação de itens da venda
    public void adicionarItem() {
        if (produtoControle.getListaProdutos() == null || produtoControle.getListaProdutos().isEmpty()
                || itensVenda.getQuantidade() == null || itensVenda.getValorUnitario() == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Preencha os Campos para Adicionar um Item"));
            return;
        }

        Double estoque = itensVenda.getProduto().getEstoque();
        ItensVenda itemTemp = null;

        for (ItensVenda it : venda.getItensVenda()) {
            if (it.getProduto().getId().equals(itensVenda.getProduto().getId())) {
                itemTemp = it;
                estoque -= it.getQuantidade();
            }
        }

        if (estoque < itensVenda.getQuantidade()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Estoque Insuficiente!", "Restam apenas: " + estoque));
        } else {
            if (itemTemp == null) {
                itensVenda.setVenda(venda);
                venda.getItensVenda().add(itensVenda);
            } else {
                if (!Objects.equals(itensVenda.getValorUnitario(), itemTemp.getValorUnitario())) {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "O valor do produto precisa ser o mesmo do já cadastrado"));
                    return;
                }
                itemTemp.setQuantidade(itemTemp.getQuantidade() + itensVenda.getQuantidade());
            }
            itensVenda = new ItensVenda();
        }
    }

    public void removerItem(ItensVenda item) {
        venda.getItensVenda().remove(item);
        venda.setValorTotal(venda.getValorTotal() - item.getSubTotal());
        itensVenda = new ItensVenda();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Item removido com sucesso!"));
    }

    // Salvar e exportar PDF
    public void salvar() {
        StringBuilder mensagemErro = new StringBuilder("Preencha os Campos: ");
        boolean hasError = false;

        if (venda.getCliente() == null) {
            mensagemErro.append("Cliente, ");
            hasError = true;
        }
        if (venda.getFuncionario() == null) {
            mensagemErro.append("Funcionário, ");
            hasError = true;
        }
        if (venda.getDataVenda() == null) {
            mensagemErro.append("Data da Venda, ");
            hasError = true;
        }

        if (hasError) {
            mensagemErro.setLength(mensagemErro.length() - 2);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", mensagemErro.toString()));
            return;
        }
        vendaFacade.salvarVenda(venda, edit);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Venda salva com sucesso!"));
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("listaVenda.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        edit = false;
        venda = new Venda();
    }

    public void exportarPDF() throws DocumentException, IOException {
        List<Venda> vendasParaExportar = getListaVendasFiltradas();

        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=relatorio_vendas.pdf");

        Document document = new Document(PageSize.A4, 20, 20, 20, 30);
        PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());

        try {
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Font.BOLD, new Color(0, 0, 128));
            Paragraph title = new Paragraph("Relatório de Vendas - Loja São Judas Tadeu", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Font.BOLD, Color.BLACK);
            Font contentFont = FontFactory.getFont(FontFactory.HELVETICA, 10);

            int vendaCounter = 1;
            for (Venda ven : vendasParaExportar) {
                Paragraph vendaHeader = new Paragraph("Detalhes da Venda " + vendaCounter, headerFont);
                vendaHeader.setSpacingAfter(10);
                document.add(vendaHeader);

                PdfPTable vendaTable = new PdfPTable(2);
                vendaTable.setWidthPercentage(100);
                vendaTable.setSpacingBefore(5);
                vendaTable.setSpacingAfter(10);
                vendaTable.addCell(new PdfPCell(new Phrase("Data da Venda:", headerFont)));
                vendaTable.addCell(new PdfPCell(new Phrase(ven.getDataVenda().toString(), contentFont)));
                vendaTable.addCell(new PdfPCell(new Phrase("Cliente:", headerFont)));
                vendaTable.addCell(new PdfPCell(new Phrase(ven.getCliente().getNome(), contentFont)));
                vendaTable.addCell(new PdfPCell(new Phrase("Funcionário:", headerFont)));
                vendaTable.addCell(new PdfPCell(new Phrase(ven.getFuncionario().getNome(), contentFont)));
                vendaTable.addCell(new PdfPCell(new Phrase("Método de Pagamento:", headerFont)));
                vendaTable.addCell(new PdfPCell(new Phrase(ven.getMetodoPagamento().toString(), contentFont)));
                vendaTable.addCell(new PdfPCell(new Phrase("Valor Total:", headerFont)));
                vendaTable.addCell(new PdfPCell(new Phrase(ven.getValorTotal().toString(), contentFont)));
                document.add(vendaTable);

                PdfPTable itemTable = new PdfPTable(4);
                itemTable.setWidthPercentage(100);
                itemTable.setWidths(new float[]{2f, 1f, 1f, 1f});
                itemTable.setSpacingBefore(10);

                String[] itemHeaders = {"Produto", "Quantidade", "Valor Unitário", "Subtotal"};
                for (String itemHeader : itemHeaders) {
                    PdfPCell itemHeaderCell = new PdfPCell(new Phrase(itemHeader, headerFont));
                    itemHeaderCell.setBackgroundColor(new Color(192, 192, 192));
                    itemHeaderCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    itemHeaderCell.setPadding(5);
                    itemTable.addCell(itemHeaderCell);
                }

                for (ItensVenda item : ven.getItensVenda()) {
                    itemTable.addCell(new PdfPCell(new Phrase(item.getProduto().getTexto(), contentFont)));
                    itemTable.addCell(new PdfPCell(new Phrase(item.getQuantidade().toString(), contentFont)));
                    itemTable.addCell(new PdfPCell(new Phrase(item.getValorUnitario().toString(), contentFont)));
                    itemTable.addCell(new PdfPCell(new Phrase(item.getSubTotal().toString(), contentFont)));
                }

                document.add(itemTable);

                Paragraph separator = new Paragraph(" ");
                separator.setSpacingAfter(20);
                document.add(separator);

                vendaCounter++;
            }
        } finally {
            document.close();
            writer.flush();
            writer.close();
            facesContext.responseComplete();
        }
    }

    // Getters e Setters adicionais
    public List<Venda> getListaVendas() {
        return vendaFacade.listaTodos();
    }

    public List<Produto> getListaProdutos() {
        return produtoFacade.listarProdutosAtivos();
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public ItensVenda getItensVenda() {
        return itensVenda;
    }

    public void setItensVenda(ItensVenda itensVenda) {
        this.itensVenda = itensVenda;
    }

    public ProdutoControle getProdutoControle() {
        return produtoControle;
    }

    public void setProdutoControle(ProdutoControle produtoControle) {
        this.produtoControle = produtoControle;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public List<MetodoPagamento> getMetodosPagamentoFiltrados() {
        return metodosPagamentoFiltrados;
    }

    public List<PlanoPagamento> getPlanosPagamentos() {
        return planosPagamentos;
    }

    public List<Pessoa> getListaFuncionariosFiltrando(String filtro) {
        return pessoaFacade.listaFuncionarioFiltrando(filtro, "nome", "cpfcnpj");
    }

    public List<Pessoa> getListaClientesFiltrando(String filtro) {
        return pessoaFacade.listaClienteFiltrando(filtro, "nome", "cpfcnpj");
    }

    public List<Produto> getListaProdutosFiltrando(String filtro) {
        return produtoFacade.listaFiltrar(filtro, "categoria");
    }

    public ConverterGenerico getPessoaConverter() {
        if (pessoaConverter == null) {
            pessoaConverter = new ConverterGenerico(pessoaFacade);
        }
        return pessoaConverter;
    }

    public ConverterGenerico getProdutoConverter() {
        if (produtoConverter == null) {
            produtoConverter = new ConverterGenerico(produtoFacade);
        }
        return produtoConverter;
    }

    public void setProdutoConverter(ConverterGenerico produtoConverter) {
        this.produtoConverter = produtoConverter;
    }

    public void setClienteConverter(ConverterGenerico estadoConverter) {
        this.pessoaConverter = estadoConverter;
    }

    public PessoaFacade getClienteFacade() {
        if (pessoaConverter == null) {
            pessoaConverter = new ConverterGenerico(pessoaFacade);
        }
        return pessoaFacade;
    }

    public void setClienteFacade(PessoaFacade estadoFacade) {
        this.pessoaFacade = estadoFacade;
    }

    public ProdutoFacade getProdutoFacade() {
        if (produtoConverter == null) {
            produtoConverter = new ConverterGenerico(produtoFacade);
        }
        return produtoFacade;
    }

    public VendaFacade getVendaFacade() {
        return vendaFacade;
    }

    public void setVendaFacade(VendaFacade vendaFacade) {
        this.vendaFacade = vendaFacade;
    }

    public Boolean getEdit() {
        return edit;
    }

    public void setEdit(Boolean edit) {
        this.edit = edit;
    }

}
