


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.compilador.chain.lexico.instrucoes;

import br.ufes.compilador.chain.AbstractHandler;
import br.ufes.compilador.models.Token;
import br.ufes.compilador.utils.StringUtils;

/**
 *
 * @author Matheus
 */
public class HandlerBreak  extends AbstractHandler{

    public HandlerBreak(Token token) {
        super(token);
    }

    @Override
    public void executar(Token token) {
        if (token.getSimbolo().toString().toLowerCase().compareTo("break") == 0){
            token.setCategoria("instrucao_break");
        } else {
            this.setProximo(new HandlerElse(token));

        }
    }
    
    @Override
    public String recuperarErrosLexico(Token token) {
        if(StringUtils.similarity(token.getSimbolo(), "break") >= 0.8 ){
            return "Esse token pode ser substituido por: instrucao_break ";
        } else if(StringUtils.similarity(token.getSimbolo(), "break") > 0.5 ){
            return "Esse token tem similaridade com: instrucao_break; "+ proximo.recuperarErrosLexico(token);
        } 
        
        return proximo.recuperarErrosLexico(token);
    }
}
