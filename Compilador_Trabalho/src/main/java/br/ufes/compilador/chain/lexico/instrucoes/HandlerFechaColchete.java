/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.compilador.chain.lexico.instrucoes;

import br.ufes.compilador.chain.lexico.AbstractHandlerToken;
import br.ufes.compilador.chain.lexico.delimitadores.literais.HandlerAspas;
import br.ufes.compilador.model.Categoria;
import br.ufes.compilador.model.Token;

/**
 *
 * @author Matheus
 */
public class HandlerFechaColchete extends AbstractHandlerToken {

    @Override
    public void doHandle(Token pToken) {

        if (pToken.getSimbolo().toString().toLowerCase().compareTo("]") == 0) {
            pToken.setCategoria(new Categoria("instrucao_fecha_colchete"));
        } else if (this.sucessor == null) {
            this.setSucessor(new HandlerAspas());
            this.sucessor.handle(pToken);
        }

    }
}
