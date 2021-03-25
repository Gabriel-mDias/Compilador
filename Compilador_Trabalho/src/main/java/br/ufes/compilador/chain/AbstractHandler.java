/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.compilador.chain;

import br.ufes.compilador.models.Token;

/**
 *
 * @author gabriel
 */
public abstract class AbstractHandler {
    
    protected AbstractHandler proximo;
    protected static int tamanhoMaximoId = 31;

    public AbstractHandler(Token token) {
        executar(token);
    }

    public void setProximo(AbstractHandler proximo) {
        this.proximo = proximo;
    }
    
    public abstract void executar(Token token);
    
    public abstract String recuperarErrosLexico(Token token);
    
    public static void setTamanhoMaximoId(int tamanho){
        AbstractHandler.tamanhoMaximoId = tamanho;
    }
}
