/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.compilador.chain.lexico.delimitadores.literais;

import br.ufes.compilador.chain.AbstractHandler;
import br.ufes.compilador.chain.lexico.instrucoes.HandlerAbreColchete;
import br.ufes.compilador.models.Token;
import br.ufes.compilador.utils.StringUtils;

/**
 *
 * @author Matheus
 */
public class HandlerAspas  extends AbstractHandler{

    public HandlerAspas(Token token) {
        super(token);
    }

    @Override
    public void executar(Token token) {
        if ((token.getSimbolo().toString().toLowerCase().compareTo("\"") == 0) ||
                (token.getSimbolo().toString().toLowerCase().compareTo("\'") == 0)){
            token.setCategoria("delimitador_literal_aspas");
        } else {
            this.setProximo(new HandlerAbreColchete(token));

        }
    }
    
    @Override
    public String recuperarErrosLexico(Token token) {
        if( (StringUtils.similarity(token.getSimbolo(), "\"") >= 0.8 ) || (StringUtils.similarity(token.getSimbolo(), "\'") >= 0.8 ) ){
            return "Esse token é similar a: delimitador_instrucao_ponto_e_virgula ";
        } else if((StringUtils.similarity(token.getSimbolo(), "\"") > 0.5  ) || (StringUtils.similarity(token.getSimbolo(), "\'") > 0.5  ) ){
            return "Esse token é poderia ser substituido por: delimitador_instrucao_ponto_e_virgula; "+ proximo.recuperarErrosLexico(token);
        } 
        
        return proximo.recuperarErrosLexico(token);
    }
}