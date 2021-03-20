/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.compiladores.trab.chain.lexico.delimitadores.instrucoes;

import br.ufes.compiladores.trab.chain.lexico.AbstractHandlerToken;
import br.ufes.compiladores.trab.chain.lexico.digitos.HandlerDigito;
import br.ufes.compiladores.trab.model.Categoria;
import br.ufes.compiladores.trab.model.Token;

/**
 *
 * @author Matheus
 */
public class HandlerPontoEVirgula extends AbstractHandlerToken {

    @Override
    public void doHandle(Token pToken) {

        if (pToken.getSimbolo().toString().toLowerCase().compareTo(";") == 0) {
            pToken.setCategoria(new Categoria("delimitador_instrucao_ponto_e_virgula"));
        } else if (this.sucessor == null) {
            this.setSucessor(new HandlerDigito());
            this.sucessor.handle(pToken);
        }

    }
}
