package Controladores;

import Converters.ConverterGenerico;
import Entidades.Produto;
import Facade.CategoriaFacade;
import Facade.MarcaFacade;
import Facade.ProdutoFacade;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.primefaces.component.datatable.DataTable;

/**
 *
 * @author felip
 */
@ManagedBean
@SessionScoped
public class ProdutoControle implements Serializable {

    private Produto produto = new Produto();
    @EJB
    private ProdutoFacade produtoFacade;
    @EJB
    private CategoriaFacade categoriaFacade;
    private ConverterGenerico categoriaConverter;
    @ManagedProperty("#{categoriaControle}")
    private CategoriaControle categoriaControle;
    @EJB
    private MarcaFacade marcaFacade;
    private ConverterGenerico marcaConverter;
    @ManagedProperty("#{marcaControle}")
    private MarcaControle marcaControle;

    public ConverterGenerico getCategoriaConverter() {
        if (categoriaConverter == null) {
            categoriaConverter = new ConverterGenerico(categoriaFacade);
        }
        return categoriaConverter;
    }

    public ConverterGenerico getMarcaConverter() {
        if (marcaConverter == null) {
            marcaConverter = new ConverterGenerico(marcaFacade);
        }
        return marcaConverter;
    }

    public void salvar() {
        if (categoriaControle.getListaCategorias() == null || categoriaControle.getListaCategorias().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Cadastre pelo menos uma categoria antes de salvar um produto.", ""));
            return;
        }
        if (marcaControle.getListaMarcas() == null || marcaControle.getListaMarcas().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Cadastre pelo menos uma marca antes de salvar um produto.", ""));
            return;
        }

        produtoFacade.salvar(produto);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Sucesso", "Produto salvo com sucesso!"));

        try {

            FacesContext.getCurrentInstance().getExternalContext().redirect("listaProduto.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }

        produto = new Produto();
    }

    public void novo() {
        produto = new Produto();
    }

    public void excluir(Produto prod) {
        if ((produtoFacade.produtoTemItensVenda(prod) || produtoFacade.produtoTemItensCompra(prod)) && prod.getAtivo() == true) {
            // Se o produto estiver associado, o marcar como inativo
            prod.setAtivo(false); // Marcar o produto como inativo
            produtoFacade.salvar(prod); // Salva as alterações no banco de dados

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Produto inativado", "O produto foi inativado com sucesso e não pode mais ser usado nas vendas/compras."));
            return; // Sai do método após inativar o produto
        }
        if (prod.getAtivo() == false) {
            if (produtoFacade.produtoTemItensVenda(prod) || produtoFacade.produtoTemItensCompra(prod)) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Erro ao Excluir", "Produto inativado, para excluir exclua as vendas/compras relacionadas a ele"));
                return;
            } else {
                produtoFacade.remover(prod);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Sucesso", "Produto inativado excluído com sucesso!"));
                return;
            }
        }

        // Se não estiver associado, remove o produto da base de dados
        produtoFacade.remover(prod);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Sucesso", "Produto excluído com sucesso!"));
    }

    public void editar(Produto prod) {
        this.produto = prod;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public void setCategoriaControle(CategoriaControle categoriaControle) {
        this.categoriaControle = categoriaControle;
    }

    public void setMarcaControle(MarcaControle marcaControle) {
        this.marcaControle = marcaControle;
    }

    public List<Produto> getListaProdutos() {
        return produtoFacade.listarProdutosAtivos();
    }

    public List<Produto> getListaProdutosInativos() {
        return produtoFacade.listarProdutosInativos();
    }

    public void exportarPDF(boolean somenteVisivel) throws DocumentException, IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();

        // Configura o cabeçalho da resposta
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=relatorio_produtos.pdf");

        // Cria o documento PDF
        Document document = new Document(PageSize.A4, 20, 20, 20, 30); // Margens
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        // Adiciona o título
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Font.BOLD, Color.BLUE);
        Paragraph title = new Paragraph("Loja São Judas Tadeu", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20); // Espaçamento após o título
        document.add(title);

        // Adiciona subtítulo
        Font subtitleFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, Color.DARK_GRAY);
        Paragraph subtitle = new Paragraph("Relatório de Produtos", subtitleFont);
        subtitle.setAlignment(Element.ALIGN_CENTER);
        subtitle.setSpacingAfter(10);
        document.add(subtitle);

        // Configura a tabela com cabeçalho estilizado
        PdfPTable table = new PdfPTable(8); // Número de colunas
        table.setWidthPercentage(100); // Largura da tabela
        table.setSpacingBefore(10);

        // Definir largura das colunas
        table.setWidths(new float[]{1.5f, 1f, 1f, 1f, 1f, 1f, 1f, 1f});

        // Fonte do cabeçalho
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Font.BOLD, Color.WHITE);

        // Adiciona o cabeçalho com fundo colorido
        String[] headers = {"Categoria", "Marca", "Numeração", "Cor", "NCM", "Valor de Compra", "Valor de Venda", "Quantidade"};
        for (String header : headers) {
            PdfPCell headerCell = new PdfPCell(new Phrase(header, headerFont));
            headerCell.setBackgroundColor(Color.GRAY);
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCell.setPadding(5);
            table.addCell(headerCell);
        }

        // Verifica se vai exportar somente os produtos visíveis ou todos
        List<Produto> produtos;
        if (somenteVisivel) {
            produtos = getProdutosVisiveisNaTabela();
        } else {
            produtos = produtoFacade.listarProdutosAtivos();
        }

        // Fonte para o conteúdo
        Font contentFont = FontFactory.getFont(FontFactory.HELVETICA, 9);

        // Preencher a tabela com os dados dos produtos
        for (Produto prod : produtos) {
            table.addCell(new PdfPCell(new Phrase(prod.getCategoria().getCategoria(), contentFont)));
            table.addCell(new PdfPCell(new Phrase(prod.getMarca().getMarca(), contentFont)));
            table.addCell(new PdfPCell(new Phrase(prod.getNumeracao().toString(), contentFont)));
            table.addCell(new PdfPCell(new Phrase(prod.getCor(), contentFont)));
            table.addCell(new PdfPCell(new Phrase(prod.getNcm(), contentFont)));
            table.addCell(new PdfPCell(new Phrase(prod.getValorUnitarioCompra().toString(), contentFont)));
            table.addCell(new PdfPCell(new Phrase(prod.getValorUnitarioVenda().toString(), contentFont)));
            table.addCell(new PdfPCell(new Phrase(prod.getEstoque().toString(), contentFont)));
        }

        // Adiciona a tabela ao documento
        document.add(table);
        document.close();

        facesContext.responseComplete();
    }

    // Método para capturar os produtos visíveis na tabela (com paginação e filtros)
    private List<Produto> getProdutosVisiveisNaTabela() {
        DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formRelatorioProdutos:tabelaProdutos");

        // Pegue o valor filtrado diretamente do dataTable
        List<Produto> listaFiltrada = (List<Produto>) dataTable.getFilteredValue();

        // Se não houver filtro, retorna a lista completa do dataTable
        if (listaFiltrada == null) {
            listaFiltrada = (List<Produto>) dataTable.getValue();
        }

        return listaFiltrada;
    }

}
