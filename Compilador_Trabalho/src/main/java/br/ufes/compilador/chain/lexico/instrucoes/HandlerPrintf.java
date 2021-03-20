/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ufes.compiladores.trab.chain.lexico.instrucoes;

import edu.ufes.compiladores.trab.chain.lexico.AbstractHandlerToken;
import edu.ufes.compiladores.trab.model.Categoria;
import edu.ufes.compiladores.trab.model.Token;

/**
 *
 * @author Matheus
 */
public class HandlerPrintf extends AbstractHandlerToken {

    @Override
    public void doHandle(Token pToken) {

        if (pToken.getSimbolo().toString().toLowerCase().compareTo("printf") == 0) {
            pToken.setCategoria(new Categoria("instrucao_printf"));
        } else if (this.sucessor == null) {
            this.setSucessor(new HandlerIf());
            this.sucessor.handle(pToken);
        }

    }
}
