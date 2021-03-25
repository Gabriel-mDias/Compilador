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
public class HandlerAuto extends AbstractHandler{

    public HandlerAuto(Token token) {
        super(token);
    }

    @Override
    public void executar(Token token) {
        if(token.getSimbolo().equals("auto")){
            token.setCategoria("Especificador_AUTO");
        }else {
            this.setProximo(new HandlerStatic(token));
        }
    }

    @Override
    public String recuperarErrosLexico(Token token) {
        if(StringUtils.similarity(token.getSimbolo(), "auto") >= 0.8 ){
            return "Esse token é similar a: Especificador_AUTO ";
        } else if(StringUtils.similarity(token.getSimbolo(), "auto") > 0.5 ){
            return "Esse token é poderia ser substituido por: Especificador_AUTO; "+ proximo.recuperarErrosLexico(token);
        } 
        
        return proximo.recuperarErrosLexico(token);
    }
    
}
