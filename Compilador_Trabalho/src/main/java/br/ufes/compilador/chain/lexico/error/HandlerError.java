/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.compilador.chain.lexico.error;

import br.ufes.compilador.chain.AbstractHandler;
import br.ufes.compilador.models.Token;

/**
 *
 * @author gabriel
 */
public class HandlerError extends AbstractHandler {

    public HandlerError(Token token) {
        super(token);
    }

    /**
     * Caso não seja marcado por nenhum dos Handler da cadeia de análise léxica,
     * esse token pode ser considerado um erro nessa linguagem.
     * @param token 
     */
    @Override
    public void executar(Token token) {
        token.setCategoria("error");
    }
    
    @Override
    public String recuperarErrosLexico(Token token) {
        return "Esse token é inválido";
    }
}
