/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.compiladores.trab.chain.lexico.instrucoes;

import br.ufes.compiladores.trab.chain.lexico.AbstractHandlerToken;
import br.ufes.compiladores.trab.model.Categoria;
import br.ufes.compiladores.trab.model.Token;

/**
 *
 * @author Matheus
 */
public class HandlerElse extends AbstractHandlerToken {

    @Override
    public void doHandle(Token pToken) {

        if (pToken.getSimbolo().toString().toLowerCase().compareTo("else") == 0) {
            pToken.setCategoria(new Categoria("instrucao_else"));
        } else if (this.sucessor == null) {
            this.setSucessor(new HandlerAbreParentese());
            this.sucessor.handle(pToken);
        }

    }
}
