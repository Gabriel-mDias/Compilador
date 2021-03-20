/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.compilador.chain.lexico.tipo;

import br.ufes.compilador.chain.AbstractHandler;
import br.ufes.compilador.models.Token;

/**
 *
 * @author gabriel
 */
public class HandlerFloat  extends AbstractHandler{

    public HandlerFloat(Token token) {
        super(token);
    }

    @Override
    public void executar(Token token) {
        if(token.getSimbolo().equals("float")){
            token.setCategoria("Especificador_FLOAT");
        }else {
            this.setProximo(new HandlerInt(token));
        }  
    }
    
}