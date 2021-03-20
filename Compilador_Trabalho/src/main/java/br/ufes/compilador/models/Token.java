/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.compilador.models;

/**
 *
 * @author gabriel
 */
public class Token {
    
    private int id;
    private String simbolo;
    private int linha;
    private int posicaoInicio;
    private int posicaoFim;
    private String categoria;

    public Token(int id, String simbolo, int linha, int posicaoInicio, int posicaoFim, String categoria) {
        this.id = id;
        this.simbolo = simbolo;
        this.linha = linha;
        this.posicaoInicio = posicaoInicio;
        this.posicaoFim = posicaoFim;
        this.categoria = categoria;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public int getLinha() {
        return linha;
    }

    public void setLinha(int linha) {
        this.linha = linha;
    }

    public int getPosicaoInicio() {
        return posicaoInicio;
    }

    public void setPosicaoInicio(int posicaoInicio) {
        this.posicaoInicio = posicaoInicio;
    }

    public int getPosicaoFim() {
        return posicaoFim;
    }

    public void setPosicaoFim(int posicaoFim) {
        this.posicaoFim = posicaoFim;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    
}
