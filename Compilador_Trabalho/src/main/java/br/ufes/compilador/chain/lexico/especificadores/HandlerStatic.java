/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.compilador.chain.lexico.especificadores;

import br.ufes.compilador.chain.AbstractHandler;
import br.ufes.compilador.models.Token;

/**
 *
 * @author gabriel
 */
public class HandlerStatic extends AbstractHandler {

    public HandlerStatic(Token token) {
        super(token);
    }

    @Override
    public void executar(Token token) {
        if(token.getSimbolo().equals("static")){
            token.setCategoria("Especificador_STATIC");
        }else {
            this.setProximo(new HandlerExtern(token));
        }
    }
    
}
