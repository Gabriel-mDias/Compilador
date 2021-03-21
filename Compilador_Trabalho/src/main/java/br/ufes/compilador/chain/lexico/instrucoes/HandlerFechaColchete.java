


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.compilador.chain.lexico.instrucoes;

import br.ufes.compilador.chain.AbstractHandler;
import br.ufes.compilador.models.Token;

/**
 *
 * @author Matheus
 */
public class HandlerFechaColchete  extends AbstractHandler{

    public HandlerFechaColchete(Token token) {
        super(token);
    }

    @Override
    public void executar(Token token) {
        if (token.getSimbolo().toString().toLowerCase().compareTo("]") == 0){
            token.setCategoria("instrucao_fecha_colchete");
        } else {
            this.setProximo(new HandlerFechaParentese(token));

        }
    }
    
}
