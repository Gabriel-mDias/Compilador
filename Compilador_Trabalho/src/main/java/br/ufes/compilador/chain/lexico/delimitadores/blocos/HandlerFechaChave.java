/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ufes.compiladores.trab.chain.lexico.delimitadores.blocos;

import edu.ufes.compiladores.trab.chain.lexico.delimitadores.instrucoes.HandlerPontoEVirgula;
import edu.ufes.compiladores.trab.chain.lexico.AbstractHandlerToken;
import edu.ufes.compiladores.trab.model.Categoria;
import edu.ufes.compiladores.trab.model.Token;

/**
 *
 * @author Matheus
 */
public class HandlerFechaChave extends AbstractHandlerToken {

    @Override
    public void doHandle(Token pToken) {

        if (pToken.getSimbolo().toString().toLowerCase().compareTo("}") == 0) {
            pToken.setCategoria(new Categoria("delimitador_bloco_fecha_chave"));
        } else if (this.sucessor == null) {
            this.setSucessor(new HandlerPontoEVirgula());
            this.sucessor.handle(pToken);
        }

    }
}
