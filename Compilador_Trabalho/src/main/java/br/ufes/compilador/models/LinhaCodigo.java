
package br.ufes.compilador.models;

/**
 *
 * @author gabriel
 */
public class LinhaCodigo {
    
    String conteudo;
    int posicao;

    public LinhaCodigo(String conteudo, int posicao) {
        this.conteudo = conteudo;
        this.posicao = posicao;
    }
    
    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public int getPosicao() {
        return posicao;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }
    
    /**
     * Esse método retira do conteúdo útil da linha, tudo que seguir depois de um "//"
     * @return O próprio objeto com seu conteúdo contendo apenas a parte útil
     */
    public LinhaCodigo removerComentarioSimples(){
        int posicaoBarras = conteudo.indexOf("//");

            //Retorno válido do indexOf
        if( posicaoBarras >= 0 ){
            conteudo = conteudo.substring(0, posicaoBarras);
        }
        
        return this;
    }
}
