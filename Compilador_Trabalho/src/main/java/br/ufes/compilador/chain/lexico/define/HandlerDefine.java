


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.compilador.chain.lexico.define;

import br.ufes.compilador.chain.AbstractHandler;
import br.ufes.compilador.models.Token;
import br.ufes.compilador.utils.StringUtils;

/**
 *
 * @author Matheus
 */
public class HandlerDefine extends AbstractHandler{

    public HandlerDefine(Token token) {
        super(token);
    }

    @Override
    public void executar(Token token) {
        if (token.getSimbolo().toString().toLowerCase().compareTo("#define") == 0){
            token.setCategoria("define");
        } else {
            this.setProximo(null);
        }
    }
    
    @Override
    public String recuperarErrosLexico(Token token) {
        if(StringUtils.similarity(token.getSimbolo(), "#define") >= 0.8 ){
            return "Esse token é similar a: define ";
        } else if(StringUtils.similarity(token.getSimbolo(), "#define") > 0.5 ){
            return "Esse token é poderia ser substituido por: define; "+ proximo.recuperarErrosLexico(token);
        } 
        
        //return proximo.recuperarErrosLexico(token);
        return "Erro qualquer";
    }
}
