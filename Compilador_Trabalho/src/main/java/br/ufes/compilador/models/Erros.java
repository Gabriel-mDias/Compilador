/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.compilador.models;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gabriel
 */
public class Erros {
  
    private List<ErrorCompilacao> listErro;

    public Erros(){
        listErro = new ArrayList<>();
    }
    
    public String getMensagem(Token token) {
        for(ErrorCompilacao elemento : listErro){
            if(elemento.getToken().equals(token)){
                return elemento.getMensagemErro();
            }
        }
        
        return null;
    }
    
    public void addErro(Token token, String mensagemErro){
        listErro.add(new ErrorCompilacao(token, mensagemErro));
    }
    
}
