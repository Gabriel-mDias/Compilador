/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.compilador.chain.sintatico;

import br.ufes.compilador.models.Erros;
import br.ufes.compilador.models.Token;
import br.ufes.compilador.view.JanelaPrincipalView;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author gabriel
 */
public class AnalisadorSintatico {
    
    private JanelaPrincipalView view;
    private List<Token> tokensOriginais;    //Tokens após a análise léxica. São guardados já que a análise sintática modificará a lista
    private List<Token> tokens;             //Tokens usados na análise sintática 
    private JTree arvoreSintatica;
    private ArrayList<DefaultMutableTreeNode> listaNo;
    private Erros handlerErros;
    
    //Variáveis auxiliares
    private Token tokenAnalisado;
    private ArrayList<Token> pilhaDeChaves;

    public AnalisadorSintatico(JanelaPrincipalView view) {
        this.view = view;
        listaNo = new ArrayList<>();
        
    }
    
    public void analiseSintatica(List<Token> tokens, Erros erros){
        this.tokens = new ArrayList<Token>();
        this.tokens.addAll(tokens);
        this.tokensOriginais = tokens;
        this.handlerErros = erros;
        
        this.createArvoreSintatica();
        pilhaDeChaves = new ArrayList<>();
    }
    
    
    /*
        Método responsável por gerar uma nova JTree, que irá exibir os resultados da análise sintática
    */
    private void createArvoreSintatica() {
        
        DefaultMutableTreeNode no = new DefaultMutableTreeNode("Programa");
        DefaultTreeModel model = new DefaultTreeModel(no);
        
        LookAndFeel previousLF = UIManager.getLookAndFeel();
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            arvoreSintatica = new JTree(model);
            UIManager.setLookAndFeel(previousLF);
            arvoreSintatica.putClientProperty("JTree.lineStyle", "Angled");
            
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(this.view,"Error: "+e.getMessage(), "Erro ao criar a árvore sintática", JOptionPane.ERROR_MESSAGE);
        }

        // O primeiro nó
        listaNo.add(no);

        JScrollPane scrollTree = new JScrollPane(arvoreSintatica);
        scrollTree.setViewportView(arvoreSintatica);

        //Se não existe a aba, crie, caso contrário, sobrescreva
        if(this.view.getTabPanelResultados().getTabCount() < 1){
            this.view.getTabPanelResultados().add("Árvore de Análise Sintática", scrollTree);
        }else{
            this.view.getTabPanelResultados().add(scrollTree,1);
        }
        

        // Muda o ícone dos nós
        /*ImageIcon icon1 = new ImageIcon("src/edu/ufes/compiladores/trab/imagens/no.png");
        DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
        renderer.setOpenIcon(icon1);
        renderer.setClosedIcon(icon1);
        renderer.setLeafIcon(icon1);
        arvoreSintatica.setCellRenderer(renderer);*/

        // Exibe seta mostrando que há como contrair ou expandir um nó
        arvoreSintatica.setShowsRootHandles(true);
    }
    
    
    private DefaultMutableTreeNode inserirNo(DefaultMutableTreeNode pai, String filho) {
        DefaultMutableTreeNode novo = new DefaultMutableTreeNode(filho);
        pai.add(novo);
        listaNo.add(novo);
        return novo;
    }
    
    //Esses métodos são específicos para cada tipos de Token diferente
    private boolean programa() throws Exception {
        boolean retorno = false;

        if (!this.tokens.isEmpty()) {
            if (especificador()) {
                if (tipo()) {
                    if (id()) {
                        continuaID();
                        programa();
                    } else {
                        this.msgErro("<identificador>");
                    }
                } else {
                    this.msgErro("<tipo>");
                }
                retorno = true;
            } else if (tipo()) {
                if (id()) {
                    continuaID();
                    programa();
                } else {
                    this.msgErro("<identificador>");
                }
                retorno = true;
            } else if (define()) {
                programa();
                retorno = true;
            }
        }

        return retorno;
    }

    private void continuaID() throws Exception {
        if (!this.tokens.isEmpty()) {
            inserirNo(listaNo.get(listaNo.size() - 1), "<continuaID>");
            if (abreParentese()) {
                parametros();
                if (fechaParentese()) {
                    bloco();
                } else {
                    msgErro("<)>");
                }
            } else {
                listaID();
            }
            listaNo.remove(listaNo.size() - 1);
        }
    }

    private void listaID() throws Exception {
        if (!this.tokens.isEmpty()) {
            inserirNo(listaNo.get(listaNo.size() - 1), "<listaID>");
            if (abreColchete()) {
                if (num()) {
                    if (fechaColchete()) {
                        continuaListaID();
                    } else {
                        msgErro("<]>");
                    }
                } else {
                    msgErro("<num>");
                }
            } else {
                continuaListaID();
            }
            listaNo.remove(listaNo.size() - 1);
        }
    }

    private void continuaListaID() throws Exception {
        if (!this.tokens.isEmpty()) {
            inserirNo(listaNo.get(listaNo.size() - 1), "<continuaListaID>");
            if (separadorVirgula()) {
                if (id()) {
                    listaID();
                } else {
                    this.msgErro("<identificador>");
                }
            } else if (!delimitadorInstrucaoPontoEVirgula()) {
                atribuicaoID();
            }
            listaNo.remove(listaNo.size() - 1);
        }
    }

    private void atribuicaoID() throws Exception {
        if (!this.tokens.isEmpty()) {
            inserirNo(listaNo.get(listaNo.size() - 1), "<atribuicaoID>");
            if (operadorDeAtribuicao()) {
                atribuicao();
                continuaAtribuicaoID();
            } else {
                msgErro("<operadorDeAtribuicao> ou <;>");
            }
            listaNo.remove(listaNo.size() - 1);
        }
    }

    private void continuaAtribuicaoID() throws Exception {
        if (!this.tokens.isEmpty()) {
            inserirNo(listaNo.get(listaNo.size() - 1), "<continuaAtribuicaoID>");
            if (!delimitadorInstrucaoPontoEVirgula()) {
                if (separadorVirgula()) {
                    if (id()) {
                        listaID();
                    } else {
                        this.msgErro("<identificador>");
                    }
                } else {
                    this.msgErro("<,> ou <;>");
                }
            }
            listaNo.remove(listaNo.size() - 1);
        }
    }

    private boolean parametros() throws Exception {
        boolean retorno = false;
        if (!this.tokens.isEmpty()) {
            inserirNo(listaNo.get(listaNo.size() - 1), "<parametros>");
            if (tipo()) {
                if (id()) {
                    continuaParametros();
                    retorno = true;
                } else {
                    msgErro("<identificador>");
                }
            }
            listaNo.remove(listaNo.size() - 1);
        }
        return retorno;
    }

    private void continuaParametros() throws Exception {
        if (!this.tokens.isEmpty()) {
            inserirNo(listaNo.get(listaNo.size() - 1), "<continuaParametros>");
            if (abreColchete()) {
                if (num()) {
                    if (fechaColchete()) {
                        listaParametros();
                    } else {
                        msgErro("<]>");
                    }
                } else {
                    msgErro("<num>");
                }
            } else {
                listaParametros();
            }
            listaNo.remove(listaNo.size() - 1);
        }
    }

    private void listaParametros() throws Exception {
        if (!this.tokens.isEmpty()) {
            inserirNo(listaNo.get(listaNo.size() - 1), "<listaParametros>");
            if (separadorVirgula()) {
                if (!parametros()) {
                    this.msgErro("<tipo>");
                }
            }
            listaNo.remove(listaNo.size() - 1);
        }
    }

    private boolean bloco() throws Exception {
        boolean retorno = false;
        if (!this.tokens.isEmpty()) {
            inserirNo(listaNo.get(listaNo.size() - 1), "<bloco>");
            if (abreChave()) {
                retorno = true;
                conteudoDeBloco();
                if (!fechaChave()) {
                    this.msgErro("<}>");
                }
            } else if (!delimitadorInstrucaoPontoEVirgula()) {
                this.msgErro("<{> ou <;>");
            }
            listaNo.remove(listaNo.size() - 1);
        }
        return retorno;
    }

    private void conteudoDeBloco() throws Exception {
        if (!this.tokens.isEmpty()) {
            inserirNo(listaNo.get(listaNo.size() - 1), "<conteudoDeBloco>");
            if (instrucao() || programa()) {
                conteudoDeBloco();
            }
            listaNo.remove(listaNo.size() - 1);
        }
    }

    private boolean instrucao() throws Exception {
        boolean retorno = false;
        if (!this.tokens.isEmpty()) {
            inserirNo(listaNo.get(listaNo.size() - 1), "<instrucao>");
            // <instrucoes>
            if (id()) {
                continuaInstrucaoID();
                if (!delimitadorInstrucaoPontoEVirgula()) {
                    this.msgErro("<;>");
                }
                retorno = true;
            } else if (instrucaoReturn()) {
                continuaInstrucoesEspecificas();
                if (!delimitadorInstrucaoPontoEVirgula()) {
                    msgErro("<;>");
                }
                retorno = true;
            } else if (instrucaoPrintf()) {
                if (abreParentese()) {
                    continuaInstrucoesEspecificas();
                    if (fechaParentese()) {
                        if (!delimitadorInstrucaoPontoEVirgula()) {
                            msgErro("<;>");
                        }
                    } else {
                        msgErro("<)>");
                    }
                } else {
                    msgErro("<(>");
                }
                retorno = true;
            } else if (instrucaoScanf()) {
                if (abreParentese()) {
                    if (id()) {
                        if (fechaParentese()) {
                            if (!delimitadorInstrucaoPontoEVirgula()) {
                                msgErro("<;>");
                            }
                        } else {
                            msgErro("<)>");
                        }
                    } else {
                        msgErro("<identificador>");
                    }
                } else {
                    msgErro("<(>");
                }
                retorno = true;
            } else if (instrucaoBreak()) {
                if (!delimitadorInstrucaoPontoEVirgula()) {
                    msgErro("<;>");
                }
                retorno = true;
            } else if (instrucaoIF()) {
                if (abreParentese()) {
                    parametrosIF();
                    if (fechaParentese()) {
                        continuaInstrucaoIF();
                    } else {
                        msgErro("<)>");
                    }
                } else {
                    msgErro("<(>");
                }
                retorno = true;
            }
            listaNo.remove(listaNo.size() - 1);
        }

        return retorno;
    }

    private void continuaInstrucaoID() throws Exception {
        if (!this.tokens.isEmpty()) {
            inserirNo(listaNo.get(listaNo.size() - 1), "<continuaInstrucaoID>");
            if (abreColchete()) {
                if (num()) {
                    if (fechaColchete()) {
                        if (operadorDeAtribuicao()) {
                            atribuicao();
                        } else {
                            this.msgErro("<operadorAtribuicao>");
                        }
                    } else {
                        this.msgErro("<]>");
                    }
                } else {
                    this.msgErro("<num>");
                }
            } else if (abreParentese()) {
                parametrosInstrucaoID();
                if (!fechaParentese()) {
                    this.msgErro(")");
                }
            } else if (operadorDeAtribuicao()) {
                atribuicao();
            } else {
                this.msgErro("<operadorAtribuicao>, <[> ou <(>");
            }
            listaNo.remove(listaNo.size() - 1);
        }
    }

    private void parametrosInstrucaoID() throws Exception {
        if (!this.tokens.isEmpty()) {
            inserirNo(listaNo.get(listaNo.size() - 1), "<parametrosInstrucaoID>");
            if (abreParentese()) {
                parametrosInstrucaoID();
                if (!fechaParentese()) {
                    this.msgErro(")");
                }
            } else if (!expressaoUnaria()) {
                if (operando()) {
                    expressaoBinaria();
                }
            }
            listaNo.remove(listaNo.size() - 1);
        }
    }

    private void continuaInstrucoesEspecificas() throws Exception {
        if (!this.tokens.isEmpty()) {
            inserirNo(listaNo.get(listaNo.size() - 1), "<continuaInstrucoesEspecificas>");
            if (abreParentese()) {
                continuaInstrucoesEspecificas();
                if (!fechaParentese()) {
                    this.msgErro(")");
                }
            } else if (!expressaoUnaria()) {
                if (operando()) {
                    expressaoBinaria();
                } else {
                    this.msgErro("<expressaoUnaria>, <operador> ou <(>");
                }
            }
            listaNo.remove(listaNo.size() - 1);
        }
    }

    private void parametrosIF() throws Exception {
        if (!this.tokens.isEmpty()) {
            inserirNo(listaNo.get(listaNo.size() - 1), "<parametrosIF>");
            if (abreParentese()) {
                parametrosIF();
                if (!fechaParentese()) {
                    this.msgErro("<)>");
                }
            } else if (!expressaoUnaria()) {
                if (operando()) {
                    expressaoBinaria();
                } else {
                    this.msgErro("<expressaoUnaria>, <operador> ou <(>");
                }
            }
            listaNo.remove(listaNo.size() - 1);
        }
    }

    private void continuaInstrucaoIF() throws Exception {
        if (!this.tokens.isEmpty()) {
            inserirNo(listaNo.get(listaNo.size() - 1), "<continuaInstrucaoIF>");
            if (!programa()) {
                if (!instrucao()) {
                    if (bloco()) {
                        continuaBlocoInstrucaoIF();
                    } else {
                        this.msgErro("<programa>, <bloco> ou <instrucao>");
                    }
                } else {
                    continuaBlocoInstrucaoIF();
                }
            } else {
                continuaBlocoInstrucaoIF();
            }
            listaNo.remove(listaNo.size() - 1);
        }
    }

    private void continuaBlocoInstrucaoIF() throws Exception {
        if (!this.tokens.isEmpty()) {
            inserirNo(listaNo.get(listaNo.size() - 1), "<continuaBlocoInstrucaoIF>");
            if (instrucaoELSE()) {
                continuaInstrucaoELSE();
            }
            listaNo.remove(listaNo.size() - 1);
        }
    }

    private void continuaInstrucaoELSE() throws Exception {
        if (!this.tokens.isEmpty()) {
            inserirNo(listaNo.get(listaNo.size() - 1), "<continuaInstrucaoELSE>");
            if (!instrucao()) {
                if (!programa()) {
                    if (!bloco()) {
                        this.msgErro("<programa>, <bloco>, <instrucao>");
                    }
                }
            }
            listaNo.remove(listaNo.size() - 1);
        }
    }

    private void atribuicao() throws Exception {
        if (!this.tokens.isEmpty()) {
            inserirNo(listaNo.get(listaNo.size() - 1), "<atribuicao>");
            if (abreParentese()) {
                atribuicao();
                if (!fechaParentese()) {
                    this.msgErro("<)>");
                }
            } else {
                if (!expressaoUnaria()) {
                    if (operando()) {
                        expressaoBinaria();
                    } else {
                        this.msgErro("<expressaoUnaria>, <operador> ou <(>");
                    }
                }
            }
            listaNo.remove(listaNo.size() - 1);
        }
    }

    private void expressaoBinaria() throws Exception {
        if (!this.tokens.isEmpty()) {
            inserirNo(listaNo.get(listaNo.size() - 1), "<expressaoBinaria>");
            if (operadorExpressaoBinaria()) {
                operandoExpressaoBinaria();
                expressaoBinaria();
            }
            listaNo.remove(listaNo.size() - 1);
        }
    }

    private void operandoExpressaoBinaria() throws Exception {
        if (!this.tokens.isEmpty()) {
            inserirNo(listaNo.get(listaNo.size() - 1), "<operandoExpressaoBinaria>");
            if (!operando()) {
                if (!expressaoUnaria()) {
                    this.msgErro("<operando> ou <expressaoUnaria>");
                }
            }
            listaNo.remove(listaNo.size() - 1);
        }
    }

    private boolean expressaoUnaria() throws Exception {
        boolean retorno = false;
        if (!this.tokens.isEmpty()) {
            inserirNo(listaNo.get(listaNo.size() - 1), "<expressaoUnaria>");
            if ((operadorAritmeticoDeSoma()) || (operadorAritmeticoDeSubtracao())) {
                retorno = true;
                continuaExpressaoUnaria();
            }
            listaNo.remove(listaNo.size() - 1);
        }
        return retorno;
    }

    private void continuaExpressaoUnaria() throws Exception {
        if (!this.tokens.isEmpty()) {
            inserirNo(listaNo.get(listaNo.size() - 1), "<continuaExpressaoUnaria>");
            if (abreParentese()) {
                continuaExpressaoUnariaParenteses();
                if (!fechaParentese()) {
                    this.msgErro(")");
                }
            } else {
                operando();
            }
            listaNo.remove(listaNo.size() - 1);
        }
    }

    private void continuaExpressaoUnariaParenteses() throws Exception {
        if (!this.tokens.isEmpty()) {
            inserirNo(listaNo.get(listaNo.size() - 1), "<continuaExpressaoUnariaParenteses>");
            if (!expressaoUnaria()) {
                if (operando()) {
                    expressaoBinaria();
                } else {
                    this.msgErro("<expressaoUnaria> ou <operando>");
                }
            }
            listaNo.remove(listaNo.size() - 1);
        }
    }

    private boolean operando() throws Exception {
        boolean retorno = false;
        if (!this.tokens.isEmpty()) {
            inserirNo(listaNo.get(listaNo.size() - 1), "<operando>");
            if (num()) {
                retorno = true;
            } else if (literal()) {
                retorno = true;
            } else {
                if (id()) {
                    retorno = true;
                    continuaOperandoID();
                }
            }
            listaNo.remove(listaNo.size() - 1);
        }

        return retorno;
    }

    private void continuaOperandoID() throws Exception {
        if (!this.tokens.isEmpty()) {
            inserirNo(listaNo.get(listaNo.size() - 1), "<continuaOperandoID>");
            if (abreColchete()) {
                if (num()) {
                    if (!fechaColchete()) {
                        this.msgErro("<]>");
                    }
                } else {
                    this.msgErro("<num>");
                }
            } else if (abreParentese()) {
                listaDeExpressao();
                if (!fechaParentese()) {
                    this.msgErro("<)>");
                }
            }
            listaNo.remove(listaNo.size() - 1);
        }
    }

    private boolean listaDeExpressao() throws Exception {
        boolean retorno = false;
        inserirNo(listaNo.get(listaNo.size() - 1), "<listaDeExpresao>");
        if (abreParentese()) {
            listaDeExpressao();
            retorno = true;
            if (!fechaParentese()) {
                this.msgErro("<)>");
            }
        } else if (expressaoUnaria()) {
            continuaListaDeExpressao();
            retorno = true;
        } else if (operando()) {
            expressaoBinaria();
            continuaListaDeExpressao();
            retorno = true;
        } else {
            //this.msgErro("<expressaoUnaria>, <operando> AQUI");
        }
        listaNo.remove(listaNo.size() - 1);
        return retorno;
    }

    private void continuaListaDeExpressao() throws Exception {
        if (!this.tokens.isEmpty()) {
            inserirNo(listaNo.get(listaNo.size() - 1), "<continuaListaDeExpressao>");
            if (separadorVirgula()) {
                if (!listaDeExpressao()) {
                    this.msgErro("<expressaoUnaria>, <operando>");
                }
            }
            listaNo.remove(listaNo.size() - 1);
        }
    }

    private boolean define() throws Exception {
        boolean retorno = false;

        if (!this.tokens.isEmpty()) {
            tokenAnalisado = this.tokens.get(0);
            switch (tokenAnalisado.getCategoria()) {
                case "define":
                    this.tokens.remove(0);
                    retorno = true;
                    inserirNo(listaNo.get(listaNo.size() - 1), "<define>");
                    inserirNo(listaNo.get(listaNo.size() - 1), "#define");
                    listaNo.remove(listaNo.size() - 1);
                    listaNo.remove(listaNo.size() - 1);
                    if (id()) {
                        if (num()) {
                            if (!this.tokens.isEmpty()) {
                                if (!CRLF(tokenAnalisado.getLinha().getPosicao())) {
                                    msgErro("<CRLF>");
                                }
                            }
                        } else {
                            msgErro("<num>");
                        }
                    } else {
                        msgErro("<identificador>");
                    }
                    break;
            }
        }

        return retorno;
    }

    private boolean operadorExpressaoBinaria() {
        boolean retorno = false;

        if (!this.tokens.isEmpty()) {
            tokenAnalisado = this.tokens.get(0);

            switch (tokenAnalisado.getCategoria()) {
                case "operador_aritmetico_soma":
                case "operador_aritmetico_subtracao":
                case "operador_aritmetico_multiplicacao":
                case "operador_aritmetico_divisao":
                case "operador_comparacao_igual":
                case "operador_comparacao_diferente":
                case "operador_comparacao_menor":
                case "operador_comparacao_maior":
                case "operador_comparacao_menor_igual":
                case "operador_comparacao_maior_igual":
                case "operador_logico_or":
                case "operador_logico_and":
                    this.tokens.remove(0);
                    retorno = true;
                    inserirNo(listaNo.get(listaNo.size() - 1), "<operadorExpressaoBinaria>");
                    inserirNo(listaNo.get(listaNo.size() - 1), tokenAnalisado.getSimbolo());
                    listaNo.remove(listaNo.size() - 1);
                    listaNo.remove(listaNo.size() - 1);
                    break;
            }
        }

        return retorno;
    }

    private boolean operadorAritmeticoDeSoma() {
        boolean retorno = false;

        if (!this.tokens.isEmpty()) {
            tokenAnalisado = this.tokens.get(0);

            switch (tokenAnalisado.getCategoria()) {
                case "operador_aritmetico_soma":
                    this.tokens.remove(0);
                    retorno = true;
                    inserirNo(listaNo.get(listaNo.size() - 1), "<operadorAritmeticoDeSoma>");
                    inserirNo(listaNo.get(listaNo.size() - 1), tokenAnalisado.getSimbolo());
                    listaNo.remove(listaNo.size() - 1);
                    listaNo.remove(listaNo.size() - 1);
                    break;
            }
        }

        return retorno;
    }

    private boolean operadorAritmeticoDeSubtracao() {
        boolean retorno = false;

        if (!this.tokens.isEmpty()) {
            tokenAnalisado = this.tokens.get(0);

            switch (tokenAnalisado.getCategoria()) {
                case "operador_aritmetico_subtracao":
                    this.tokens.remove(0);
                    retorno = true;
                    inserirNo(listaNo.get(listaNo.size() - 1), "<operadorAritmeticoDeSubtracao>");
                    inserirNo(listaNo.get(listaNo.size() - 1), tokenAnalisado.getSimbolo());
                    listaNo.remove(listaNo.size() - 1);
                    listaNo.remove(listaNo.size() - 1);
                    break;
            }
        }

        return retorno;
    }

    private boolean operadorDeAtribuicao() {
        boolean retorno = false;

        if (!this.tokens.isEmpty()) {
            tokenAnalisado = this.tokens.get(0);

            switch (tokenAnalisado.getCategoria()) {
                case "operador_atribuicao_igual":
                case "operador_atribuicao_multiplicacao_igual":
                case "operador_atribuicao_divisao_igual":
                case "operador_atribuicao_percente_igual":
                case "operador_atribuicao_mais_igual":
                case "operador_atribuicao_menos_igual":
                    this.tokens.remove(0);
                    retorno = true;
                    inserirNo(listaNo.get(listaNo.size() - 1), "<operadorDeAtribuicao>");
                    inserirNo(listaNo.get(listaNo.size() - 1), tokenAnalisado.getSimbolo());
                    listaNo.remove(listaNo.size() - 1);
                    listaNo.remove(listaNo.size() - 1);
                    break;
            }
        }

        return retorno;
    }

    private boolean literal() {
        boolean retorno = false;
        Token temp;

        if (!this.tokens.isEmpty()) {
            tokenAnalisado = this.tokens.get(0);

            if (tokenAnalisado.getCategoria().equals("delimitador_literal_aspas")) {
                this.tokens.remove(0);
                tokenAnalisado = this.tokens.get(0);
                if (tokenAnalisado.getCategoria().equals("identificador")) {
                    this.tokens.remove(0);
                    temp = tokenAnalisado;
                    tokenAnalisado = this.tokens.get(0);
                    if (tokenAnalisado.getCategoria().equals("delimitador_literal_aspas")) {
                        this.tokens.remove(0);
                        inserirNo(listaNo.get(listaNo.size() - 1), "<literal>");
                        inserirNo(listaNo.get(listaNo.size() - 1), "'" + tokenAnalisado.getSimbolo() + "'");
                        listaNo.remove(listaNo.size() - 1);
                        listaNo.remove(listaNo.size() - 1);
                        retorno = true;
                    }
                }
            }
        }

        return retorno;
    }

    private boolean instrucaoIF() {
        boolean retorno = false;

        if (!this.tokens.isEmpty()) {
            tokenAnalisado = this.tokens.get(0);

            switch (tokenAnalisado.getCategoria()) {
                case "instrucao_if":
                    this.tokens.remove(0);
                    retorno = true;
                    inserirNo(listaNo.get(listaNo.size() - 1), "<instrucaoIF>");
                    inserirNo(listaNo.get(listaNo.size() - 1), tokenAnalisado.getSimbolo());
                    listaNo.remove(listaNo.size() - 1);
                    listaNo.remove(listaNo.size() - 1);
                    break;
            }
        }

        return retorno;
    }

    private boolean instrucaoELSE() {
        boolean retorno = false;

        if (!this.tokens.isEmpty()) {
            tokenAnalisado = this.tokens.get(0);

            switch (tokenAnalisado.getCategoria()) {
                case "instrucao_else":
                    this.tokens.remove(0);
                    retorno = true;
                    inserirNo(listaNo.get(listaNo.size() - 1), "<instrucaoELSE>");
                    inserirNo(listaNo.get(listaNo.size() - 1), tokenAnalisado.getSimbolo());
                    listaNo.remove(listaNo.size() - 1);
                    listaNo.remove(listaNo.size() - 1);
                    break;
            }
        }

        return retorno;
    }

    private boolean instrucaoBreak() {
        boolean retorno = false;

        if (!this.tokens.isEmpty()) {
            tokenAnalisado = this.tokens.get(0);

            switch (tokenAnalisado.getCategoria()) {
                case "instrucao_break":
                    this.tokens.remove(0);
                    retorno = true;
                    inserirNo(listaNo.get(listaNo.size() - 1), "<instrucaoBreak>");
                    inserirNo(listaNo.get(listaNo.size() - 1), tokenAnalisado.getSimbolo());
                    listaNo.remove(listaNo.size() - 1);
                    listaNo.remove(listaNo.size() - 1);
                    break;
            }
        }

        return retorno;
    }

    private boolean instrucaoPrintf() {
        boolean retorno = false;

        if (!this.tokens.isEmpty()) {
            tokenAnalisado = this.tokens.get(0);

            switch (tokenAnalisado.getCategoria()) {
                case "instrucao_printf":
                    this.tokens.remove(0);
                    retorno = true;
                    inserirNo(listaNo.get(listaNo.size() - 1), "<instrucaoPrintf>");
                    inserirNo(listaNo.get(listaNo.size() - 1), tokenAnalisado.getSimbolo());
                    listaNo.remove(listaNo.size() - 1);
                    listaNo.remove(listaNo.size() - 1);
                    break;
            }
        }

        return retorno;
    }

    private boolean instrucaoScanf() {
        boolean retorno = false;

        if (!this.tokens.isEmpty()) {
            tokenAnalisado = this.tokens.get(0);

            switch (tokenAnalisado.getCategoria()) {
                case "instrucao_scanf":
                    this.tokens.remove(0);
                    retorno = true;
                    inserirNo(listaNo.get(listaNo.size() - 1), "<instrucaoScanf>");
                    inserirNo(listaNo.get(listaNo.size() - 1), tokenAnalisado.getSimbolo());
                    listaNo.remove(listaNo.size() - 1);
                    listaNo.remove(listaNo.size() - 1);
                    break;
            }
        }

        return retorno;
    }

    private boolean instrucaoReturn() {
        boolean retorno = false;

        if (!this.tokens.isEmpty()) {
            tokenAnalisado = this.tokens.get(0);

            switch (tokenAnalisado.getCategoria()) {
                case "instrucao_return":
                    this.tokens.remove(0);
                    retorno = true;
                    inserirNo(listaNo.get(listaNo.size() - 1), "<instrucaoReturn>");
                    inserirNo(listaNo.get(listaNo.size() - 1), tokenAnalisado.getSimbolo());
                    listaNo.remove(listaNo.size() - 1);
                    listaNo.remove(listaNo.size() - 1);
                    break;
            }
        }

        return retorno;
    }

    private boolean especificador() {
        boolean retorno = false;

        if (!this.tokens.isEmpty()) {
            tokenAnalisado = this.tokens.get(0);

            switch (tokenAnalisado.getCategoria()) {
                case "Especificador_AUTO":
                case "Especificador_STATIC":
                case "Especificador_EXTERN":
                case "Especificador_CONST":
                    this.tokens.remove(0);
                    retorno = true;
                    inserirNo(listaNo.get(listaNo.size() - 1), "<especificador>");
                    inserirNo(listaNo.get(listaNo.size() - 1), tokenAnalisado.getSimbolo());
                    listaNo.remove(listaNo.size() - 1);
                    listaNo.remove(listaNo.size() - 1);
                    break;
            }
        }

        return retorno;
    }

    private boolean tipo() {
        boolean retorno = false;

        if (!this.tokens.isEmpty()) {
            tokenAnalisado = this.tokens.get(0);

            switch (tokenAnalisado.getCategoria()) {
                case "Especificador_VOID":
                case "Especificador_CHAR":
                case "Especificador_FLOAT":
                case "Especificador_DOUBLE":
                case "Especificador_SHORT":
                case "Especificador_INT":
                case "Especificador_LONG":
                    this.tokens.remove(0);
                    retorno = true;
                    inserirNo(listaNo.get(listaNo.size() - 1), "<tipo>");
                    inserirNo(listaNo.get(listaNo.size() - 1), tokenAnalisado.getSimbolo());
                    listaNo.remove(listaNo.size() - 1);
                    listaNo.remove(listaNo.size() - 1);
                    break;
                case "sinalizador_tipo_signed":
                case "sinalizador_tipo_unsigned":
                    this.tokens.remove(0);
                    inserirNo(listaNo.get(listaNo.size() - 1), "<sinalizador>");
                    inserirNo(listaNo.get(listaNo.size() - 1), tokenAnalisado.getSimbolo());
                    listaNo.remove(listaNo.size() - 1);
                    listaNo.remove(listaNo.size() - 1);
                    retorno = inteiro();
                    break;
            }
        }

        return retorno;
    }

    private boolean inteiro() {
        boolean retorno = false;

        if (!this.tokens.isEmpty()) {
            tokenAnalisado = this.tokens.get(0);

            switch (tokenAnalisado.getCategoria()) {
                case "Especificador_SHORT":
                case "Especificador_INT":
                case "Especificador_LONG":
                    this.tokens.remove(0);
                    retorno = true;
                    inserirNo(listaNo.get(listaNo.size() - 1), "<inteiro>");
                    inserirNo(listaNo.get(listaNo.size() - 1), tokenAnalisado.getSimbolo());
                    listaNo.remove(listaNo.size() - 1);
                    listaNo.remove(listaNo.size() - 1);
                    break;
            }
        }

        return retorno;
    }

    private boolean delimitadorInstrucaoPontoEVirgula() {
        boolean retorno = false;

        if (!this.tokens.isEmpty()) {
            tokenAnalisado = this.tokens.get(0);

            switch (tokenAnalisado.getCategoria()) {
                case "delimitador_instrucao_ponto_e_virgula":
                    this.tokens.remove(0);
                    retorno = true;
                    inserirNo(listaNo.get(listaNo.size() - 1), "<delimitadorPontoEVirgula>");
                    inserirNo(listaNo.get(listaNo.size() - 1), tokenAnalisado.getSimbolo());
                    listaNo.remove(listaNo.size() - 1);
                    listaNo.remove(listaNo.size() - 1);
                    break;
            }
        }

        return retorno;
    }

    private boolean separadorVirgula() {
        boolean retorno = false;

        if (!this.tokens.isEmpty()) {
            tokenAnalisado = this.tokens.get(0);

            switch (tokenAnalisado.getCategoria()) {
                case "separador_virgula":
                    this.tokens.remove(0);
                    retorno = true;
                    inserirNo(listaNo.get(listaNo.size() - 1), "<separadorVirgula>");
                    inserirNo(listaNo.get(listaNo.size() - 1), tokenAnalisado.getSimbolo());
                    listaNo.remove(listaNo.size() - 1);
                    listaNo.remove(listaNo.size() - 1);
                    break;
            }
        }

        return retorno;
    }

    private boolean abreColchete() {
        boolean retorno = false;

        if (!this.tokens.isEmpty()) {
            tokenAnalisado = this.tokens.get(0);

            switch (tokenAnalisado.getCategoria()) {
                case "instrucao_abre_colchete":
                    this.tokens.remove(0);
                    retorno = true;
                    inserirNo(listaNo.get(listaNo.size() - 1), "<instrucaoAbreColchete>");
                    inserirNo(listaNo.get(listaNo.size() - 1), tokenAnalisado.getSimbolo());
                    listaNo.remove(listaNo.size() - 1);
                    listaNo.remove(listaNo.size() - 1);
                    break;
            }
        }

        return retorno;
    }

    private boolean fechaColchete() {
        boolean retorno = false;

        if (!this.tokens.isEmpty()) {
            tokenAnalisado = this.tokens.get(0);

            switch (tokenAnalisado.getCategoria()) {
                case "instrucao_fecha_colchete":
                    this.tokens.remove(0);
                    retorno = true;
                    inserirNo(listaNo.get(listaNo.size() - 1), "<instrucaoFechaConlchete>");
                    inserirNo(listaNo.get(listaNo.size() - 1), tokenAnalisado.getSimbolo());
                    listaNo.remove(listaNo.size() - 1);
                    listaNo.remove(listaNo.size() - 1);
                    break;
            }
        }

        return retorno;
    }

    private boolean abreParentese() {
        boolean retorno = false;

        if (!this.tokens.isEmpty()) {
            tokenAnalisado = this.tokens.get(0);

            switch (tokenAnalisado.getCategoria()) {
                case "instrucao_abre_parentese":
                    this.tokens.remove(0);
                    retorno = true;
                    inserirNo(listaNo.get(listaNo.size() - 1), "<instrucaoAbreParensete>");
                    inserirNo(listaNo.get(listaNo.size() - 1), tokenAnalisado.getSimbolo());
                    listaNo.remove(listaNo.size() - 1);
                    listaNo.remove(listaNo.size() - 1);
                    break;
            }
        }

        return retorno;
    }

    private boolean fechaParentese() {
        boolean retorno = false;

        if (!this.tokens.isEmpty()) {
            tokenAnalisado = this.tokens.get(0);

            switch (tokenAnalisado.getCategoria()) {
                case "instrucao_fecha_parentese":
                    this.tokens.remove(0);
                    retorno = true;
                    inserirNo(listaNo.get(listaNo.size() - 1), "<instrucaoFechaParentese>");
                    inserirNo(listaNo.get(listaNo.size() - 1), tokenAnalisado.getSimbolo());
                    listaNo.remove(listaNo.size() - 1);
                    listaNo.remove(listaNo.size() - 1);
                    break;
            }
        }

        return retorno;
    }

    private boolean abreChave() {
        boolean retorno = false;

        if (!this.tokens.isEmpty()) {
            tokenAnalisado = this.tokens.get(0);

            switch (tokenAnalisado.getCategoria()) {
                case "delimitador_bloco_abre_chave":
                    this.tokens.remove(0);
                    retorno = true;
                    pilhaDeChaves.add(tokenAnalisado);
                    inserirNo(listaNo.get(listaNo.size() - 1), "<instrucaoAbreChave>");
                    inserirNo(listaNo.get(listaNo.size() - 1), tokenAnalisado.getSimbolo());
                    listaNo.remove(listaNo.size() - 1);
                    listaNo.remove(listaNo.size() - 1);
                    break;
            }
        }

        return retorno;
    }

    private boolean fechaChave() {
        boolean retorno = false;

        if (!this.tokens.isEmpty()) {
            tokenAnalisado = this.tokens.get(0);

            switch (tokenAnalisado.getCategoria()) {
                case "delimitador_bloco_fecha_chave":
                    this.tokens.remove(0);
                    retorno = true;
                    if (!pilhaDeChaves.isEmpty()) {
                        pilhaDeChaves.remove(0);
                    }
                    inserirNo(listaNo.get(listaNo.size() - 1), "<instrucaoFechaChave>");
                    inserirNo(listaNo.get(listaNo.size() - 1), tokenAnalisado.getSimbolo());
                    listaNo.remove(listaNo.size() - 1);
                    listaNo.remove(listaNo.size() - 1);
                    break;
            }
        }

        return retorno;
    }

    private boolean id() {
        boolean retorno = false;

        if (!this.tokens.isEmpty()) {
            tokenAnalisado = this.tokens.get(0);

            switch (tokenAnalisado.getCategoria()) {
                case "identificador":
                    this.tokens.remove(0);
                    retorno = true;
                    inserirNo(listaNo.get(listaNo.size() - 1), "<identificador>");
                    inserirNo(listaNo.get(listaNo.size() - 1), tokenAnalisado.getSimbolo());
                    listaNo.remove(listaNo.size() - 1);
                    listaNo.remove(listaNo.size() - 1);
                    break;
            }
        }

        return retorno;
    }

    private boolean num() {
        boolean retorno = false;

        if (!this.tokens.isEmpty()) {
            tokenAnalisado = this.tokens.get(0);

            // a classificação para digito no analisador léxico, já engloba
            // número inteiros e com ponto flutuante, por isto <num> é
            // verificado por <digito>. (Veja a gramática)
            switch (tokenAnalisado.getCategoria()) {
                case "digito":
                    this.tokens.remove(0);
                    retorno = true;
                    inserirNo(listaNo.get(listaNo.size() - 1), "<num>");
                    inserirNo(listaNo.get(listaNo.size() - 1), tokenAnalisado.getSimbolo());
                    listaNo.remove(listaNo.size() - 1);
                    listaNo.remove(listaNo.size() - 1);
                    break;
                case "operador_aritmetico_subtracao":
                case "operador_aritmetico_soma":
                    if (!this.tokens.isEmpty()) {
                        Token temp = this.tokens.get(1);
                        if (temp.getCategoria().equals("digito")) {
                            this.tokens.remove(0);
                            this.tokens.remove(0);
                            retorno = true;
                            inserirNo(listaNo.get(listaNo.size() - 1), "<num>");
                            inserirNo(listaNo.get(listaNo.size() - 1), tokenAnalisado.getSimbolo() + temp.getSimbolo());
                            listaNo.remove(listaNo.size() - 1);
                            listaNo.remove(listaNo.size() - 1);
                        }
                    }
                    break;
            }
        }

        return retorno;
    }

    private boolean CRLF(int pLinha) {
        boolean retorno = false;

        if (!this.tokens.isEmpty()) {
            tokenAnalisado = this.tokens.get(0);

            if (tokenAnalisado.getLinha().getPosicao() != pLinha) {
                retorno = true;
                inserirNo(listaNo.get(listaNo.size() - 1), "<CRLF>");
                inserirNo(listaNo.get(listaNo.size() - 1), "\\n");
                listaNo.remove(listaNo.size() - 1);
                listaNo.remove(listaNo.size() - 1);
            }
        }

        return retorno;
    }

    // Tenta recuperar após ocorrência de erro para continuar a análise
    private void recuperarErro() {
        if (!this.tokens.isEmpty()) {
            Token temp;
            temp = this.tokens.get(0);
            while ((!this.tokens.isEmpty()) && (!tokenSincronizador(temp))) {
                //System.out.println("Removeu " + temp.getSimbolo() + " da linha " + temp.getLinha().getPosicao());
                if (!this.tokens.isEmpty()) {
                    this.tokens.remove(0);
                }
                if (!this.tokens.isEmpty()) {
                    temp = this.tokens.get(0);
                }
            }
            if (!this.tokens.isEmpty()) {
                if (this.tokens.get(0).getSimbolo().equals(";")) {
                    this.tokens.remove(0);
                    //System.out.println("Removeu " + temp.getSimbolo() + " da linha " + temp.getLinha().getPosicao());
                }
            }
        }

        inserirNo(listaNo.get(listaNo.size() - 1), "<ERRO>");
        listaNo.remove(listaNo.size() - 1);

    }

    private boolean tokenSincronizador(Token pToken) {
        boolean retorno = false;

        switch (pToken.getCategoria()) {
            case "delimitador_instrucao_ponto_e_virgula":
            case "define":
            case "Especificador_AUTO":
            case "Especificador_STATIC":
            case "Especificador_EXTERN":
            case "Especificador_CONST":
            case "Especificador_VOID":
            case "Especificador_CHAR":
            case "Especificador_FLOAT":
            case "Especificador_DOUBLE":
            case "Especificador_SHORT":
            case "Especificador_INT":
            case "Especificador_LONG":
            case "sinalizador_tipo_signed":
            case "sinalizador_tipo_unsigned":
            case "instrucao_return":
            case "instrucao_printf":
            case "instrucao_scanf":
            case "instrucao_break":
            case "instrucao_if":
                retorno = true;
                break;
        }

        return retorno;
    }

    private void msgErro(String simboloEsperado)  {
        if (tokenAnalisado != null && !tokenAnalisado.getCategoria().equals("error")) {

            if (simboloEsperado.equals("<;>") || simboloEsperado.equals("<,> ou <;>")) {
                this.handlerErros.addErro(tokenAnalisado, "Erro sintático. Esperado '" + simboloEsperado + "'.");

            } else {
                if (!this.tokens.isEmpty()) {
                    this.handlerErros.addErro(tokenAnalisado, "Erro sintático para o token '" + tokenAnalisado.getSimbolo()
                        + "'. Esperado " + simboloEsperado + ", encontrado '" + tokenAnalisado.getCategoria() + "'.");
                }
            }
        }

    }
}
