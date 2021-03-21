/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.compilador.chain.lexico.delimitadores.instrucoes;

import br.ufes.compilador.chain.AbstractHandler;
import br.ufes.compilador.chain.lexico.delimitadores.literais.HandlerAspas;
import br.ufes.compilador.models.Token;

/**
 *
 * @author Matheus
 */
public class HandlerPontoEVirgula extends AbstractHandler{

    public HandlerPontoEVirgula(Token token) {
        super(token);
    }

    @Override
    public void executar(Token token) {
        if (token.getSimbolo().toString().toLowerCase().compareTo(";") == 0) {
            token.setCategoria("delimitador_instrucao_ponto_e_virgula");
        } else {
            this.setProximo(new HandlerAspas(token));
        }
    }
    
}