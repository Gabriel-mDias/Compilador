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
public class HandlerDouble  extends AbstractHandler{

    public HandlerDouble(Token token) {
        super(token);
    }

    @Override
    public void executar(Token token) {
        if(token.getSimbolo().equals("double")){
            token.setCategoria("Especificador_DOUBLE");
        }else {
            this.setProximo(new HandlerFloat(token));
        }  
    }
    
    @Override
    public String recuperarErrosLexico(Token token) {
        if(StringUtils.similarity(token.getSimbolo(), "double") >= 0.8 ){
            return "Esse token é similar a: Especificador_DOUBLE ";
        } else if(StringUtils.similarity(token.getSimbolo(), "double") > 0.5 ){
            return "Esse token é poderia ser substituido por: Especificador_DOUBLE; "+ proximo.recuperarErrosLexico(token);
        } 
        
        return proximo.recuperarErrosLexico(token);
    }
}
