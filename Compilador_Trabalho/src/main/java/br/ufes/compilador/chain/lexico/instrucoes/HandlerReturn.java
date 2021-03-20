/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.compiladores.chain.lexico.instrucoes;

import br.ufes.compiladores.chain.lexico.AbstractHandlerToken;
import br.ufes.compiladores.model.Categoria;
import br.ufes.compiladores.model.Token;

/**
 *
 * @author Matheus
 */
public class HandlerReturn extends AbstractHandlerToken {

    @Override
    public void doHandle(Token pToken) {

        if (pToken.getSimbolo().toString().toLowerCase().compareTo("return") == 0) {
            pToken.setCategoria(new Categoria("instrucao_return"));
        } else if (this.sucessor == null) {
            this.setSucessor(new HandlerScanf());
            this.sucessor.handle(pToken);
        }

    }
}
