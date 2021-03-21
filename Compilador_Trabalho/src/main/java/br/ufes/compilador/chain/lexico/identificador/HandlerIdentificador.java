/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.compilador.chain.lexico.identificador;

import br.ufes.compilador.chain.AbstractHandler;
import br.ufes.compilador.chain.lexico.error.HandlerError;
import br.ufes.compilador.models.Token;
import java.util.regex.Pattern;

/**
 *
 * @author gabriel
 */
public class HandlerIdentificador extends AbstractHandler{
    
    public HandlerIdentificador(Token token) {
        super(token);
    }

   @Override
    public void executar(Token token) {
        if (token.getSimbolo().length() <= AbstractHandler.tamanhoMaximoId && Pattern.matches("[[A-Za-z]+|[_]+][A-Za-z0-9]*", token.getSimbolo())){
            token.setCategoria("identificador");
        } else {
            this.setProximo(new HandlerError(token));
        }
    }
}
