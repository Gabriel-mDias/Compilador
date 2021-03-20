/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.compiladores.chain.lexico.delimitadores.blocos;

import br.ufes.compiladores.chain.lexico.delimitadores.instrucoes.HandlerPontoEVirgula;
import br.ufes.compiladores.chain.lexico.AbstractHandlerToken;
import br.ufes.compiladores.model.Categoria;
import br.ufes.compiladores.model.Token;

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