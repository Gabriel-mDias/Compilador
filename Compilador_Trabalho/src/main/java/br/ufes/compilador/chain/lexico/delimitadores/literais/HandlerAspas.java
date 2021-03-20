/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.compiladores.trab.chain.lexico.delimitadores.literais;

import br.ufes.compiladores.trab.chain.lexico.identificadores.HandlerIdentificador;
import br.ufes.compiladores.trab.chain.lexico.AbstractHandlerToken;
import br.ufes.compiladores.trab.model.Categoria;
import br.ufes.compiladores.trab.model.Token;

/**
 *
 * @author Matheus
 */
public class HandlerAspas extends AbstractHandlerToken {

    @Override
    public void doHandle(Token pToken) {

        if ((pToken.getSimbolo().toString().toLowerCase().compareTo("\"") == 0) ||
                (pToken.getSimbolo().toString().toLowerCase().compareTo("\'") == 0)){
            pToken.setCategoria(new Categoria("delimitador_literal_aspas"));
        } else if (this.sucessor == null) {
            this.setSucessor(new HandlerIdentificador());
            this.sucessor.handle(pToken);
        }

    }
}
