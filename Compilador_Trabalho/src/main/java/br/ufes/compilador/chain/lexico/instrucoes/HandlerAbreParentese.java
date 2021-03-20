/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.compilador.chain.lexico.instrucoes;

import br.ufes.compilador.chain.lexico.AbstractHandlerToken;
import br.ufes.compilador.model.Categoria;
import br.ufes.compilador.model.Token;

/**
 *
 * @author Matheus
 */
public class HandlerAbreParentese extends AbstractHandlerToken {

    @Override
    public void doHandle(Token pToken) {

        if (pToken.getSimbolo().toString().toLowerCase().compareTo("(") == 0) {
            pToken.setCategoria(new Categoria("instrucao_abre_parentese"));
        } else if (this.sucessor == null) {
            this.setSucessor(new HandlerFechaParentese());
            this.sucessor.handle(pToken);
        }

    }
}
