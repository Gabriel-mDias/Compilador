


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.compilador.chain.lexico.operadores.atribuicoes;

import br.ufes.compilador.chain.AbstractHandler;
import br.ufes.compilador.models.Token;
import br.ufes.compilador.utils.StringUtils;

/**
 *
 * @author Matheus
 */
public class HandlerMaisIgual  extends AbstractHandler{

    public HandlerMaisIgual(Token token) {
        super(token);
    }

    @Override
    public void executar(Token token) {
        if (token.getSimbolo().toString().toLowerCase().compareTo("+=") == 0){
            token.setCategoria("operador_atribuicao_mais_igual");
        } else {
            this.setProximo(new HandlerMenosIgual(token));
        }
    }
    
    @Override
    public String recuperarErrosLexico(Token token) {
        if(StringUtils.similarity(token.getSimbolo(), "+=") >= 0.8 ){
            return "Esse token pode ser substituido por: operador_atribuicao_mais_igual ";
        } else if(StringUtils.similarity(token.getSimbolo(), "+=") > 0.5 ){
            return "Esse token tem similaridade com: operador_atribuicao_mais_igual; "+ proximo.recuperarErrosLexico(token);
        } 
        
        return proximo.recuperarErrosLexico(token);
    }
}
