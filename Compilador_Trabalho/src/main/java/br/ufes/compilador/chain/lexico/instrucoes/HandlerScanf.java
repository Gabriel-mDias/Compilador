


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.compilador.chain.lexico.instrucoes;

import br.ufes.compilador.chain.AbstractHandler;
import br.ufes.compilador.models.Token;
import br.ufes.compilador.chain.lexico.separadores.HandlerVirgula;

/**
 *
 * @author Matheus
 */
public class HandlerScanf  extends AbstractHandler{

    public HandlerScanf(Token token) {
        super(token);
    }

    @Override
    public void executar(Token token) {
        if (token.getSimbolo().toString().toLowerCase().compareTo("scanf") == 0){
            token.setCategoria("instrucao_scanf");
        } else {
            this.setProximo(new HandlerVirgula(token));
        }
    }
    
}
