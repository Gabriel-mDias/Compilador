


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.compilador.chain.lexico.instrucoes;

import br.ufes.compilador.chain.AbstractHandler;
import br.ufes.compilador.models.Token;
import br.ufes.compilador.chain.lexico.separadores.HandlerVirgula;
import br.ufes.compilador.utils.StringUtils;

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
    
    @Override
    public String recuperarErrosLexico(Token token) {
        if(StringUtils.similarity(token.getSimbolo(), "scanf") >= 0.8 ){
            return "Esse token pode ser substituido por: instrucao_scanf ";
        } else if(StringUtils.similarity(token.getSimbolo(), "scanf") > 0.5 ){
            return "Esse token tem similaridade com: instrucao_scanf; "+ proximo.recuperarErrosLexico(token);
        } 
        
        return proximo.recuperarErrosLexico(token);
    }
}
