/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.compilador.chain.lexico.especificadores;

import br.ufes.compilador.chain.AbstractHandler;
import br.ufes.compilador.models.Token;
import br.ufes.compilador.utils.StringUtils;

/**
 *
 * @author gabriel
 */
public class HandlerExtern extends AbstractHandler{

    public HandlerExtern(Token token) {
        super(token);
    }

    @Override
    public void executar(Token token) {
        if(token.getSimbolo().equals("extern")){
            token.setCategoria("Especificador_EXTERN");
        }else {
            this.setProximo(new HandlerConst(token));
        }
    }
    
    @Override
    public String recuperarErrosLexico(Token token) {
        if(StringUtils.similarity(token.getSimbolo(), "extern") >= 0.8 ){
            return "Esse token é similar a: Especificador_EXTERN ";
        } else if(StringUtils.similarity(token.getSimbolo(), "extern") > 0.5 ){
            return "Esse token é poderia ser substituido por: Especificador_EXTERN; "+ proximo.recuperarErrosLexico(token);
        } 
        
        return proximo.recuperarErrosLexico(token);
    }
}
