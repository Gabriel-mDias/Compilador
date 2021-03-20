/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.compilador.presenter;

import br.ufes.compilador.view.JanelaPrincipalView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author gabriel
 */
public class JanelaPrincipalPresenter {
    
    private JanelaPrincipalView view;

    public JanelaPrincipalPresenter() {
            
            //Gerando a nova tela
        this.view = new JanelaPrincipalView();
        this.view.setVisible(true);
        
            //Gerando actionListeners para os botões
        this.view.getBtnCompilar().addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    view.getTxtSaida().setText("Compilado!");
                }
            }
        );
    }
    
    
    
}
