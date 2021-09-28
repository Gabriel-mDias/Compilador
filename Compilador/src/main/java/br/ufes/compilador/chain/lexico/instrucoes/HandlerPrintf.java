


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
public class HandlerPrintf  extends AbstractHandler{

    public HandlerPrintf(Token token) {
        super(token);
    }

    @Override
    public void executar(Token token) {
        if (token.getSimbolo().toString().toLowerCase().compareTo("printf") == 0){
            token.setCategoria("instrucao_printf");
        } else {
            this.setProximo(new HandlerReturn(token));
        }
    }
    
    @Override
    public String recuperarErrosLexico(Token token) {
        if(StringUtils.similarity(token.getSimbolo(), "printf") >= 0.8 ){
            return "Esse token pode ser substituido por: instrucao_printf ";
        } else if(StringUtils.similarity(token.getSimbolo(), "printf") > 0.5 ){
            return "Esse token tem similaridade com: instrucao_printf; "+ proximo.recuperarErrosLexico(token);
        } 
        
        return proximo.recuperarErrosLexico(token);
    }
}
