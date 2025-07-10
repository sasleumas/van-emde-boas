package van_emde_boas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VanEmdeBoas {
    //Número de valores possíveis do universo de valores positivos.
    long universo;// = (long)Math.pow(2,32);
    Integer min = null;
    Integer max = null;
    
    VanEmdeBoas resumo = null;
    //O Tamanho máximo será a raiz quadrada de 2^32 -> 2^16
    VanEmdeBoas[] clusters = null;

    // Variáveis para otimização com bits, calculadas uma vez.
    private int raiz2;
    private int log2DaRaiz; 
    private int mascara;

    public VanEmdeBoas(long u) {
        // Garante que universo é uma potência de 2
        if ((u & (u - 1)) != 0 && u != 1) {
            throw new IllegalArgumentException("O universo deve ser uma potência de 2.");
        }
        if (u == 0) {
             throw new IllegalArgumentException("O universo não pode ser 0.");
        }

        universo = u;
        min = null;
        max = null;

        if (universo == 2) {
            resumo = null;
            clusters = null;
            raiz2 = 1; // Evita erro de inicialização
            log2DaRaiz = 0;
            mascara = 0;
        } else {
            //raiz = (int) Math.sqrt(universo);
            raiz2 = 1 << (31 - Long.numberOfLeadingZeros(universo) / 2);
            // Calcula log2(raizDoUniverso)
            log2DaRaiz = 31 - Integer.numberOfLeadingZeros(raiz2);

            // Máscara para a operação 'getMenorOrdem'
            mascara = raiz2 - 1;

            resumo = new VanEmdeBoas(raiz2);
            criarClusters();
        }
    }

    public void criarClusters() {
        clusters = new VanEmdeBoas[raiz2];
        for (int i = 0; i < raiz2; i++) {
            clusters[i] = new VanEmdeBoas(raiz2);
        }
    }

    // Operações auxiliares para getMaiorOrdem, getMenorOrdem e getValor
    /**
     * Pega o valor referente aos bits de maior ordem (mais significativos) de x.
     * @param x
     * @return
     */
    int getMaiorOrdem(int x) {
        //return x / (int) Math.ceil(Math.sqrt(universo));
        return x >> log2DaRaiz;
    }

    /**
     * Pega o valor referente aos bits de menor ordem (menos significativos) de x.
     * @param x
     * @return
     */
    int getMenorOrdem(int x) {
        //return x % (int) Math.ceil(Math.sqrt(universo));
        return x & mascara;
    }

    /**
     * Restaura o valor do número com base nos valores gerados a partir dos bits
     * mais e menos significativos.
     * @param maisAlto
     * @param maisBaixo
     * @return
     */
    int getValor(int maisAlto, int maisBaixo) {
        //return maisAlto * (int) Math.ceil(Math.sqrt(universo)) + maisBaixo;
        int valor = (maisAlto << log2DaRaiz) | maisBaixo;
        return valor;
    }

    int getValor(int maisAlto, int maisBaixo, int log) {
        int valor = (maisAlto << log) | maisBaixo;
        return valor;
    }

    public boolean pertenceAoUniverso(int x) {
        if (x > universo - 1 || x < 0) {
            return false;
        }
        return true;
    }

    /**
     * Insere um elemento na estrutura van Emde Boas.
     * @param x
     */
    public void inserir(int x) {
        if (!pertenceAoUniverso(x))
            return;
        // Caso esteja vazia
        if (min == null) {
            min = x;
            max = x;
            return;
        }
        // Se x for menor que o min atual, troca x e min
        if (x < min) {
            int temp = x;
            x = min;
            min = temp;
        }
        // Atualiza o max, se necessário
        if (x > max) {
            max = x;
        }
        // Se o universo for maior que 2, coloca valor no cluster.
        if (universo > 2) {
            int c = getMaiorOrdem(x);// cluster de x
            int i = getMenorOrdem(x);// valor de x no cluster
            // Se o cluster de x estiver vazio, insere c no resumo
            if (clusters[c].min == null) {
                resumo.inserir(c);
                //TODO aumentar vetor de cluster.
            }
            // Insere i no cluster
            clusters[c].inserir(i);
        }
    }

    public void remover(int x) {
        if (!pertenceAoUniverso(x))
            return;
        // Árvore vazia
        if (min == null) {
            return;
        }
        // Árvore com um único elemento (min == max)
        if (min == x && min.equals(max)) {
            min = null;
            max = null;
            return;
        }
        if (universo == 2) {
            if (x == max) { 
                //min = 1;
                max = min;
            } else if (x == min) { 
                min = max;
                //max = 0;
            }
            return;
        }
        // Se o valor a ser removido é o 'min' do nó atual
        if (x == min) {
            // Encontra o primeiro cluster não vazio no summary
            Integer c = resumo.min;
            if (c == null) { 
                min = null;
                max = null; 
                return;
            }
            // O novo min será o menor valor no primeiro cluster não vazio
            int novoMin = getValor(c, clusters[c].min);
            min = novoMin; // Atualiza o min do nó atual
            x = novoMin;   // O valor que será removido internamente agora é o antigo min do cluster
        }
        // Delega a remoção ao cluster apropriado
        int c = getMaiorOrdem(x);
        int i = getMenorOrdem(x);
        clusters[c].remover(i);

        // Verifica se o cluster ficou vazio após a remoção
        if (clusters[c].min == null) {
            resumo.remover(c); // Remove o cluster do resumo
            //TODO reduzir vetor de cluster.
        }
        if (resumo.min == null) {
            max = min;
        } else {// Precisamos encontrar um novo 'max'.
            // Encontra o último cluster não vazio no summary
            c = resumo.max;
            // O novo max é o maior valor no último cluster não vazio
            max = getValor(c, clusters[c].max);
        }        
    }

    /**
     * Encontra o sucessor de x.
     * @param x
     * @return
     */
    public Integer successor(int x) {
        if (universo == 2) {
            if (x == 0 && max != null && max == 1) {
                return max;
            }
            return null;    
        }
        if (!pertenceAoUniverso(x))
            return null;
        // Se x é menor que o min da árvore, o min é o sucessor
        if (min != null && x < min) {
            return min;
        }
        int c = getMaiorOrdem(x);// cluster de x
        int i = getMenorOrdem(x);// valor de x no cluster
        // Se tiver o cluster correspondente a c e este tiver um max maior que i,
        // então o sucessor está no cluster c.
        VanEmdeBoas cluster = clusters[c];
        if (cluster != null && cluster.max != null && i < cluster.max) {
            Integer offset = cluster.successor(i);
            return getValor(c, offset);
        } 
        
        // Procura o próximo cluster não vazio no resumo
        Integer indiceClusterSuc = resumo.successor(c);
        if (indiceClusterSuc == null) {
            return null; // Não há sucessor
        } 
        // O sucessor é o min do próximo cluster
        return getValor(indiceClusterSuc, clusters[indiceClusterSuc].min);        
    }

    /**
     * Encontra o predecessor de x.
     * @param x
     * @return
     */
    public Integer predecessor(int x) {
        if (universo == 2) {
            if (x == 1 && min != null && min == 0) {
                return min;
            }
            return null;    
        }
        if (!pertenceAoUniverso(x))
            return null;
        // Implementação similar ao successor, mas reversa
        if (max != null && x > max) {
            return max;
        }
        int c = getMaiorOrdem(x);
        int i = getMenorOrdem(x);
        // Se tiver o cluster correspondente a c e este tiver um min menor que i,
        // então o sucessor está no cluster c.
        VanEmdeBoas cluster = clusters[c];
        if (cluster != null && cluster.min != null && i > cluster.min) {
            Integer offset = cluster.predecessor(i);
            return getValor(c, offset);
        } 
        // Procura o cluster anterior não vazio no resumo
        Integer indiceClusterPred = resumo.predecessor(c);
        if (indiceClusterPred == null) {
            if (min != null && x > min)//O mínimo não está no resumo.
                return min;
            return null; // Não há predecessor
        } 
        // O predecessor é o max do cluster anterior
        return getValor(indiceClusterPred, clusters[indiceClusterPred].max);
    }

    boolean valorNoCluster = true;
    public String imprimir() {
        String res = "";
        if (min != null) {
            res = res.concat("Min: " + min);
        } 
        if (universo > 2) {
            for (int c = 0; c < clusters.length; c++) {
                if (clusters[c].min != null) { // Verifica se o cluster não está vazio
                    res = res.concat(", C[" + c + "]: ");
                    List<Integer> valores = new ArrayList<Integer>();
                    // Coleta os valores originais deste cluster, usando 'i' como o valor de mais alta ordem para reconstruir
                    // Passa o log2 da raiz atual para reconstruir. O valor de log do cluster é menor, não sendo útil para esse fim.
                    clusters[c].coletarValoresOriginais(c, valores, log2DaRaiz);
                    // O laço abaixo altera os valores encontrados para exibir como o valor está no cluster.
                    // Para mostrar o valor integral, basta configurar valorNoCluster para false.
                    for(int i = 0; i < valores.size() && valorNoCluster; i++) {
                        valores.set(i, valores.get(i) - c * raiz2);
                    }
                    Collections.sort(valores);
                    String valoresStr = valores + "";
                    valoresStr = valoresStr.substring(1, valoresStr.length() - 1);
                    res = res.concat(valoresStr);
                }
            }
        } else {
             if (max != null && !max.equals(min)) {
                res = res.concat(", " + max);
             }
        }
        return res;
    }

    private void coletarValoresOriginais(int ordem, List<Integer> result, int logAnterior) {
        if (min != null) {
            int minimoOriginal = getValor(ordem, min, logAnterior);
            result.add(minimoOriginal);
            if (min.equals(max)) {
                return;
            }
        }

        if (universo > 2) {
            if (resumo.min != null) { 
                for (int i = 0; i < clusters.length; i++) { 
                    if (clusters[i].min != null) { 
                        int proximaOrdem = getValor(ordem, i, log2DaRaiz);
                        clusters[i].coletarValoresOriginais(proximaOrdem, result, log2DaRaiz);
                    }
                }
            }
        } else {
            if (min != null && max != null && !min.equals(max)) {
                int maximoOriginal = getValor(ordem, max, logAnterior);
                result.add(maximoOriginal);
            }
        }
    }
}
