/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.compilador.chain.lexico.identificador;

import br.ufes.compilador.chain.AbstractHandler;
import br.ufes.compilador.models.Token;
import java.util.regex.Pattern;

/**
 *
 * @author gabriel
 */
public class HandlerDigito extends AbstractHandler{

    public HandlerDigito(Token token) {
        super(token);
    }

   @Override
    public void executar(Token token) {
        if (token.getSimbolo().length() <= AbstractHandler.tamanhoMaximoId && Pattern.matches("[0-9]*", token.getSimbolo())){
            token.setCategoria("digito");
        } else {
            this.setProximo(new HandlerIdentificador(token));
        }
    }
    
    @Override
    public String recuperarErrosLexico(Token token) {
        return proximo.recuperarErrosLexico(token);
    }
    
}
