/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.compilador.presenter;

import br.ufes.compilador.models.LinhaCodigo;
import br.ufes.compilador.models.Token;
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
    private List<Token> tokens;

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
        
        this.linhas = new ArrayList<>();
        this.tokens = new ArrayList<>();
        int posicaoLinha = 1;
        int idToken = 1;
        
        codigoFonte = preProcessamentoCodigo(codigoFonte);
        
            //Para cada quebra de linha, uma nova LinhaCodigo é gerada
        for(String linha : codigoFonte.split("\n")){
            
            LinhaCodigo novaLinha = new LinhaCodigo(linha, posicaoLinha++).removerComentarioSimples();
            linhas.add(novaLinha);
            
                //Para cada linha, Tokens são buscados
            String palavra = "";
            
            //A opção por percorrer caracter por caracter é devido a possibilidade de um simbolo estar presente mais de uma vez na lista
            for(int posicao = 0; posicao < novaLinha.getConteudo().length(); posicao++ ){
                
                if(novaLinha.getConteudo().charAt(posicao) != ' '){
                    
                    int fimToken = novaLinha.getConteudo().indexOf(" ", posicao);
                    
                    if(fimToken > -1 ){
                        palavra = novaLinha.getConteudo().substring(posicao,fimToken);
                    }else{
                        palavra = novaLinha.getConteudo().substring(posicao);
                    }
                    
                    Token novoToken = new Token(idToken++, palavra, posicao, fimToken-1, "undefined", novaLinha);
                    tokens.add(novoToken);
                    
                    posicao += palavra.length();
                }
                
            }
        }

        
    }
    
    /**
     * Esse método removerá tabulações, comentários, entre outros pré processamentos necessários
     * @param codigoFonte
     * @return 
     */
    private String preProcessamentoCodigo(String codigoFonte){
        
            //Separação dos tokens específicos, dando o espaço do restante
  
        // Vírgurla
        codigoFonte = codigoFonte.replaceAll(",", " , ");

        //Ponto e vírgula
        codigoFonte = codigoFonte.replaceAll(";", " ; ");
        
        // Colchetes
        codigoFonte = codigoFonte.replaceAll("\\[", " \\[ ");
        codigoFonte = codigoFonte.replaceAll("\\]", " \\] ");
        
        // Parenteses
        codigoFonte = codigoFonte.replaceAll("\\(", " \\( ");
        codigoFonte = codigoFonte.replaceAll("\\)", " \\) ");

        // Chaves
        codigoFonte = codigoFonte.replaceAll("\\{", " \\{ ");
        codigoFonte = codigoFonte.replaceAll("\\}", " \\} ");

        // Aspas
        codigoFonte = codigoFonte.replaceAll("\\'", " \\' ");
        codigoFonte = codigoFonte.replaceAll("\\\"", " \\\" ");

        // Operadores lógicos
        codigoFonte = codigoFonte.replaceAll("\\&&", " \\&& ");
        codigoFonte = codigoFonte.replaceAll("\\|\\|", " \\|\\| ");

        // Geral
        codigoFonte = codigoFonte.replaceAll("\\==", " \\=.= ");
        codigoFonte = codigoFonte.replaceAll("\\=", " \\= ");
        codigoFonte = codigoFonte.replaceAll("\\= \\. \\=", "\\==");

        codigoFonte = codigoFonte.replaceAll("\\! = ", " \\!.= ");
        codigoFonte = codigoFonte.replaceAll("\\!", " \\! ");
        codigoFonte = codigoFonte.replaceAll("\\! \\.=", "\\!=");

        codigoFonte = codigoFonte.replaceAll("\\< = ", " \\<.= ");
        codigoFonte = codigoFonte.replaceAll("\\<", " \\< ");
        codigoFonte = codigoFonte.replaceAll("\\< \\.=", "\\<=");

        codigoFonte = codigoFonte.replaceAll("\\> = ", " \\>.= ");
        codigoFonte = codigoFonte.replaceAll("\\>", " \\> ");
        codigoFonte = codigoFonte.replaceAll("\\> \\.=", "\\>=");

        codigoFonte = codigoFonte.replaceAll("\\+ = ", " \\+.= ");
        codigoFonte = codigoFonte.replaceAll("\\+", " \\+ ");
        codigoFonte = codigoFonte.replaceAll("\\+ \\.=", "\\+=");

        codigoFonte = codigoFonte.replaceAll("\\- = ", " \\-.= ");
        codigoFonte = codigoFonte.replaceAll("\\-", " \\- ");
        codigoFonte = codigoFonte.replaceAll("\\- \\.=", "\\-=");

        codigoFonte = codigoFonte.replaceAll("\\* = ", " \\*.= ");
        codigoFonte = codigoFonte.replaceAll("\\*", " \\* ");
        codigoFonte = codigoFonte.replaceAll("\\* \\.=", "\\*=");

        codigoFonte = codigoFonte.replaceAll("\\/ = ", " \\/.= ");
        codigoFonte = codigoFonte.replaceAll("\\/", " \\/ ");
        codigoFonte = codigoFonte.replaceAll("\\/ \\.=", "\\/=");

        codigoFonte = codigoFonte.replaceAll("\\% = ", " \\%.= ");
        codigoFonte = codigoFonte.replaceAll("\\%", " \\% ");
        codigoFonte = codigoFonte.replaceAll("\\% \\.=", "\\%=");
        
        //Descosiderando tabulações, removendo espaços multiplos e no fim da linha
        codigoFonte = codigoFonte.replaceAll("\t", " ");
        codigoFonte = codigoFonte.replaceAll("\\ +", " ");
        codigoFonte = codigoFonte.replaceAll("\\ \\n", "\\\n");
        
        //Removendo blocos de comentários
        codigoFonte = codigoFonte.replaceAll("\\/\\*([\\s\\S]*)\\*\\/", "");
        
        return codigoFonte;
    }
    
}
