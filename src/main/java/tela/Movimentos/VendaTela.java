/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package tela.Movimentos;

import Entidades.ItensVenda;
import Entidades.Pessoa;
import Entidades.Produto;
import Entidades.Venda;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import persistencia.HibernateUtil;
import tela.Cadastros.CadastroColaboradores;
import tela.Cadastros.CadastroProdutos;
import tela.Relatorios.ListarVendas;

/**
 *
 * @author felip
 */
public class VendaTela extends javax.swing.JDialog {

    private Produto prod = new Produto();
    private Venda venda = new Venda();
    private ItensVenda itensVenda = new ItensVenda();
    private List<Venda> listaVendas = new ArrayList<>();
    private List<Pessoa> listaFunc = new ArrayList<>();
    private List<Pessoa> listaCliente = new ArrayList<>();
    private List<Produto> listaProdutos = new ArrayList<>();
    private Boolean habilitarEdicao = false;
    private JFrame frame;

    public JButton getBtIrLista() {
        return btIrLista;
    }

    public void setBtIrLista(JButton btIrLista) {
        this.btIrLista = btIrLista;
    }

    public VendaTela(JFrame frame) {
        this.frame = frame;
    }

    public Boolean getHabilitarEdicao() {
        return habilitarEdicao;
    }

    public void setHabilitarEdicao(Boolean habilitarEdicao) {
        this.habilitarEdicao = habilitarEdicao;
    }

    private Boolean verificaPagamento = false;

    public boolean addVerifica(ItensVenda itensVenda) {
        if (itensVenda.getProduto() == null
                || itensVenda.getValorUnitario() == null
                || itensVenda.getQuantidade() == null) {
            JOptionPane.showMessageDialog(null, "TODOS OS CAMPOS PRECISAM SER PREENCHIDOS");
            return true;
        }
        return false;
    }

    public boolean salvarVerifica(Venda ven) {
        if (ven.getItensVenda() == null
                || ven.getDataVenda() == null
                || ven.getCliente() == null
                || ven.getFuncionario() == null
                || ven.getMetodoPagamento() == null) {
            JOptionPane.showMessageDialog(null, "TODOS OS CAMPOS PRECISAM SER PREENCHIDOS");
            return false;
        }
        return true;
    }

    public void SetCampos(Venda ven) {
        dataVenda.setDate(ven.getDataVenda());
        dataVencimento.setDate(ven.getDataVencimento());
        comboCliente.setSelectedItem(ven.getCliente().getNome());
        comboFuncionario.setSelectedItem(ven.getFuncionario().getNome());
        comboPagamento.setSelectedItem(ven.getMetodoPagamento());
        venda.setItensVenda(ven.getItensVenda());
        this.venda.setId(ven.getId());
        validaCampos("selecionado");
    }

    public void validaCampos(String operacao) {
        if (operacao.equals("inicio")) {
            btNovo.setEnabled(true);
            btSalvar.setEnabled(false);
            comboPagamento.setEnabled(false);
            dataVenda.setEnabled(false);
            comboCliente.setEnabled(false);
            dataVencimento.setEnabled(false);
            comboFuncionario.setEnabled(false);
            comboProduto.setEnabled(false);
            preco.setEnabled(false);
            quantidade.setEnabled(false);
            btADD.setEnabled(false);
            btLimpar.setEnabled(false);
            tabelaItens.setEnabled(false);
        } else if (operacao.equals("novo")) {
            btNovo.setEnabled(false);
            btSalvar.setEnabled(true);
            comboPagamento.setEnabled(true);
            dataVenda.setEnabled(true);
            comboFuncionario.setEnabled(true);
            comboCliente.setEnabled(true);
            comboFuncionario.setEnabled(true);
            if (habilitarEdicao == true) {
                comboProduto.setEnabled(false);
                preco.setEnabled(false);
                quantidade.setEnabled(false);
            } else {
                comboProduto.setEnabled(true);
                preco.setEnabled(true);
                quantidade.setEnabled(true);
            }
            btADD.setEnabled(true);
            btLimpar.setEnabled(true);
            tabelaItens.setEnabled(true);

        } else if (operacao.equals("selecionado")) {
            btNovo.setEnabled(true);
            btSalvar.setEnabled(false);
            dataVenda.setEnabled(false);
            comboCliente.setEnabled(false);
            comboProduto.setEnabled(false);
            dataVencimento.setEnabled(false);
            preco.setEnabled(false);
            quantidade.setEnabled(false);
        }

    }

    public void limpaCamposItens() {
        preco.setText("");
        quantidade.setText("");
    }

    public void limpaCampos() {
        dataVenda.setDate(null);
        dataVencimento.setDate(null);
    }

    public void montaComboPagamento() {
        comboPagamento.removeAllItems();
        comboPagamento.addItem("CARTÃO");
        comboPagamento.addItem("A VISTA");
        comboPagamento.addItem("A PRAZO(NOTINHA)");
        comboPagamento.addItem("PIX");
    }

    public void montaComboCliente() {
        comboCliente.removeAllItems();
        listaCliente = HibernateUtil.getSession().createQuery("from Pessoa where pes_tipo = 'CLIENTE'").list();
        for (Pessoa o : listaCliente) {
            comboCliente.addItem(o.getNome());

        }
    }

    public void montaComboFuncionario() {
        comboFuncionario.removeAllItems();
        listaFunc = HibernateUtil.getSession().createQuery("from Pessoa where pes_tipo = 'FUNCIONARIO'").list();
        for (Pessoa o : listaFunc) {
            comboFuncionario.addItem(o.getNome());
        }
    }

    public void montaComboProduto() {
        comboProduto.removeAllItems();
        listaProdutos = HibernateUtil.getSession().createQuery("from Produto").list();
        for (Produto p : listaProdutos) {
            comboProduto.addItem(p.toString());
        }
    }

    public void montaTabelaItens() {

        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Produto");
        modelo.addColumn("Preço");
        modelo.addColumn("Quantidade");
        modelo.addColumn("SubTotal");

        for (ItensVenda e : venda.getItensVenda()) {
            modelo.addRow(new Object[]{e.getProduto().getId(), e.getValorUnitario(), e.getQuantidade(), e.getSubTotal()});
        }
        tabelaItens.setModel(modelo);
    }

    public VendaTela(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        montaComboPagamento();
        montaComboCliente();
        montaComboFuncionario();
        montaComboProduto();
        montaTabelaItens();
        validaCampos("inicio");

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        comboPagamento = new javax.swing.JComboBox<>();
        dataVenda = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        comboCliente = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        comboFuncionario = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaItens = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        comboProduto = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        preco = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        dataVencimento = new com.toedter.calendar.JDateChooser();
        jLabel10 = new javax.swing.JLabel();
        btSalvar = new javax.swing.JButton();
        btNovo = new javax.swing.JButton();
        quantidade = new javax.swing.JTextField();
        btADD = new javax.swing.JButton();
        btLimpar = new javax.swing.JButton();
        btIrLista = new javax.swing.JButton();
        btsair = new javax.swing.JButton();
        btCadastrarFunc = new javax.swing.JButton();
        btCadastroProduto1 = new javax.swing.JButton();
        btCadastrarCliente = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel1.setText("VENDA");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("METODO DE PAGAMENTO:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("DATA DA VENDA:");

        comboPagamento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboPagamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboPagamentoActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("CLIENTE");

        comboCliente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("FUNCIONARIO");

        comboFuncionario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        tabelaItens.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabelaItens.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaItensMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelaItens);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setText("CARINHO DE COMPRAS");

        jLabel7.setText("PRODUTO:");

        comboProduto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboProdutoActionPerformed(evt);
            }
        });

        jLabel8.setText("PREÇO");

        preco.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                precoKeyTyped(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("DATA DA VENCIMENTO:");

        jLabel10.setText("QUANTIDADE");

        btSalvar.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btSalvar.setText("SALVAR");
        btSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSalvarActionPerformed(evt);
            }
        });

        btNovo.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btNovo.setText("NOVO");
        btNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btNovoActionPerformed(evt);
            }
        });

        quantidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quantidadeActionPerformed(evt);
            }
        });
        quantidade.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                quantidadeKeyTyped(evt);
            }
        });

        btADD.setText("ADD");
        btADD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btADDActionPerformed(evt);
            }
        });

        btLimpar.setText("LIMPAR");
        btLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btLimparActionPerformed(evt);
            }
        });

        btIrLista.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btIrLista.setText("LISTA DE VENDAS");
        btIrLista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btIrListaActionPerformed(evt);
            }
        });

        btsair.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btsair.setText("SAIR");
        btsair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btsairActionPerformed(evt);
            }
        });

        btCadastrarFunc.setText("CADASTRAR PRODUTO");
        btCadastrarFunc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCadastrarFuncActionPerformed(evt);
            }
        });

        btCadastroProduto1.setText("CADASTRAR PRODUTO");
        btCadastroProduto1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCadastroProduto1ActionPerformed(evt);
            }
        });

        btCadastrarCliente.setText("CADASTRAR CLIENTE");
        btCadastrarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCadastrarClienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(338, 338, 338)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dataVencimento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(42, 42, 42)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(dataVenda, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                                            .addComponent(comboPagamento, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(comboFuncionario, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(comboCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btCadastrarFunc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btCadastrarCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btIrLista)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btsair, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 819, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(comboProduto, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(preco, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(quantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(513, 513, 513)))
                .addComponent(btADD, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btLimpar)
                .addGap(11, 11, 11))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(718, Short.MAX_VALUE)
                    .addComponent(btCadastroProduto1)
                    .addGap(1, 1, 1)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(comboPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dataVenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dataVencimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(comboCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btCadastrarCliente))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(comboFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btCadastrarFunc))
                .addGap(24, 24, 24)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(comboProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7)
                        .addComponent(jLabel8))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(quantidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btADD)
                            .addComponent(preco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btLimpar))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btsair, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btIrLista, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(0, 0, 0))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(243, Short.MAX_VALUE)
                    .addComponent(btCadastroProduto1)
                    .addGap(232, 232, 232)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSalvarActionPerformed
        venda.setDataVenda(dataVenda.getDate());
        System.out.println("aqqq");
        venda.setMetodoPagamento(comboPagamento.getSelectedItem().toString());
        System.out.println("aq2");
        venda.setCliente(listaCliente.get(comboCliente.getSelectedIndex()));
        System.out.println("aq3");
        venda.setFuncionario(listaFunc.get(comboFuncionario.getSelectedIndex()));
        System.out.println("aq4");
        if (verificaPagamento == true) {
            venda.setDataVencimento(dataVencimento.getDate());
        } else {
            venda.setDataVencimento(null);
        }

        if (salvarVerifica(venda)) {
            HibernateUtil.beginTransaction();
            HibernateUtil.getSession().merge(venda);
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
            if (habilitarEdicao == true) {
                dispose();
                JOptionPane.showMessageDialog(null, "EDIÇÃO REALIZADA COM SUCESSO");
            } else {
                JOptionPane.showMessageDialog(null, "VENDA CADASTRADA COM SUCESSO");
            }

        }

        if (habilitarEdicao == false) {
            for (ItensVenda it : venda.getItensVenda()) {
                Produto p = it.getProduto();
                p.setEstoque(p.getEstoque() - it.getQuantidade());
                p.setCont(p.getCont() + it.getQuantidade());
                HibernateUtil.beginTransaction();
                HibernateUtil.getSession().merge(p);
                HibernateUtil.commitTransaction();
                HibernateUtil.closeSession();
            }
        }
        venda = new Venda();
        limpaCampos();
        habilitarEdicao = false;
        montaTabelaItens();
        validaCampos("inicio");
    }//GEN-LAST:event_btSalvarActionPerformed

    private void quantidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quantidadeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_quantidadeActionPerformed


    private void comboPagamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboPagamentoActionPerformed
        Object selectedItem = comboPagamento.getSelectedItem();
        if (selectedItem != null && selectedItem.toString().equals("A PRAZO(NOTINHA)")) {
            dataVencimento.setEnabled(true);
            verificaPagamento = true;
        } else {
            dataVencimento.setEnabled(false);
            verificaPagamento = false;
        }
    }//GEN-LAST:event_comboPagamentoActionPerformed

    private void btADDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btADDActionPerformed

        itensVenda.setProduto(listaProdutos.get(comboProduto.getSelectedIndex()));

        if (!preco.getText().equals("")) {
            itensVenda.setValorUnitario(Double.parseDouble(preco.getText()));
        }
        if (!quantidade.getText().equals("")) {
            itensVenda.setQuantidade(Double.parseDouble(quantidade.getText()));
        }
        if (addVerifica(itensVenda)) {
            return;
        }
        itensVenda.setVenda(venda);

        Boolean jaExiste = false;
        for (ItensVenda it : venda.getItensVenda()) {
            if (it.getProduto().getId().equals(itensVenda.getProduto().getId())) {
                JOptionPane.showMessageDialog(null, "O produto já foi adicionado na lista!");
                jaExiste = true;
            }
        }

        if (!jaExiste) {
            Double estoque = itensVenda.getProduto().getEstoque();
            estoque = estoque - itensVenda.getQuantidade();
            if (estoque < 0) {
                JOptionPane.showMessageDialog(null, "Estoque insuficiente! Restam apenas " + itensVenda.getProduto().getEstoque() + "unidades.");
            } else {
                venda.getItensVenda().add(itensVenda);
                itensVenda = new ItensVenda();
                montaTabelaItens();
                limpaCamposItens();
            }
        }
    }//GEN-LAST:event_btADDActionPerformed

    private void comboProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboProdutoActionPerformed
        //prod = listaProdutos.get(comboProduto.getSelectedIndex());
        //preco.setText(prod.getValorUnitarioVenda().toString());
    }//GEN-LAST:event_comboProdutoActionPerformed

    private void tabelaItensMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaItensMouseClicked

    }//GEN-LAST:event_tabelaItensMouseClicked

    private void btLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btLimparActionPerformed
        venda.getItensVenda().clear();
        montaTabelaItens();
    }//GEN-LAST:event_btLimparActionPerformed

    private void btNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btNovoActionPerformed
        venda = new Venda();
        limpaCampos();
        validaCampos("novo");
    }//GEN-LAST:event_btNovoActionPerformed

    private void precoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_precoKeyTyped
        char c = evt.getKeyChar();

        if (!Character.isDigit(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_precoKeyTyped

    private void quantidadeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_quantidadeKeyTyped
        char c = evt.getKeyChar();

        if (!Character.isDigit(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_quantidadeKeyTyped

    private void btIrListaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btIrListaActionPerformed
        habilitarEdicao = false;

        dispose();
        ListarVendas frm = new ListarVendas(frame, true);
        frm.setVisible(true);
    }//GEN-LAST:event_btIrListaActionPerformed

    private void btsairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btsairActionPerformed
        dispose();

    }//GEN-LAST:event_btsairActionPerformed

    private void btCadastrarFuncActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCadastrarFuncActionPerformed
        dispose();
        CadastroColaboradores frm = new CadastroColaboradores(frame, true);
        frm.setVisible(true);
    }//GEN-LAST:event_btCadastrarFuncActionPerformed

    private void btCadastroProduto1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCadastroProduto1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btCadastroProduto1ActionPerformed

    private void btCadastrarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCadastrarClienteActionPerformed
        
    }//GEN-LAST:event_btCadastrarClienteActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VendaTela.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VendaTela.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VendaTela.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VendaTela.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                VendaTela dialog = new VendaTela(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btADD;
    private javax.swing.JButton btCadastrarCliente;
    private javax.swing.JButton btCadastrarFunc;
    private javax.swing.JButton btCadastroProduto1;
    private javax.swing.JButton btIrLista;
    private javax.swing.JButton btLimpar;
    private javax.swing.JButton btNovo;
    private javax.swing.JButton btSalvar;
    private javax.swing.JButton btsair;
    private javax.swing.JComboBox<String> comboCliente;
    private javax.swing.JComboBox<String> comboFuncionario;
    private javax.swing.JComboBox<String> comboPagamento;
    private javax.swing.JComboBox<String> comboProduto;
    private com.toedter.calendar.JDateChooser dataVencimento;
    private com.toedter.calendar.JDateChooser dataVenda;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField preco;
    private javax.swing.JTextField quantidade;
    private javax.swing.JTable tabelaItens;
    // End of variables declaration//GEN-END:variables
}
