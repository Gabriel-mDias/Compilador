/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.compilador.view;

import javax.swing.JButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

/**
 *
 * @author gabriel
 */
public class JanelaPrincipalView extends javax.swing.JFrame {

    public JanelaPrincipalView() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelCodigo = new javax.swing.JPanel();
        scrollPanelCodigo = new javax.swing.JScrollPane();
        txtCodigo = new javax.swing.JTextArea();
        tabPanelResultados = new javax.swing.JTabbedPane();
        panelAnaliseLexico = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblAnaliseLexica = new javax.swing.JTable();
        toolBar = new javax.swing.JToolBar();
        btnCompilar = new javax.swing.JButton();
        lbSaida = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSaida = new javax.swing.JTable(){
            @Override
            public void changeSelection(int rowIndex, int columnIndex,
                boolean toggle, boolean extend) {
                super.changeSelection(rowIndex, columnIndex, true, false);
            }
        };
        menuBar = new javax.swing.JMenuBar();
        menuArquivo = new javax.swing.JMenu();
        menuExecutar = new javax.swing.JMenu();
        rbAnaliseLexicaAuto = new javax.swing.JRadioButtonMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txtCodigo.setColumns(20);
        txtCodigo.setRows(5);
        txtCodigo.setTabSize(2);
        scrollPanelCodigo.setViewportView(txtCodigo);

        javax.swing.GroupLayout panelCodigoLayout = new javax.swing.GroupLayout(panelCodigo);
        panelCodigo.setLayout(panelCodigoLayout);
        panelCodigoLayout.setHorizontalGroup(
            panelCodigoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCodigoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPanelCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelCodigoLayout.setVerticalGroup(
            panelCodigoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPanelCodigo)
        );

        tblAnaliseLexica.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Linha", "Lexema", "Token"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblAnaliseLexica);
        if (tblAnaliseLexica.getColumnModel().getColumnCount() > 0) {
            tblAnaliseLexica.getColumnModel().getColumn(0).setResizable(false);
            tblAnaliseLexica.getColumnModel().getColumn(1).setResizable(false);
            tblAnaliseLexica.getColumnModel().getColumn(2).setResizable(false);
            tblAnaliseLexica.getColumnModel().getColumn(3).setResizable(false);
        }

        javax.swing.GroupLayout panelAnaliseLexicoLayout = new javax.swing.GroupLayout(panelAnaliseLexico);
        panelAnaliseLexico.setLayout(panelAnaliseLexicoLayout);
        panelAnaliseLexicoLayout.setHorizontalGroup(
            panelAnaliseLexicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 405, Short.MAX_VALUE)
        );
        panelAnaliseLexicoLayout.setVerticalGroup(
            panelAnaliseLexicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
        );

        tabPanelResultados.addTab("An??lise L??xica", panelAnaliseLexico);

        toolBar.setRollover(true);

        btnCompilar.setText("Compilar");
        btnCompilar.setFocusable(false);
        btnCompilar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCompilar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBar.add(btnCompilar);
        btnCompilar.getAccessibleContext().setAccessibleDescription("Bot??o para compilar o c??digo fornecido");

        lbSaida.setText("Sa??da:");

        tblSaida.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Error", "Linha", "Posi????o", "ID do Token"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblSaida);
        if (tblSaida.getColumnModel().getColumnCount() > 0) {
            tblSaida.getColumnModel().getColumn(0).setResizable(false);
            tblSaida.getColumnModel().getColumn(1).setResizable(false);
            tblSaida.getColumnModel().getColumn(2).setResizable(false);
            tblSaida.getColumnModel().getColumn(3).setResizable(false);
        }

        menuArquivo.setText("Arquivo");
        menuBar.add(menuArquivo);

        menuExecutar.setText("Executar");

        rbAnaliseLexicaAuto.setText("An??lise L??xica a cada altera????o");
        rbAnaliseLexicaAuto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbAnaliseLexicaAutoActionPerformed(evt);
            }
        });
        menuExecutar.add(rbAnaliseLexicaAuto);

        menuBar.add(menuExecutar);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(toolBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(panelCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tabPanelResultados))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lbSaida, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(toolBar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tabPanelResultados))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbSaida)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabPanelResultados.getAccessibleContext().setAccessibleName("An??lise Lexica");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void rbAnaliseLexicaAutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbAnaliseLexicaAutoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbAnaliseLexicaAutoActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCompilar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbSaida;
    private javax.swing.JMenu menuArquivo;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuExecutar;
    private javax.swing.JPanel panelAnaliseLexico;
    private javax.swing.JPanel panelCodigo;
    private javax.swing.JRadioButtonMenuItem rbAnaliseLexicaAuto;
    private javax.swing.JScrollPane scrollPanelCodigo;
    private javax.swing.JTabbedPane tabPanelResultados;
    private javax.swing.JTable tblAnaliseLexica;
    private javax.swing.JTable tblSaida;
    private javax.swing.JToolBar toolBar;
    private javax.swing.JTextArea txtCodigo;
    // End of variables declaration//GEN-END:variables

    public JButton getBtnCompilar() {
        return btnCompilar;
    }

    public void setBtnCompilar(JButton btnCompilar) {
        this.btnCompilar = btnCompilar;
    }

    public JTable getTblAnaliseLexica() {
        return tblAnaliseLexica;
    }

    public void setTblAnaliseLexica(JTable tblAnaliseLexica) {
        this.tblAnaliseLexica = tblAnaliseLexica;
    }

    public JTextArea getTxtCodigo() {
        return txtCodigo;
    }

    public void setTxtCodigo(JTextArea txtCodigo) {
        this.txtCodigo = txtCodigo;
    }

    public JRadioButtonMenuItem getRbAnaliseLexicaAuto() {
        return rbAnaliseLexicaAuto;
    }

    public void setRbAnaliseLexicaAuto(JRadioButtonMenuItem rbAnaliseLexicaAuto) {
        this.rbAnaliseLexicaAuto = rbAnaliseLexicaAuto;
    }

    public JTable getTblSaida() {
        return tblSaida;
    }

    public void setTblSaida(JTable tblSaida) {
        this.tblSaida = tblSaida;
    }
    
    public JTabbedPane getTabPanelResultados() {
        return tabPanelResultados;
    }

    public void setTabPanelResultados(JTabbedPane tabPanelResultados) {
        this.tabPanelResultados = tabPanelResultados;
    }
    
}
