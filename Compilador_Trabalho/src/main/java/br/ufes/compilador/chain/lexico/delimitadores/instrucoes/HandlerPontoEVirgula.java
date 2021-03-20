/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.compilador.chain.lexico.delimitadores.instrucoes;

import br.ufes.compilador.chain.lexico.AbstractHandlerToken;
import br.ufes.compilador.chain.lexico.digitos.HandlerDigito;
import br.ufes.compilador.model.Categoria;
import br.ufes.compilador.model.Token;

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
