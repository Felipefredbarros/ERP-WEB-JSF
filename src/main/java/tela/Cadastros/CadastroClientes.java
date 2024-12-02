/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package tela.Cadastros;

import Entidades.Pessoa;
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
import tela.Relatorios.ListarClientes;
import util.ColaboradoresConsultas;

/**
 *
 * @author felip
 */
public class CadastroClientes extends javax.swing.JDialog {

    private Boolean habilitarEdicao = false;
    private Pessoa cli = new Pessoa();
    private List<Pessoa> listaClientes = new ArrayList<>();
    private Query query;
    private JFrame frame;

    public JButton getBtIrLista() {
        return btIrLista;
    }

    public void setBtIrLista(JButton btIrLista) {
        this.btIrLista = btIrLista;
    }

    public CadastroClientes(JFrame frame) {
        this.frame = frame;
    }

    public Boolean getHabilitarEdicao() {
        return habilitarEdicao;
    }

    public void setHabilitarEdicao(Boolean habilitarEdicao) {
        this.habilitarEdicao = habilitarEdicao;
    }

    public Pessoa getCli() {
        return cli;
    }

    public void setCli(Pessoa cli) {
        this.cli = cli;
    }

    public void limpaCampos() {
        campoNome.setText("");
        campoEmail.setText("");
        campoCpf.setText("");
        campoEndereco.setText("");
        campoRG.setText("");
        campoTelefone.setText("");
        checkFisica.setSelected(false);
        checkJuridica.setSelected(false);
    }

    public boolean adicionarPessoa(Pessoa cli) {
        if (cli.getNome() == null || cli.getNome().isEmpty()
                || cli.getCpfcnpj() == null || cli.getCpfcnpj().isEmpty()
                || cli.getTelefone() == null || cli.getTelefone().isEmpty()
                || cli.getEndereco() == null || cli.getEndereco().isEmpty()
                || cli.getEmail() == null || cli.getEmail().isEmpty()
                || cli.getTelefone() == null || cli.getTelefone().isEmpty()
                || cli.getRg() == null || cli.getRg().isEmpty()) {
            JOptionPane.showMessageDialog(null, "TODOS OS CAMPOS PRECISAM SER PREENCHIDOS");
            return false;
        }
        return true;
    }

    public boolean verificaRG(String rgDigitado, Integer idPessoa) {
        if (habilitarEdicao) {

            query = HibernateUtil.getSession().createQuery("SELECT coalesce(COUNT(*),0) FROM Pessoa "
                    + "                         WHERE rg = :rgDigitado "
                    + "                             AND id <> :idPessoa");
            query.setParameter("rgDigitado", rgDigitado);

            query.setParameter("idPessoa", idPessoa);

        } else {
            query = HibernateUtil.getSession().createQuery("SELECT coalesce(COUNT(*),0) FROM Pessoa WHERE rg = :rgDigitado");
            query.setParameter("rgDigitado", rgDigitado);
        }
        System.out.println("aquiiii222222");

        Long result = (Long) query.uniqueResult();

        if (result > 0L) {
            return true;
        } else {
            return false;
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
            if (verificaCPFCNPJ(cli.getCpfcnpj(), cli.getId()) || verificaRG(cli.getRg(), cli.getId())) {
                JOptionPane.showMessageDialog(null, "CPFCNPJ/RG QUE ESTA TENDO MODIFICAR NA EXISTE");
                return true;
            } else {
                return false;
            }
        } else {
            if (verificaCPFCNPJ(cli.getCpfcnpj(), cli.getId()) || verificaRG(cli.getRg(), cli.getId())) {
                JOptionPane.showMessageDialog(null, "CPFCNPJ/RG JA EXISTE NO BANCO");
                return true;
            } else {
                return false;
            }
        }
    }

    public void SetCampos(Pessoa cli) {
        campoNome.setText(cli.getNome());
        campoCpf.setText(cli.getCpfcnpj());
        campoEmail.setText(cli.getEmail());
        campoEndereco.setText(cli.getEndereco());
        campoRG.setText(cli.getRg());
        campoTelefone.setText(cli.getTelefone());
        this.cli.setId(cli.getId());

        System.out.println(cli.getId() + "aqui1");
        validaCampos("novo");
        System.out.println(cli.getId() + "aqui2");

    }

    public void CPFouCPNJ(Pessoa cli) {
        if (cli.getCpfcnpj().length() <= 15) {
            checkFisica.setSelected(true);
            checkFisica.setEnabled(false);
            checkJuridica.setEnabled(false);
            campoNome.setEnabled(true);
            campoRG.setEnabled(true);
        } else {
            checkJuridica.setSelected(true);
            checkFisica.setEnabled(false);
            checkJuridica.setEnabled(false);
        }
    }

    public void validaCampos(String operacao) {
        if (operacao.equals("inicio")) {
            btNovo.setEnabled(true);
            btSalvar.setEnabled(false);
            btCancelar.setEnabled(false);
            campoNome.setEnabled(false);
            campoCpf.setEnabled(false);
            campoEmail.setEnabled(false);
            campoEndereco.setEnabled(false);
            campoRG.setEnabled(false);
            campoTelefone.setEnabled(false);
            checkFisica.setEnabled(false);
            checkJuridica.setEnabled(false);
        } else if (operacao.equals("novo")) {
            btNovo.setEnabled(false);
            btSalvar.setEnabled(true);
            btCancelar.setEnabled(true);
            campoNome.setEnabled(true);
            campoCpf.setEnabled(true);
            campoEmail.setEnabled(true);
            campoEndereco.setEnabled(true);
            campoRG.setEnabled(false);
            campoTelefone.setEnabled(true);
            checkFisica.setEnabled(true);
            checkJuridica.setEnabled(true);
        }
    }

    /**
     * Creates new form CadastroClientes
     */
    public CadastroClientes(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
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
        btNovo = new javax.swing.JButton();
        btCancelar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        campoNome = new javax.swing.JTextField();
        campoTelefone = new javax.swing.JTextField();
        campoEndereco = new javax.swing.JTextField();
        campoEmail = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        campoCpf = new javax.swing.JTextField();
        btSalvar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        checkFisica = new javax.swing.JRadioButton();
        checkJuridica = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        campoRG = new javax.swing.JTextField();
        btIrLista = new javax.swing.JButton();
        btsair = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

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

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel2.setText("Cadastro de Clientes");

        jLabel4.setText("Nome/Razao:");

        jLabel5.setText("Telefone:");

        jLabel6.setText("Endereço:");

        checkFisica.setText("PessoaFisica");
        checkFisica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkFisicaActionPerformed(evt);
            }
        });

        checkJuridica.setText("PessoaJuridica");
        checkJuridica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkJuridicaActionPerformed(evt);
            }
        });

        jLabel3.setText("RG:");

        campoRG.setEnabled(false);

        btIrLista.setText("LISTA DE CLIENTES");
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
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(checkFisica)
                        .addGap(18, 18, 18)
                        .addComponent(checkJuridica))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(92, 92, 92)
                        .addComponent(campoRG, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(campoCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(campoEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(campoTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(campoEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btCancelar))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btIrLista)
                        .addGap(18, 18, 18)
                        .addComponent(btsair)))
                .addContainerGap(43, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(campoNome, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(campoEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(campoCpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkFisica)
                    .addComponent(checkJuridica))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(campoRG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btSalvar)
                    .addComponent(btNovo)
                    .addComponent(btCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btIrLista)
                    .addComponent(btsair))
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btNovoActionPerformed
        cli = new Pessoa();
        limpaCampos();
        validaCampos("novo");
    }//GEN-LAST:event_btNovoActionPerformed

    private void btCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelarActionPerformed
        limpaCampos();
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
        cli.setTelefone(campoTelefone.getText());
        cli.setEndereco(campoEndereco.getText());
        cli.setEmail(campoEmail.getText());
        cli.setTipo(TipoPessoa.CLIENTE);
        cli.setCpfcnpj(campoCpf.getText());
        cli.setNome(campoNome.getText());

        if (checkFisica.isSelected()) {
            cli.setRg(campoRG.getText());
        }
        if (checkJuridica.isSelected()) {
            cli.setRg("NULL");

        }

        if (adicionarPessoa(cli)) {
            if (VerificacaoEdit() == true) {
                return;
            }
            HibernateUtil.beginTransaction();
            if (checkJuridica.isSelected()) {
                cli.setRg("null" + cli.getId());
            }
            HibernateUtil.getSession().merge(cli);

            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
            if (habilitarEdicao) {
                JOptionPane.showMessageDialog(null, "EDIÇÃO REALIZADA COM SUCESSO");
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "COLABORADOR CADASTRADO COM SUCESSO");
            }
            limpaCampos();
            checkFisica.setSelected(false);
            checkJuridica.setSelected(false);
            habilitarEdicao = false;
            cli = new Pessoa();
            validaCampos("inicio");
        }
    }//GEN-LAST:event_btSalvarActionPerformed

    private void checkFisicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkFisicaActionPerformed

        if (checkFisica.isSelected()) {
            checkJuridica.setEnabled(false);
            campoRG.setEnabled(true);
            btSalvar.setEnabled(true);
        } else {
            checkJuridica.setEnabled(true);
            campoRG.setEnabled(false);
            btSalvar.setEnabled(false);

        }
    }//GEN-LAST:event_checkFisicaActionPerformed

    private void checkJuridicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkJuridicaActionPerformed
        if (checkJuridica.isSelected()) {
            checkFisica.setEnabled(false);
            btSalvar.setEnabled(true);

        } else {
            checkFisica.setEnabled(true);
            btSalvar.setEnabled(false);

        }
    }//GEN-LAST:event_checkJuridicaActionPerformed

    private void btIrListaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btIrListaActionPerformed
        habilitarEdicao = false;

        dispose();
        ListarClientes frm = new ListarClientes(frame, true);
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
        //<editor-fold defaultstate="clilapsed" desc=" Look and feel setting code (optional) ">
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
            java.util.logging.Logger.getLogger(CadastroClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CadastroClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CadastroClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CadastroClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                CadastroClientes dialog = new CadastroClientes(new javax.swing.JFrame(), true);
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
    private javax.swing.JTextField campoRG;
    private javax.swing.JTextField campoTelefone;
    private javax.swing.JRadioButton checkFisica;
    private javax.swing.JRadioButton checkJuridica;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    // End of variables declaration//GEN-END:variables
}
