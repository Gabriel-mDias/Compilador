/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.compilador.chain.lexico.tipo;

import br.ufes.compilador.chain.AbstractHandler;
import br.ufes.compilador.chain.lexico.delimitadores.blocos.HandlerAbreChave;
import br.ufes.compilador.models.Token;
import br.ufes.compilador.utils.StringUtils;

/**
 *
 * @author gabriel
 */
public class HandlerVoid  extends AbstractHandler{

    public HandlerVoid(Token token) {
        super(token);
    }

    @Override
    public void executar(Token token) {
        if(token.getSimbolo().equals("void")){
            token.setCategoria("Especificador_VOID");
        }else {
            this.setProximo(new HandlerAbreChave(token));

        } 
    }
    
    @Override
    public String recuperarErrosLexico(Token token) {
        if(StringUtils.similarity(token.getSimbolo(), "void") >= 0.8 ){
            return "Esse token pode ser substituido por: Especificador_VOID ";
        } else if(StringUtils.similarity(token.getSimbolo(), "void") > 0.5 ){
            return "Esse token tem similaridade com: Especificador_VOID; "+ proximo.recuperarErrosLexico(token);
        } 
        
        return proximo.recuperarErrosLexico(token);
    }
}
