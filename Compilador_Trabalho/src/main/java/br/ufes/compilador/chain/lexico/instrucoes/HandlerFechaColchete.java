


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.compilador.chain.lexico.instrucoes;

import br.ufes.compilador.chain.AbstractHandler;
import br.ufes.compilador.models.Token;
import br.ufes.compilador.utils.StringUtils;

/**
 *
 * @author Matheus
 */
public class HandlerFechaColchete  extends AbstractHandler{

    public HandlerFechaColchete(Token token) {
        super(token);
    }

    @Override
    public void executar(Token token) {
        if (token.getSimbolo().toString().toLowerCase().compareTo("]") == 0){
            token.setCategoria("instrucao_fecha_colchete");
        } else {
            this.setProximo(new HandlerFechaParentese(token));

        }
    }
    
    @Override
    public String recuperarErrosLexico(Token token) {
        if(StringUtils.similarity(token.getSimbolo(), "]") >= 0.8 ){
            return "Esse token é similar a: instrucao_fecha_colchete ";
        } else if(StringUtils.similarity(token.getSimbolo(), "]") > 0.5 ){
            return "Esse token é poderia ser substituido por: instrucao_fecha_colchete; "+ proximo.recuperarErrosLexico(token);
        } 
        
        return proximo.recuperarErrosLexico(token);
    }
}
