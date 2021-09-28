/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.compilador.chain.lexico.delimitadores.instrucoes;

import br.ufes.compilador.chain.AbstractHandler;
import br.ufes.compilador.chain.lexico.delimitadores.literais.HandlerAspas;
import br.ufes.compilador.models.Token;
import br.ufes.compilador.utils.StringUtils;

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
    
    @Override
    public String recuperarErrosLexico(Token token) {
        if(StringUtils.similarity(token.getSimbolo(), ";") >= 0.8 ){
            return "Esse token pode ser substituido por: delimitador_instrucao_ponto_e_virgula ";
        } else if(StringUtils.similarity(token.getSimbolo(), ";") > 0.5 ){
            return "Esse token tem similaridade com: delimitador_instrucao_ponto_e_virgula; "+ proximo.recuperarErrosLexico(token);
        } 
        
        return proximo.recuperarErrosLexico(token);
    }
}