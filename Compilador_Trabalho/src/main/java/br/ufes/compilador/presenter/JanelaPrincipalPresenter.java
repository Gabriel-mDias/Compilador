/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.compilador.presenter;

import br.ufes.compilador.models.LinhaCodigo;
import br.ufes.compilador.view.JanelaPrincipalView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gabriel
 */
public class JanelaPrincipalPresenter {
    
    private JanelaPrincipalView view;
    private List<LinhaCodigo> linhas;

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
                    compilarCodigo(view.getTxtCodigo().getText());
                }
            }
        );
    }
    
    private void compilarCodigo(String codigoFonte){
        
        this.linhas = new ArrayList<LinhaCodigo>();
        int posicaoLinha = 1;
        
        codigoFonte = preProcessamentoCodigo(codigoFonte);
        
            //Para cada quebra de linha, uma nova LinhaCodigo é gerada
        for(String linha : codigoFonte.split("\n")){
            linhas.add( new LinhaCodigo(linha, posicaoLinha++).removerComentarioSimples());
        }
        
        view.getTxtSaida().setText(codigoFonte);
    }
    
    /**
     * Esse método removerá tabulações, comentários, entre outros pré processamentos necessários
     * @param codigoFonte
     * @return 
     */
    private String preProcessamentoCodigo(String codigoFonte){
            //Descosiderando tabulações
        codigoFonte = codigoFonte.replaceAll("\t", "");
        
        codigoFonte = codigoFonte.replaceAll("\\/\\*([\\s\\S]*)\\*\\/", "");
        
        return codigoFonte;
    }
}
