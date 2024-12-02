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
import org.hibernate.Query;
import persistencia.HibernateUtil;
import tela.Relatorios.ListarColaboradores;

/**
 *
 * @author felip
 */
public class CadastroColaboradores extends javax.swing.JDialog {

    private Boolean habilitarEdicao = false;
    private Pessoa col = new Pessoa();
    private List<Pessoa> listaColaboradores = new ArrayList<>();
    private Query query;
    private JFrame frame;

    public JButton getBtIrLista() {
        return btIrLista;
    }

    public void setBtIrLista(JButton btIrLista) {
        this.btIrLista = btIrLista;
    }

    public CadastroColaboradores(JFrame frame) {
        this.frame = frame;
    }

    public void setHabilitarEdicao(Boolean habilitarEdicao) {
        this.habilitarEdicao = habilitarEdicao;
    }

    public Pessoa getCol() {
        return col;
    }

    public void setCol(Pessoa col) {
        this.col = col;
    }

    public void limpaCampos() {
        campoNome.setText("");
        campoEmail.setText("");
        campoCpf.setText("");
        campoEndereco.setText("");
        campoRG.setText("");
        campoSalario.setText("");
        campoTelefone.setText("");
        checkFunc.setSelected(false);
        checkGen.setSelected(false);
    }

    public boolean adicionarPessoa(Pessoa col) {
        if (col.getNome() == null || col.getNome().isEmpty()
                || col.getCpfcnpj() == null || col.getCpfcnpj().isEmpty()
                || col.getTelefone() == null || col.getTelefone().isEmpty()
                || col.getEndereco() == null || col.getEndereco().isEmpty()
                || col.getEmail() == null || col.getEmail().isEmpty()
                || col.getSalario() == null
                || col.getTelefone() == null || col.getTelefone().isEmpty()
                || col.getRg() == null || col.getRg().isEmpty()
                || col.getCargo() == null || col.getCargo().isEmpty()) {
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
            if (verificaCPFCNPJ(col.getCpfcnpj(), col.getId()) || verificaRG(col.getRg(), col.getId())) {
                JOptionPane.showMessageDialog(null, "CPFCNPJ/RG QUE ESTA TENDO MODIFICAR NA EXISTE");
                return true;
            } else {
                return false;
            }
        } else {
            if (verificaCPFCNPJ(col.getCpfcnpj(), col.getId()) || verificaRG(col.getRg(), col.getId())) {
                JOptionPane.showMessageDialog(null, "CPFCNPJ/RG JA EXISTE NO BANCO");
                return true;
            } else {
                return false;
            }
        }
    }

    public void SetCampos(Pessoa col) {
        campoNome.setText(col.getNome());
        campoCpf.setText(col.getCpfcnpj());
        campoEmail.setText(col.getEmail());
        campoEndereco.setText(col.getEndereco());
        campoRG.setText(col.getRg());
        campoTelefone.setText(col.getTelefone());
        campoSalario.setText(col.getSalario().toString());
        this.col.setId(col.getId());
        if (col.getCargo().equals("Vendedor")) {
            checkFunc.setSelected(true);
        }
        if (col.getCargo().equals("Gerente")) {
            checkGen.setSelected(true);
        }
        System.out.println(col.getId() + "aqui1");
        validaCampos("novo");
        System.out.println(col.getId() + "aqui2");

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
            campoSalario.setEnabled(false);
            campoTelefone.setEnabled(false);
            checkFunc.setEnabled(false);
            checkGen.setEnabled(false);
        } else if (operacao.equals("novo")) {
            btNovo.setEnabled(false);
            btSalvar.setEnabled(true);
            btCancelar.setEnabled(true);
            campoNome.setEnabled(true);
            campoCpf.setEnabled(true);
            campoEmail.setEnabled(true);
            campoEndereco.setEnabled(true);
            campoRG.setEnabled(true);
            campoSalario.setEnabled(true);
            campoTelefone.setEnabled(true);
            checkFunc.setEnabled(true);
            checkGen.setEnabled(true);
        }

    }

    public CadastroColaboradores(java.awt.Frame parent, boolean modal) {
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        checkFunc = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        checkGen = new javax.swing.JCheckBox();
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
        jLabel10 = new javax.swing.JLabel();
        campoSalario = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        campoRG = new javax.swing.JTextField();
        btNovo = new javax.swing.JButton();
        btCancelar = new javax.swing.JButton();
        btIrLista = new javax.swing.JButton();
        btsair = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel2.setText("Cadastro de Colaboradores");

        checkFunc.setText("Funcionario");
        checkFunc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkFuncActionPerformed(evt);
            }
        });

        jLabel3.setText("Função:");

        checkGen.setText("Gerente");
        checkGen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkGenActionPerformed(evt);
            }
        });

        jLabel4.setText("Nome:");

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

        jLabel8.setText("CPF:");

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

        jLabel10.setText("Salario:");

        campoSalario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoSalarioActionPerformed(evt);
            }
        });
        campoSalario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                campoSalarioKeyTyped(evt);
            }
        });

        jLabel9.setText("RG:");

        campoRG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoRGActionPerformed(evt);
            }
        });

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

        btIrLista.setText("LISTA DE COLABORADORES");
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
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 28, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(btSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btCancelar))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(btIrLista)
                                .addGap(18, 18, 18)
                                .addComponent(btsair)))
                        .addContainerGap(44, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(jLabel4)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(campoNome, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(jLabel5)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(campoTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel6)
                                                .addComponent(jLabel7)
                                                .addComponent(jLabel10))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addComponent(jLabel8)
                                            .addGap(34, 34, 34)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addGap(40, 40, 40)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(campoEndereco)
                                    .addComponent(campoEmail)
                                    .addComponent(campoSalario, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(campoCpf)
                                    .addComponent(campoRG, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(checkFunc)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(checkGen)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(campoNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(campoTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(campoEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(campoEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(campoSalario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(campoCpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(campoRG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(checkFunc)
                    .addComponent(checkGen))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btSalvar)
                    .addComponent(btNovo)
                    .addComponent(btCancelar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btIrLista)
                    .addComponent(btsair))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void checkFuncActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkFuncActionPerformed
        if (checkFunc.isSelected()) {
            checkGen.setEnabled(false);
        } else {
            checkGen.setEnabled(true);
        }
    }//GEN-LAST:event_checkFuncActionPerformed

    private void campoCpfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoCpfActionPerformed

    }//GEN-LAST:event_campoCpfActionPerformed

    private void campoTelefoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoTelefoneActionPerformed

    }//GEN-LAST:event_campoTelefoneActionPerformed

    private void campoEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoEmailActionPerformed

    private void checkGenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkGenActionPerformed
        if (checkGen.isSelected()) {
            checkFunc.setEnabled(false);
        } else {
            checkFunc.setEnabled(true);
        }

    }//GEN-LAST:event_checkGenActionPerformed

    private void btSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSalvarActionPerformed

        col.setNome(campoNome.getText());
        col.setTelefone(campoTelefone.getText());
        col.setEndereco(campoEndereco.getText());
        col.setEmail(campoEmail.getText());
        String salarioTexto = campoSalario.getText();
        if (!salarioTexto.equals("")) {
            double salarioDouble = Double.parseDouble(salarioTexto);
            BigDecimal salarioBigDecimal = new BigDecimal(salarioDouble);
            col.setSalario(salarioBigDecimal);
        }
        col.setTipo(TipoPessoa.FUNCIONARIO);
        col.setCpfcnpj(campoCpf.getText());
        col.setRg(campoRG.getText());

        if (checkFunc.isSelected()) {
            col.setCargo("Vendedor");
        }
        if (checkGen.isSelected()) {
            col.setCargo("Gerente");
        }

        if (adicionarPessoa(col)) {
            if (VerificacaoEdit() == true) {
                return;
            }
            HibernateUtil.beginTransaction();
            HibernateUtil.getSession().merge(col);
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
            if (habilitarEdicao) {
                JOptionPane.showMessageDialog(null, "EDIÇÃO REALIZADA COM SUCESSO");
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "COLABORADOR CADASTRADO COM SUCESSO");
            }
            limpaCampos();
            habilitarEdicao = false;
            col = new Pessoa();
            validaCampos("inicio");
        }
    }//GEN-LAST:event_btSalvarActionPerformed

    private void campoRGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoRGActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoRGActionPerformed

    private void campoSalarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoSalarioActionPerformed

    }//GEN-LAST:event_campoSalarioActionPerformed

    private void campoSalarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoSalarioKeyTyped
        char c = evt.getKeyChar();

        if (!Character.isDigit(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_campoSalarioKeyTyped

    private void btNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btNovoActionPerformed
        //col = new Pessoa();
        limpaCampos();
        validaCampos("novo");
    }//GEN-LAST:event_btNovoActionPerformed

    private void btCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelarActionPerformed
        limpaCampos();
        habilitarEdicao = false;
        validaCampos("inicio");
    }//GEN-LAST:event_btCancelarActionPerformed

    private void btIrListaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btIrListaActionPerformed
        habilitarEdicao = false;

        dispose();
        ListarColaboradores frm = new ListarColaboradores(frame, true);
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
            java.util.logging.Logger.getLogger(CadastroColaboradores.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CadastroColaboradores.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CadastroColaboradores.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CadastroColaboradores.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                CadastroColaboradores dialog = new CadastroColaboradores(new javax.swing.JFrame(), true);
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
    private javax.swing.JTextField campoCpf;
    private javax.swing.JTextField campoEmail;
    private javax.swing.JTextField campoEndereco;
    private javax.swing.JTextField campoNome;
    private javax.swing.JTextField campoRG;
    private javax.swing.JTextField campoSalario;
    private javax.swing.JTextField campoTelefone;
    private javax.swing.JCheckBox checkFunc;
    private javax.swing.JCheckBox checkGen;
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
    // End of variables declaration//GEN-END:variables
}
