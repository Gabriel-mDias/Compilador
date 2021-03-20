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
    
}
