


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.compilador.chain.lexico.operadores.aritmeticos;

import br.ufes.compilador.chain.AbstractHandler;
import br.ufes.compilador.models.Token;

/**
 *
 * @author Matheus
 */
public class HandlerMultiplicacao  extends AbstractHandler{

    public HandlerMultiplicacao(Token token) {
        super(token);
    }

    @Override
    public void executar(Token token) {
        if (token.getSimbolo().toString().toLowerCase().compareTo("*") == 0){
            token.setCategoria("operador_aritmetico_multiplicacao");
        } else {
            this.setProximo(new HandlerSoma(token));
        }
    }
    
}
