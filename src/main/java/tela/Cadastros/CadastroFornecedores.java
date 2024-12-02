/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package tela.Cadastros;

import Entidades.Pessoa;
import Entidades.Produto;
import Entidades.TipoPessoa;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Query;
import persistencia.HibernateUtil;
import tela.Relatorios.ListarFornecedores;

/**
 *
 * @author felip
 */
public class CadastroFornecedores extends javax.swing.JFrame {

    private Boolean habilitarEdicao = false;
    private Pessoa fornecedor = new Pessoa();
    private List<Pessoa> listaFornecedor = new ArrayList<>();
    private Query query;
    private JFrame frame;

    public JButton getBtIrLista() {
        return btIrLista;
    }

    public void setBtIrLista(JButton btIrLista) {
        this.btIrLista = btIrLista;
    }

    public CadastroFornecedores(JFrame frame) {
        this.frame = frame;
    }

    public Boolean getHabilitarEdicao() {
        return habilitarEdicao;
    }

    public void setHabilitarEdicao(Boolean habilitarEdicao) {
        this.habilitarEdicao = habilitarEdicao;
    }

    public void limpaCampos() {
        campoNome.setText("");
        campoEmail.setText("");
        campoCpf.setText("");
        campoRegiao.setText("");
        campoEndereco.setText("");
        campoTelefone.setText("");

    }

    public void SetCampos(Pessoa forn) {
        campoNome.setText(forn.getNome());
        campoTelefone.setText(forn.getTelefone());
        campoEndereco.setText(forn.getEndereco());
        campoEmail.setText(forn.getEmail());
        campoCpf.setText(forn.getCpfcnpj());
        campoRegiao.setText(forn.getRegiao());
        this.fornecedor.setId(forn.getId());

        validaCampos("novo");

    }

    public boolean adicionarPessoa(Pessoa fornecedor) {
        if (fornecedor.getNome() == null || fornecedor.getNome().isEmpty()
                || fornecedor.getCpfcnpj() == null || fornecedor.getCpfcnpj().isEmpty()
                || fornecedor.getTelefone() == null || fornecedor.getTelefone().isEmpty()
                || fornecedor.getEndereco() == null || fornecedor.getEndereco().isEmpty()
                || fornecedor.getEmail() == null || fornecedor.getEmail().isEmpty()
                || fornecedor.getTelefone() == null || fornecedor.getTelefone().isEmpty()
                || fornecedor.getRegiao() == null || fornecedor.getRegiao().isEmpty()) {
            JOptionPane.showMessageDialog(null, "TODOS OS CAMPOS PRECISAM SER PREENCHIDOS");
            return false;
        }
        return true;
    }

    public void validaCampos(String operacao) {
        if (operacao.equals("inicio")) {
            btNovo.setEnabled(true);
            btsair.setEnabled(true);
            btSalvar.setEnabled(false);
            btCancelar.setEnabled(false);
            campoNome.setEnabled(false);
            campoCpf.setEnabled(false);
            campoEmail.setEnabled(false);
            campoEndereco.setEnabled(false);
            campoTelefone.setEnabled(false);
            campoRegiao.setEnabled(false);
            campoNome.setEnabled(false);
        } else if (operacao.equals("novo")) {
            btNovo.setEnabled(false);
            btsair.setEnabled(false);
            btSalvar.setEnabled(true);
            btCancelar.setEnabled(true);
            campoNome.setEnabled(false);
            campoCpf.setEnabled(true);
            campoEmail.setEnabled(true);
            campoEndereco.setEnabled(true);
            campoTelefone.setEnabled(true);
            campoRegiao.setEnabled(true);
            campoNome.setEnabled(true);
        } else if (operacao.equals("selecionado")) {
            btNovo.setEnabled(true);
            btSalvar.setEnabled(false);
            btCancelar.setEnabled(true);
            campoNome.setEnabled(false);
            campoCpf.setEnabled(false);
            campoEmail.setEnabled(false);
            campoRegiao.setEnabled(false);
            campoEndereco.setEnabled(false);
            campoTelefone.setEnabled(false);
            campoNome.setEnabled(false);

        }

    }

    public boolean verificaCPFCNPJ(String cpfDigitado, Integer idPessoa) {
        if (habilitarEdicao) {
            query = HibernateUtil.getSession().createQuery("SELECT coalesce(COUNT(*),0) FROM Pessoa WHERE cpfcnpj = :cpfDigitado AND id <> :idPessoa");
            query.setParameter("cpfDigitado", cpfDigitado);
            query.setParameter("idPessoa", idPessoa);
        }
        if (!habilitarEdicao) {
            query = HibernateUtil.getSession().createQuery("SELECT coalesce(COUNT(*),0) FROM Pessoa WHERE cpfcnpj = :cpfDigitado");
            query.setParameter("cpfDigitado", cpfDigitado);
        }

        Long result = (Long) query.uniqueResult();
        if (result > 0L) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean VerificacaoEdit() {
        if (habilitarEdicao) {
            if (verificaCPFCNPJ(fornecedor.getCpfcnpj(), fornecedor.getId())) {
                JOptionPane.showMessageDialog(null, "CPF/CNPJ QUE ESTA TENDO MODIFICAR NA EXISTE");
                return true;
            } else {
                return false;
            }
        } else {
            if (verificaCPFCNPJ(fornecedor.getCpfcnpj(), fornecedor.getId())) {
                JOptionPane.showMessageDialog(null, "CPF/CNPJ JA EXISTE NO BANCO");
                return true;
            } else {
                return false;
            }
        }
    }

    public CadastroFornecedores(java.awt.Frame parent, boolean modal) {
        initComponents();
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel2 = new javax.swing.JLabel();
        btNovo = new javax.swing.JButton();
        btCancelar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        campoNome = new javax.swing.JTextField();
        campoTelefone = new javax.swing.JTextField();
        campoEndereco = new javax.swing.JTextField();
        campoEmail = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        campoCpf = new javax.swing.JTextField();
        btSalvar = new javax.swing.JButton();
        campoRegiao = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        btIrLista = new javax.swing.JButton();
        btsair = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel2.setText("Cadastro de Fornecedores");

        btNovo.setText("NOVO");
        btNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btNovoActionPerformed(evt);
            }
        });

        btCancelar.setText("CANCELAR");
        btCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelarActionPerformed(evt);
            }
        });

        jLabel4.setText("Nome/Razao:");

        jLabel5.setText("Telefone:");

        jLabel6.setText("Endereço:");

        jLabel7.setText("Email:");

        campoTelefone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoTelefoneActionPerformed(evt);
            }
        });

        campoEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoEmailActionPerformed(evt);
            }
        });

        jLabel8.setText("CPF/CNPJ:");

        campoCpf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoCpfActionPerformed(evt);
            }
        });

        btSalvar.setText("SALVAR");
        btSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSalvarActionPerformed(evt);
            }
        });

        jLabel9.setText("Região:");

        btIrLista.setText("LISTA DE FORNECEDORES");
        btIrLista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btIrListaActionPerformed(evt);
            }
        });

        btsair.setText("SAIR");
        btsair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btsairActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btIrLista)
                                .addGap(18, 18, 18)
                                .addComponent(btsair))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(btSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(30, 30, 30)
                                    .addComponent(btCancelar))
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel6)
                                                .addGap(23, 23, 23))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(jLabel7)
                                                .addGap(68, 68, 68)))
                                        .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel5)
                                                .addComponent(jLabel9)
                                                .addComponent(jLabel4))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(campoNome, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(campoRegiao, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(campoEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(campoEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(campoTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel8)
                                    .addGap(42, 42, 42)
                                    .addComponent(campoCpf))))))
                .addGap(0, 11, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoRegiao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(campoTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(campoEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(campoEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoCpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btSalvar)
                    .addComponent(btNovo)
                    .addComponent(btCancelar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btIrLista)
                    .addComponent(btsair))
                .addGap(10, 10, 10))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btNovoActionPerformed
        fornecedor = new Pessoa();
        limpaCampos();
        validaCampos("novo");
    }//GEN-LAST:event_btNovoActionPerformed

    private void btCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelarActionPerformed
        limpaCampos();
        habilitarEdicao = false;
        validaCampos("inicio");
    }//GEN-LAST:event_btCancelarActionPerformed

    private void campoTelefoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoTelefoneActionPerformed

    }//GEN-LAST:event_campoTelefoneActionPerformed

    private void campoEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoEmailActionPerformed

    private void campoCpfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoCpfActionPerformed

    }//GEN-LAST:event_campoCpfActionPerformed

    private void btSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSalvarActionPerformed

        fornecedor.setTelefone(campoTelefone.getText());
        fornecedor.setEndereco(campoEndereco.getText());
        fornecedor.setEmail(campoEmail.getText());
        fornecedor.setTipo(TipoPessoa.FORNECEDOR);
        fornecedor.setCpfcnpj(campoCpf.getText());
        fornecedor.setNome(campoNome.getText());
        fornecedor.setRegiao(campoRegiao.getText());

        if (adicionarPessoa(fornecedor)) {
            if (VerificacaoEdit() == true) {
                return;
            }
            HibernateUtil.beginTransaction();
            HibernateUtil.getSession().merge(fornecedor);
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
            if (habilitarEdicao) {
                JOptionPane.showMessageDialog(null, "EDIÇÃO REALIZADA COM SUCESSO");
                dispose();
                ListarFornecedores frm1 = new ListarFornecedores(frame, true);
                frm1.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "COLABORADOR CADASTRADO COM SUCESSO");
            }
            limpaCampos();

            habilitarEdicao = false;
            fornecedor = new Pessoa();
            validaCampos("inicio");
        }
    }//GEN-LAST:event_btSalvarActionPerformed

    private void btIrListaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btIrListaActionPerformed
        habilitarEdicao = false;

        dispose();
        ListarFornecedores frm = new ListarFornecedores(frame, true);
        frm.setVisible(true);
    }//GEN-LAST:event_btIrListaActionPerformed

    private void btsairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btsairActionPerformed
        dispose();
    }//GEN-LAST:event_btsairActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="fornecedorlapsed" desc=" Look and feel setting code (optional) ">
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
            java.util.logging.Logger.getLogger(CadastroFornecedores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CadastroFornecedores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CadastroFornecedores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CadastroFornecedores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                CadastroFornecedores dialog = new CadastroFornecedores(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btCancelar;
    private javax.swing.JButton btIrLista;
    private javax.swing.JButton btNovo;
    private javax.swing.JButton btSalvar;
    private javax.swing.JButton btsair;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JTextField campoCpf;
    private javax.swing.JTextField campoEmail;
    private javax.swing.JTextField campoEndereco;
    private javax.swing.JTextField campoNome;
    private javax.swing.JTextField campoRegiao;
    private javax.swing.JTextField campoTelefone;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    // End of variables declaration//GEN-END:variables
}
