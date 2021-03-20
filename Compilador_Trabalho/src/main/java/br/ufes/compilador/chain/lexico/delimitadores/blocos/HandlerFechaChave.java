/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.compilador.chain.lexico.tipo;

import br.ufes.compilador.chain.AbstractHandler;
import br.ufes.compilador.models.Token;

/**
 *
 * @author Matheus
 */
public class HandlerFechaChave  extends AbstractHandler{

    public HandlerFechaChave(Token token) {
        super(token);
    }

    @Override
    public void executar(Token token) {
        if (token.getSimbolo().toString().toLowerCase().compareTo("}") == 0) {
            token.setCategoria("delimitador_bloco_fecha_chave");
        } else {
            this.setProximo(new HandlerPontoEVirgula(token));
        }
    }
    
}

