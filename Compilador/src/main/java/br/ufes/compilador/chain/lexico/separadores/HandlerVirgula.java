


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.compilador.chain.lexico.separadores;

import br.ufes.compilador.chain.AbstractHandler;
import br.ufes.compilador.models.Token;
import br.ufes.compilador.chain.lexico.define.HandlerDefine;
import br.ufes.compilador.utils.StringUtils;

/**
 *
 * @author Matheus
 */
public class HandlerVirgula  extends AbstractHandler{

    public HandlerVirgula(Token token) {
        super(token);
    }

    @Override
    public void executar(Token token) {
        if (token.getSimbolo().toString().toLowerCase().compareTo(",") == 0){
            token.setCategoria("separador_virgula");
        } else {
            this.setProximo(new HandlerDefine(token));
        }
    }
    
    @Override
    public String recuperarErrosLexico(Token token) {
        if(StringUtils.similarity(token.getSimbolo(), ",") >= 0.8 ){
            return "Esse token pode ser substituido por: separador_virgula ";
        } else if(StringUtils.similarity(token.getSimbolo(), ",") > 0.5 ){
            return "Esse token tem similaridade com: separador_virgula; "+ proximo.recuperarErrosLexico(token);
        } 
        
        return proximo.recuperarErrosLexico(token);
    }
}
