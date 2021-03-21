


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
public class HandlerDiferente  extends AbstractHandler{

    public HandlerDiferente(Token token) {
        super(token);
    }

    @Override
    public void executar(Token token) {
        if (token.getSimbolo().toString().toLowerCase().compareTo("!=") == 0){
            token.setCategoria("operador_comparacao_diferente");
        } else {
            this.setProximo(new HandlerIgual(token));
        }
    }
    
}
