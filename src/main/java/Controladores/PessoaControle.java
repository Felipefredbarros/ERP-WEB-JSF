/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Converters.ConverterGenerico;
import Entidades.TipoPessoa;
import javax.faces.application.FacesMessage;
import Entidades.Pessoa;
import Facade.PessoaFacade;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 *
 * @author felip
 */
@ManagedBean
@SessionScoped
public class PessoaControle implements Serializable {

    private Pessoa pessoa = new Pessoa();
    @EJB
    private PessoaFacade pessoaFacade;
    private ConverterGenerico pessoaConverter;
    private String tipoPessoa;
    private Boolean edit = false;

    public String getTipoPessoa() {
        if ("JURIDICA".equals(tipoPessoa)) {
            pessoa.setTipoPessoa("JURIDICA");
        } else if ("FISICA".equals(tipoPessoa)) {
            pessoa.setTipoPessoa("FISICA");
        }
        return tipoPessoa;
    }

    public void setTipoPessoa(String tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
        atualizarTipoPessoa();
    }

    public void atualizarTipoPessoa() {
        if ("JURIDICA".equals(tipoPessoa)) {
            pessoa.setRg(null);
        }
        if (edit == false) {
            pessoa = new Pessoa();
        }
    }

    public PessoaFacade getPessoaFacade() {
        return pessoaFacade;
    }

    public void setPessoaFacade(PessoaFacade pessoaFacade) {
        this.pessoaFacade = pessoaFacade;
    }

    public ConverterGenerico getPessoaConverter() {
        return pessoaConverter;
    }

    public void setPessoaConverter(ConverterGenerico pessoaConverter) {
        this.pessoaConverter = pessoaConverter;
    }

    public void salvarCliente() {
        pessoa.setTipo(TipoPessoa.CLIENTE);
        pessoa.setTipoPessoa("FISICA");
        pessoa.limparDadosPessoais();
        pessoaFacade.salvar(pessoa);
        pessoa = new Pessoa();
    }

    public void salvarFunc() {
        pessoa.setTipoPessoa("FISICA");
        pessoa.setTipo(TipoPessoa.FUNCIONARIO);
        pessoa.limparDadosPessoais();
        pessoaFacade.salvar(pessoa);
        pessoa = new Pessoa();
    }

    public void salvarForn() {
        pessoa.setTipo(TipoPessoa.FORNECEDOR);
        pessoa.limparDadosPessoais();
        pessoaFacade.salvar(pessoa);
        pessoa = new Pessoa();
    }

    public void novo() {
        edit = false;
        pessoa = new Pessoa();
    }

    public void excluir(Pessoa pessoa) {
        // Verifica se a pessoa é um cliente ou funcionário
        if (pessoaFacade.pessoaTemVendas(pessoa) && pessoa.getAtivo()) {
            // Se a pessoa estiver associada a alguma venda, marcar como inativa
            pessoa.setAtivo(false); // Marcar a pessoa como inativa
            pessoaFacade.salvar(pessoa); // Salva as alterações no banco de dados

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Pessoa inativada", "A pessoa foi inativada com sucesso e não pode mais ser usada nas vendas/compras."));
            return; // Sai do método após inativar a pessoa
        }

        if (!pessoa.getAtivo()) {
            if (pessoaFacade.pessoaTemVendas(pessoa)) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Erro ao Excluir", "Pessoa inativada, para excluir exclua as vendas/compras relacionadas a ela."));
                return;
            } else {
                pessoaFacade.remover(pessoa);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Sucesso", "Pessoa inativada excluída com sucesso!"));
                return;
            }
        }

        // Se não estiver associado a vendas, remove a pessoa da base de dados
        pessoaFacade.remover(pessoa);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Sucesso", "Pessoa excluída com sucesso!"));
    }

    public void editar(Pessoa pes) {
        edit = true;
        this.pessoa = pes;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public List<Pessoa> getListaClientes() {
        return pessoaFacade.listaClienteAtivo();
    }

    public List<Pessoa> getListaFornecedor() {
        return pessoaFacade.listaFornecedorAtivo();
    }

    public List<Pessoa> getListaFuncionario() {
        return pessoaFacade.listaFuncionarioAtivo();
    }

    public List<Pessoa> getListaClientesInativos() {
        return pessoaFacade.listaClienteInativo();
    }

    public List<Pessoa> getListaFornecedorInativos() {
        return pessoaFacade.listaFornecedorInativo();
    }

    public List<Pessoa> getListaFuncionarioInativos() {
        return pessoaFacade.listaFuncionarioInativo();
    }

    public void exportarRelatorioClientes() throws DocumentException, IOException {
        exportarPDF(pessoaFacade.listaClienteAtivo(), "relatorio_clientes.pdf");
    }

    public void exportarRelatorioFuncionarios() throws DocumentException, IOException {
        exportarPDFFunc(pessoaFacade.listaFuncionarioAtivo(), "relatorio_funcionarios.pdf");
    }

    public void exportarRelatorioFornecedores() throws DocumentException, IOException {
        exportarPDF(pessoaFacade.listaFornecedorAtivo(), "relatorio_fornecedores.pdf");
    }

    private void exportarPDF(List<Pessoa> pessoas, String fileName) throws DocumentException, IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

        Document document = new Document(PageSize.A4, 20, 20, 20, 30);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        // Título do Relatório
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Font.BOLD, Color.BLUE);
        Paragraph title = new Paragraph("Relatório de Clientes - Loja São Judas Tadeu", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);

        // Tabela
        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);

        // Cabeçalhos da Tabela
        String[] headers = {"Nome", "Telefone", "Endereço", "Email", "CPF/CNPJ", "RG", "Tipo", "Status"};
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Font.BOLD, Color.WHITE);
        for (String header : headers) {
            PdfPCell headerCell = new PdfPCell(new Phrase(header, headerFont));
            headerCell.setBackgroundColor(Color.GRAY);
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCell.setPadding(5);
            table.addCell(headerCell);
        }

        // Preenchimento da Tabela
        Font contentFont = FontFactory.getFont(FontFactory.HELVETICA, 9);
        for (Pessoa p : pessoas) {
            table.addCell(new PdfPCell(new Phrase(p.getNome(), contentFont)));
            table.addCell(new PdfPCell(new Phrase(p.getTelefone(), contentFont)));
            table.addCell(new PdfPCell(new Phrase(p.getEndereco(), contentFont)));
            table.addCell(new PdfPCell(new Phrase(p.getEmail(), contentFont)));
            table.addCell(new PdfPCell(new Phrase(p.getCpfcnpjFormatado(), contentFont)));
            table.addCell(new PdfPCell(new Phrase(p.getRg(), contentFont)));
            table.addCell(new PdfPCell(new Phrase(p.getTipoPessoa(), contentFont)));
            table.addCell(new PdfPCell(new Phrase(p.getStatus(), contentFont)));
        }

        document.add(table);
        document.close();
        facesContext.responseComplete();
    }

    private void exportarPDFFunc(List<Pessoa> pessoas, String fileName) throws DocumentException, IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

        Document document = new Document(PageSize.A4, 20, 20, 20, 30);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        // Título do Relatório
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Font.BOLD, Color.BLUE);
        Paragraph title = new Paragraph("Relatório de Funcionários - Loja São Judas Tadeu", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);

        // Formatação de Data
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM");

        // Tabela
        PdfPTable table = new PdfPTable(11);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);

        // Cabeçalhos da Tabela
        String[] headers = {"Nome", "Telefone", "Endereço", "Email", "Salário", "Cargo", "Dia Pagamento", "CPF", "RG", "Tipo", "Status"};
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Font.BOLD, Color.WHITE);
        for (String header : headers) {
            PdfPCell headerCell = new PdfPCell(new Phrase(header, headerFont));
            headerCell.setBackgroundColor(Color.GRAY);
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCell.setPadding(5);
            table.addCell(headerCell);
        }

        // Preenchimento da Tabela
        Font contentFont = FontFactory.getFont(FontFactory.HELVETICA, 9);
        for (Pessoa p : pessoas) {
            table.addCell(new PdfPCell(new Phrase(p.getNome(), contentFont)));
            table.addCell(new PdfPCell(new Phrase(p.getTelefone(), contentFont)));
            table.addCell(new PdfPCell(new Phrase(p.getEndereco(), contentFont)));
            table.addCell(new PdfPCell(new Phrase(p.getEmail(), contentFont)));
            table.addCell(new PdfPCell(new Phrase(p.getSalario() != null ? p.getSalario().toString() : "N/A", contentFont)));
            table.addCell(new PdfPCell(new Phrase(p.getCargo(), contentFont)));
            String dataFormatada = (p.getDiaPagamentos() != null) ? dateFormatter.format(p.getDiaPagamentos()) : "N/A";
            table.addCell(new PdfPCell(new Phrase(dataFormatada, contentFont)));
            table.addCell(new PdfPCell(new Phrase(p.getCpfcnpjFormatado(), contentFont)));
            table.addCell(new PdfPCell(new Phrase(p.getRg(), contentFont)));
            table.addCell(new PdfPCell(new Phrase(p.getTipoPessoa(), contentFont)));
            table.addCell(new PdfPCell(new Phrase(p.getStatus(), contentFont)));
        }

        document.add(table);
        document.close();
        facesContext.responseComplete();
    }

}
