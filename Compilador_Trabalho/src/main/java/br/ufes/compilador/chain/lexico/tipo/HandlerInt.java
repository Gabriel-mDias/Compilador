/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.compilador.chain.lexico.tipo;

import br.ufes.compilador.chain.AbstractHandler;
import br.ufes.compilador.models.Token;
import br.ufes.compilador.utils.StringUtils;

/**
 *
 * @author gabriel
 */
public class HandlerInt  extends AbstractHandler{

    public HandlerInt(Token token) {
        super(token);
    }

    @Override
    public void executar(Token token) {
        if(token.getSimbolo().equals("int")){
            token.setCategoria("Especificador_INT");
        }else {
            this.setProximo(new HandlerLong(token));
        }  
    }
    
    @Override
    public String recuperarErrosLexico(Token token) {
        if(StringUtils.similarity(token.getSimbolo(), "int") >= 0.8 ){
            return "Esse token pode ser substituido por: Especificador_INT ";
        } else if(StringUtils.similarity(token.getSimbolo(), "int") > 0.5 ){
            return "Esse token tem similaridade com: Especificador_INT; "+ proximo.recuperarErrosLexico(token);
        } 
        
        return proximo.recuperarErrosLexico(token);
    }
}
