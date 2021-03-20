/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.compiladores.chain.lexico.delimitadores.blocos;

import br.ufes.compiladores.chain.lexico.AbstractHandlerToken;
import br.ufes.compiladores.model.Categoria;
import br.ufes.compiladores.model.Token;

/**
 *
 * @author Matheus
 */
public class HandlerAbreChave extends AbstractHandlerToken {

    @Override
    public void doHandle(Token pToken) {

        if (pToken.getSimbolo().toString().toLowerCase().compareTo("{") == 0) {
            pToken.setCategoria(new Categoria("delimitador_bloco_abre_chave"));
        } else if (this.sucessor == null) {
            this.setSucessor(new HandlerFechaChave());
            this.sucessor.handle(pToken);
        }

    }
}
