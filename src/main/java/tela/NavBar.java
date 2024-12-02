/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package tela;

import tela.Relatorios.ListarColaboradores;
import tela.Cadastros.CadastroProdutos;
import tela.Cadastros.CadastroClientes;
import tela.Cadastros.CadastroFornecedores;
import tela.Cadastros.CadastroColaboradores;
import tela.Movimentos.VendaTela;
import tela.Relatorios.ListarClientes;
import tela.Relatorios.ListarFornecedores;
import tela.Relatorios.ListarProdutos;
import tela.Relatorios.ListarVendas;
import tela.Relatorios.produtoVendido;

/**
 *
 * @author felip
 */
public class NavBar extends javax.swing.JFrame {

    /**
     * Creates new form NavBar
     */
    public NavBar() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        cadastro = new javax.swing.JMenu();
        colaboradores = new javax.swing.JMenuItem();
        clientes = new javax.swing.JMenuItem();
        fornecedores = new javax.swing.JMenuItem();
        produtos = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        venda = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        listarvendas = new javax.swing.JMenuItem();
        listarClientes = new javax.swing.JMenuItem();
        listarColaboradores = new javax.swing.JMenuItem();
        listarprodutos = new javax.swing.JMenuItem();
        listarfornecedores = new javax.swing.JMenuItem();
        produtovendido = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem9 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        cadastro.setText("Cadastros");

        colaboradores.setText("Colaboradores");
        colaboradores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colaboradoresActionPerformed(evt);
            }
        });
        cadastro.add(colaboradores);

        clientes.setText("Clientes");
        clientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clientesActionPerformed(evt);
            }
        });
        cadastro.add(clientes);

        fornecedores.setText("Fornecedores");
        fornecedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fornecedoresActionPerformed(evt);
            }
        });
        cadastro.add(fornecedores);

        produtos.setText("Produtos");
        produtos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                produtosActionPerformed(evt);
            }
        });
        cadastro.add(produtos);

        jMenuBar1.add(cadastro);

        jMenu2.setText("Movimentos");

        venda.setText("Venda");
        venda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vendaActionPerformed(evt);
            }
        });
        jMenu2.add(venda);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Relatorios");

        listarvendas.setText("ListarVendas");
        listarvendas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listarvendasActionPerformed(evt);
            }
        });
        jMenu3.add(listarvendas);

        listarClientes.setText("ListarClientes");
        listarClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listarClientesActionPerformed(evt);
            }
        });
        jMenu3.add(listarClientes);

        listarColaboradores.setText("ListarColaboradores");
        listarColaboradores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listarColaboradoresActionPerformed(evt);
            }
        });
        jMenu3.add(listarColaboradores);

        listarprodutos.setText("ListarProdutos");
        listarprodutos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listarprodutosActionPerformed(evt);
            }
        });
        jMenu3.add(listarprodutos);

        listarfornecedores.setText("ListarForcenedores");
        listarfornecedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listarfornecedoresActionPerformed(evt);
            }
        });
        jMenu3.add(listarfornecedores);

        produtovendido.setText("Produto mais Vendido");
        produtovendido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                produtovendidoActionPerformed(evt);
            }
        });
        jMenu3.add(produtovendido);

        jMenuBar1.add(jMenu3);

        jMenu4.setText("Ajuda");

        jMenuItem9.setText("Sair");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem9);

        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 557, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 401, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        System.exit(1);
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void colaboradoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colaboradoresActionPerformed
        CadastroColaboradores tela = new CadastroColaboradores(this, true);
        tela.setLocationRelativeTo(null);
        tela.setVisible(true);
    }//GEN-LAST:event_colaboradoresActionPerformed

    private void fornecedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fornecedoresActionPerformed
        CadastroFornecedores tela = new CadastroFornecedores(this, true);
        tela.setLocationRelativeTo(null);
        tela.setVisible(true);
    }//GEN-LAST:event_fornecedoresActionPerformed

    private void clientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clientesActionPerformed
        CadastroClientes tela = new CadastroClientes(this, true);
        tela.setLocationRelativeTo(null);
        tela.setVisible(true);
    }//GEN-LAST:event_clientesActionPerformed

    private void produtosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_produtosActionPerformed
        CadastroProdutos tela = new CadastroProdutos(this, true);
        tela.setLocationRelativeTo(null);
        tela.setVisible(true);
    }//GEN-LAST:event_produtosActionPerformed

    private void vendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vendaActionPerformed
        VendaTela tela = new VendaTela(this, true);
        tela.setLocationRelativeTo(null);
        tela.setVisible(true);
    }//GEN-LAST:event_vendaActionPerformed

    private void listarColaboradoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listarColaboradoresActionPerformed
        ListarColaboradores tela = new ListarColaboradores(this, true);
        tela.setLocationRelativeTo(null);
        tela.setVisible(true);
    }//GEN-LAST:event_listarColaboradoresActionPerformed

    private void listarClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listarClientesActionPerformed
        ListarClientes tela = new ListarClientes(this, true);
        tela.setLocationRelativeTo(null);
        tela.setVisible(true);
    }//GEN-LAST:event_listarClientesActionPerformed

    private void listarvendasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listarvendasActionPerformed
        ListarVendas tela = new ListarVendas(this, true);
        tela.setLocationRelativeTo(null);
        tela.setVisible(true);
    }//GEN-LAST:event_listarvendasActionPerformed

    private void listarprodutosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listarprodutosActionPerformed
        ListarProdutos tela = new ListarProdutos(this, true);
        tela.setLocationRelativeTo(null);
        tela.setVisible(true);
    }//GEN-LAST:event_listarprodutosActionPerformed

    private void listarfornecedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listarfornecedoresActionPerformed
        ListarFornecedores tela = new ListarFornecedores(this, true);
        tela.setLocationRelativeTo(null);
        tela.setVisible(true);
    }//GEN-LAST:event_listarfornecedoresActionPerformed

    private void produtovendidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_produtovendidoActionPerformed
        produtoVendido tela = new produtoVendido(this, true);
        tela.setLocationRelativeTo(null);
        tela.setVisible(true);
    }//GEN-LAST:event_produtovendidoActionPerformed

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
            java.util.logging.Logger.getLogger(NavBar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NavBar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NavBar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NavBar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NavBar().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu cadastro;
    private javax.swing.JMenuItem clientes;
    private javax.swing.JMenuItem colaboradores;
    private javax.swing.JMenuItem fornecedores;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JMenuItem listarClientes;
    private javax.swing.JMenuItem listarColaboradores;
    private javax.swing.JMenuItem listarfornecedores;
    private javax.swing.JMenuItem listarprodutos;
    private javax.swing.JMenuItem listarvendas;
    private javax.swing.JMenuItem produtos;
    private javax.swing.JMenuItem produtovendido;
    private javax.swing.JMenuItem venda;
    // End of variables declaration//GEN-END:variables
}