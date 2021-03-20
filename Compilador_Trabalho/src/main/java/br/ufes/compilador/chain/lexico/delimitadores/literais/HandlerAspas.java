/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ufes.compiladores.trab.chain.lexico.delimitadores.literais;

import edu.ufes.compiladores.trab.chain.lexico.identificadores.HandlerIdentificador;
import edu.ufes.compiladores.trab.chain.lexico.AbstractHandlerToken;
import edu.ufes.compiladores.trab.model.Categoria;
import edu.ufes.compiladores.trab.model.Token;

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
