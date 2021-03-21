


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.compilador.chain.lexico.operadores.comparacoes;

import br.ufes.compilador.chain.AbstractHandler;
import br.ufes.compilador.models.Token;

/**
 *
 * @author Matheus
 */
public class HandlerMenor  extends AbstractHandler{

    public HandlerMenor(Token token) {
        super(token);
    }

    @Override
    public void executar(Token token) {
        if (token.getSimbolo().toString().toLowerCase().compareTo("<") == 0){
            token.setCategoria("operador_comparacao_menor");
        } else {
            this.setProximo(new HandlerMenorIgual(token));
        }
    }
    
}
