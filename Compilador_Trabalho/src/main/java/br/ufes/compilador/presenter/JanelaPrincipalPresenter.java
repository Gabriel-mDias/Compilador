/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.compilador.presenter;

import br.ufes.compilador.chain.AbstractHandler;
import br.ufes.compilador.chain.lexico.especificadores.HandlerAuto;
import br.ufes.compilador.chain.sintatico.AnalisadorSintatico;
import br.ufes.compilador.models.Erros;
import br.ufes.compilador.models.LinhaCodigo;
import br.ufes.compilador.models.Token;
import br.ufes.compilador.view.JanelaPrincipalView;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.swing.Timer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author gabriel
 */
public class JanelaPrincipalPresenter {
    
    private JanelaPrincipalView view;
    private List<LinhaCodigo> linhas;
    private Erros gerenciadorErro;
    private List<Token> tokens;
    private Timer timerReloadCodigo;
    private boolean listenerAlteracoes = false;
    private KeyListener keyListenerTextArea;

    public JanelaPrincipalPresenter() {
        
            //Inicializando variáveis
        this.gerenciadorErro = new Erros();
            
            //Gerando a nova tela
        this.view = new JanelaPrincipalView();
        this.view.setVisible(true);
        
            //Gerando actionListeners para os botões
        this.view.getBtnCompilar().addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    compilarCodigo(view.getTxtCodigo().getText());
                }
            }
        );
        
            //Gerando actionListener para o Radio que define se a análise léxica será automática ou não
        this.view.getRbAnaliseLexicaAuto().addActionListener(
                new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(view.getRbAnaliseLexicaAuto().isSelected()){
                        addKeyReleaseListenerCodigo();
                    }else{
                        view.getTxtCodigo().removeKeyListener(keyListenerTextArea);
                    }
                }
            }
        );
        
        this.view.getTblSaida().getSelectionModel().addListSelectionListener(
            new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if(view.getTblSaida().getSelectedRow()>= 0){
                        erroSelecionado(view.getTblSaida().getSelectedRow());
                    }else{
                        view.getTxtCodigo().setSelectionColor(new Color(176, 197, 227));
                    }
                }
            }
       );
        
    }
    
    private void compilarCodigo(String codigoFonte){
        
        this.linhas = new ArrayList<>();
        this.tokens = new ArrayList<>();
        int posicaoLinha = 1;
        int idToken = 1;
        
        String codigoFonteProcessado = preProcessamentoCodigo(codigoFonte);
        
            //Para cada quebra de linha, uma nova LinhaCodigo é gerada
        for(String linha : codigoFonteProcessado.split("\n")){
            
            LinhaCodigo novaLinha = new LinhaCodigo(linha, posicaoLinha++).removerComentarioSimples();
            linhas.add(novaLinha);
            
                //Para cada linha, Tokens são buscados
            String palavra = "";
            
            //A opção por percorrer caracter por caracter é devido a possibilidade de um simbolo estar presente mais de uma vez na linha
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

        this.tokens =  this.calcularPosicaoOriginal(tokens, codigoFonte);
        this.tokens = this.chainAnaliseLexica(tokens);
        this.preencheTabelaAnaliseLexica(tokens);
        new AnalisadorSintatico(view).analiseSintatica(tokens, gerenciadorErro);
        
        this.preencheTabelaErro(tokens);
    }
    
    /**
     * Esse método removerá tabulações, comentários, entre outros pré processamentos necessários
     * @param codigoFonte
     * @return 
     */
    private String preProcessamentoCodigo(String codigoFonte){
        
        //Removendo blocos de comentários
        codigoFonte = codigoFonte.replaceAll("\\/\\*([\\s\\S]*)\\*\\/", "");
        
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

        codigoFonte = codigoFonte.replaceAll("\\% = ", " \\%.= ");
        codigoFonte = codigoFonte.replaceAll("\\%", " \\% ");
        codigoFonte = codigoFonte.replaceAll("\\% \\.=", "\\%=");
        
        //Descosiderando tabulações, removendo espaços multiplos e no fim da linha
        codigoFonte = codigoFonte.replaceAll("\t", " ");
        codigoFonte = codigoFonte.replaceAll("\\ +", " ");
        codigoFonte = codigoFonte.replaceAll("\\ \\n", "\\\n");
        
        return codigoFonte;
    }
    
    /**
     * Método responsável por executar a Chain of Responsability
     * referente a análise léxica com os dados passados como parâmetro
     */
    private List<Token> chainAnaliseLexica(List<Token> tokens){
        for(Token token : tokens){
            AbstractHandler handler = new HandlerAuto(token);
            
            if(token.getCategoria().equalsIgnoreCase("error") || token.getCategoria().equalsIgnoreCase("undefined")){
                this.gerenciadorErro.addErro( token, handler.recuperarErrosLexico(token) );
            }
        }
        
        return tokens;
    }
    
    private void preencheTabelaAnaliseLexica(List<Token> tokens){
        DefaultTableModel modelTabela = new DefaultTableModel(new Object[]{"ID", "Linha", "Lexema", "Token"}, 0) {
            @Override
            public boolean isCellEditable(int rowIndex, int mColIndex) {
                return false;
            }
        }; 
        
        for(Token token : tokens){
            modelTabela.addRow(new Object[]{
                token.getId(),
                token.getLinha().getPosicao(),
                token.getSimbolo(),
                token.getCategoria()
            });
        }
        
        this.view.getTblAnaliseLexica().setModel(modelTabela);
    }
    
    /**
     * Método responsável por criar um listener para escrita no TextArea do código
     */
    private void addKeyReleaseListenerCodigo(){
        
        keyListenerTextArea = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                listenerAlteracoes = true;
                actionListenerKeyReleasedTextArea();
            }
        };
        
        this.view.getTxtCodigo().addKeyListener(keyListenerTextArea);
    }
       
    private void actionListenerKeyReleasedTextArea() {
        
        view.getTblAnaliseLexica().clearSelection();

        if (listenerAlteracoes) {
            if (timerReloadCodigo != null) {
                timerReloadCodigo.stop();
                timerReloadCodigo = null;
            }

            ActionListener action = new ActionListener() {
                @Override
                public void actionPerformed(@SuppressWarnings("unused") java.awt.event.ActionEvent e) {
                    if (listenerAlteracoes) {
                        listenerAlteracoes = false;
                        compilarCodigo(view.getTxtCodigo().getText());
                    }
                }
            };

            timerReloadCodigo = new Timer(1000, action);
            timerReloadCodigo.start();

        } else {
            if (timerReloadCodigo != null) {
                timerReloadCodigo.stop();
                timerReloadCodigo = null;
            }
        }
    }
    
    private void preencheTabelaErro(List<Token> tokens){
        DefaultTableModel modelTabela = new DefaultTableModel(new Object[]{"Error", "Linha", "Posição", "ID do Token"}, 0) {
            @Override
            public boolean isCellEditable(int rowIndex, int mColIndex) {
                return false;
            }
        }; 
        
        for(Token token : tokens){
            if(token.getCategoria().equalsIgnoreCase("error") || token.getCategoria().equalsIgnoreCase("undefined")){
                
                modelTabela.addRow(new Object[]{
                    gerenciadorErro.getMensagem(token),
                    token.getLinha().getPosicao(),
                    token.getPosicaoInicio(),
                    token.getId()
                });
            }
            
        }
        
        this.view.getTblSaida().setModel(modelTabela);
    }
    
    /**
     * Para fazer o pré processamento, o texto original é moldado de acordo com algumas necessidades
     * e essas necessidades o distorcem. Para calcular a real posição, esse método usa o texto original.
     * @param tokens
     * @param textoOriginal
     * @return 
     */
    private List<Token> calcularPosicaoOriginal(List<Token> tokens, String textoOriginal){
        
        /**
         * Ordena a lista visando o ID, do maior para o menor
         */
        tokens.sort( new Comparator<Token>(){
            @Override
            public int compare(Token o1, Token o2) {
                if(o2.getId() > o1.getId()){
                    return -1;   //O primeiro é menor que o segundo
                }
                return 1; //O segundo é menor que o primeiro
            }
        });
        
        int posTexto=0;
        int posToken, posComentarioSimples, posComentarioBloco;
        for(Token token : tokens){
            posToken = textoOriginal.indexOf(token.getSimbolo(), posTexto);
            posComentarioSimples = textoOriginal.indexOf("//", posTexto);
            posComentarioBloco = textoOriginal.indexOf("/*", posTexto);
            
                //Se não forem encontrados, sua posição é jogada após o final do texto
            posComentarioSimples = posComentarioSimples > 0 ? posComentarioSimples : textoOriginal.length()+1;
            posComentarioBloco = posComentarioBloco > 0 ? posComentarioBloco : textoOriginal.length()+1;
            
                /*Caso há um comentário antes da ocorrência do token:
                Esse loop tentará procurar a primeira ocorrência do fechamento do bloco de comentário, e saltar a variável 
                referente a posição do texto*/
            while(posToken > posComentarioSimples || posToken > posComentarioBloco){
                if(posToken > posComentarioSimples){
                    posTexto=textoOriginal.indexOf("\n", posComentarioSimples);
                }else if(posToken > posComentarioBloco){
                    posTexto=textoOriginal.indexOf("*/", posComentarioBloco) + 1;
                }
                posToken = textoOriginal.indexOf(token.getSimbolo(), posTexto);
                posComentarioSimples = textoOriginal.indexOf("//", posTexto) > 0 ? textoOriginal.indexOf("//", posTexto) : textoOriginal.length()+1;
                posComentarioBloco = textoOriginal.indexOf("*/", posTexto) > 0   ? textoOriginal.indexOf("*/", posTexto) : textoOriginal.length()+1;
            }
            
            token.setPosicaoInicio(posToken);
            token.setPosicaoFim(posToken+token.getSimbolo().length());
            
            posTexto=token.getPosicaoFim();
        }
        
        return tokens;
    }
    
    private void erroSelecionado(int linhaTblSaida){
        int idToken = (int) this.view.getTblSaida().getValueAt(linhaTblSaida, 3);
        
        Token erroSelecionado = null;
        for(Token token : tokens){
            if(token.getId() == idToken){
                erroSelecionado = token;
                break;
            }   
        }
        
        if(erroSelecionado != null){
            this.view.getTxtCodigo().setSelectionStart(erroSelecionado.getPosicaoInicio());
            this.view.getTxtCodigo().setSelectionEnd(erroSelecionado.getPosicaoFim());
            this.view.getTxtCodigo().setSelectionColor(Color.PINK);
            this.view.getTxtCodigo().requestFocus();
        }
    }
}
