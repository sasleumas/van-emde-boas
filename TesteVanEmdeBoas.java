package van_emde_boas;

public class TesteVanEmdeBoas {
    public static void main(String args[]) {
        // Exemplo de uso
        //Universos possívels: 2, 4, 16, 256, ...
        VanEmdeBoas veb = new VanEmdeBoas(16); // Universo de 0 a 15
        veb.valorNoCluster = false;
        insercoes(veb);
        //testar2();
        //testar4();
        //testar16();
    }

    public static void testar2() {
        System.out.println("---------------Teste de Universo = 2---------------");
        VanEmdeBoas veb = new VanEmdeBoas(2);
        testarResumo(veb.resumo, true, null);
        testarClusters(veb.clusters, null);
        testarValor("mínimo", null, veb.min);
        testarValor("máximo", null, veb.max);
        testarValor("predecessor", null, veb.predecessor(1));
        testarValor("sucessor", null, veb.successor(0));
        testarImprimir("", veb.imprimir());

        veb.inserir(0);
        testarResumo(veb.resumo, true, null);
        testarClusters(veb.clusters, null);
        testarValor("mínimo",0, veb.min);
        testarValor("máximo",0, veb.max);
        testarValor("predecessor", 0, veb.predecessor(1));
        testarValor("predecessor", null, veb.predecessor(0));
        testarValor("sucessor", null, veb.successor(0));
        testarValor("sucessor", null, veb.successor(1));
        testarImprimir("Min: 0", veb.imprimir());

        veb.inserir(1);
        testarResumo(veb.resumo, true, null);
        testarClusters(veb.clusters, null);
        testarValor("mínimo",0, veb.min);
        testarValor("máximo",1, veb.max);
        testarValor("predecessor", null, veb.predecessor(2));
        testarValor("sucessor", null, veb.successor(2));
        testarValor("predecessor", 0, veb.predecessor(1));
        testarValor("predecessor", null, veb.predecessor(0));
        testarValor("sucessor", 1, veb.successor(0));
        testarValor("sucessor", null, veb.successor(1));
        testarImprimir("Min: 0, 1", veb.imprimir());

        veb.inserir(4);
        testarResumo(veb.resumo, true, null);
        testarClusters(veb.clusters, null);
        testarValor("mínimo",0, veb.min);
        testarValor("máximo",1, veb.max);
        testarValor("predecessor", null, veb.predecessor(2));
        testarValor("sucessor", null, veb.successor(2));
        testarValor("predecessor", 0, veb.predecessor(1));
        testarValor("predecessor", null, veb.predecessor(0));
        testarValor("sucessor", 1, veb.successor(0));
        testarValor("sucessor", null, veb.successor(1));
        testarImprimir("Min: 0, 1", veb.imprimir());

        veb.remover(0);
        testarResumo(veb.resumo, true, null);
        testarClusters(veb.clusters, null);
        testarValor("mínimo",1, veb.min);
        testarValor("máximo",1, veb.max);
        testarValor("predecessor", null, veb.predecessor(2));
        testarValor("sucessor", null, veb.successor(2));
        testarValor("predecessor", null, veb.predecessor(1));
        testarValor("sucessor", 1, veb.successor(0));
        testarValor("sucessor", null, veb.successor(1));
        testarImprimir("Min: 1", veb.imprimir());

        veb.remover(5);
        testarResumo(veb.resumo, true, null);
        testarClusters(veb.clusters, null);
        testarValor("mínimo",1, veb.min);
        testarValor("máximo",1, veb.max);
        testarValor("predecessor", null, veb.predecessor(2));
        testarValor("sucessor", null, veb.successor(2));
        testarValor("predecessor", null, veb.predecessor(1));
        testarValor("sucessor", 1, veb.successor(0));
        testarValor("sucessor", null, veb.successor(1));
        testarImprimir("Min: 1", veb.imprimir());

        veb.remover(1);
        testarValor("mínimo", null, veb.min);
        testarValor("máximo", null, veb.max);
        testarValor("predecessor", null, veb.predecessor(2));
        testarValor("sucessor", null, veb.successor(2));
        testarValor("predecessor", null, veb.predecessor(1));
        testarValor("sucessor", null, veb.successor(0));
        testarValor("sucessor", null, veb.successor(1));
        testarImprimir("", veb.imprimir());
    }

    public static void testar4() {
        System.out.println("---------------Teste de Universo = 4---------------");
        VanEmdeBoas veb = new VanEmdeBoas(4);
        testarResumo(veb.resumo, false, null);
        testarResumo(veb.resumo.resumo, true, null);
        testarClusters(veb.clusters, 2);
        testarValor("mínimo", null, veb.min);
        testarValor("máximo", null, veb.max);
        testarValor("predecessor", null, veb.predecessor(2));
        testarValor("sucessor", null, veb.successor(2));
        testarImprimir("", veb.imprimir());


        veb.inserir(3);
        testarValor("mínimo",3, veb.min);
        testarValor("máximo",3, veb.max);
        testarValor("predecessor", null, veb.predecessor(2));
        testarValor("predecessor", null, veb.predecessor(5));
        testarValor("sucessor", 3, veb.successor(2));
        testarValor("sucessor", null, veb.successor(5));
        testarImprimir("Min: 3", veb.imprimir());

        veb.inserir(2);
        testarValor("mínimo",2, veb.min);
        testarValor("máximo",3, veb.max);
        testarValor("predecessor", null, veb.predecessor(2));
        testarValor("predecessor", null, veb.predecessor(5));
        testarValor("sucessor", 3, veb.successor(2));
        testarValor("sucessor", null, veb.successor(5));
        testarImprimir("Min: 2, C[1]: 1", veb.imprimir());//11 em binário para 3

        veb.inserir(0);
        testarValor("mínimo",0, veb.min);
        testarValor("máximo",3, veb.max);
        testarValor("predecessor", 0, veb.predecessor(2));
        testarValor("predecessor", null, veb.predecessor(5));
        testarValor("sucessor", 3, veb.successor(2));
        testarValor("sucessor", null, veb.successor(5));
        testarImprimir("Min: 0, C[1]: 0, 1", veb.imprimir());

        veb.inserir(1);
        testarValor("mínimo",0, veb.min);
        testarValor("máximo",3, veb.max);
        testarValor("predecessor", 1, veb.predecessor(2));
        testarValor("predecessor", 2, veb.predecessor(3));
        testarValor("sucessor", 3, veb.successor(2));
        testarValor("sucessor", 1, veb.successor(0));
        testarImprimir("Min: 0, C[0]: 1, C[1]: 0, 1", veb.imprimir());//01 para 1

        veb.remover(2);
        testarValor("mínimo",0, veb.min);
        testarValor("máximo",3, veb.max);
        testarValor("predecessor", 1, veb.predecessor(2));
        testarValor("predecessor", 1, veb.predecessor(3));
        testarValor("sucessor", 3, veb.successor(2));
        testarValor("sucessor", 1, veb.successor(0));
        testarImprimir("Min: 0, C[0]: 1, C[1]: 1", veb.imprimir());

        veb.remover(0);
        testarValor("mínimo",1, veb.min);
        testarValor("máximo",3, veb.max);
        testarValor("predecessor", 1, veb.predecessor(2));
        testarValor("predecessor", 1, veb.predecessor(3));
        testarValor("sucessor", 3, veb.successor(2));
        testarValor("sucessor", 1, veb.successor(0));
        testarImprimir("Min: 1, C[1]: 1", veb.imprimir());

        veb.remover(3);
        testarValor("mínimo",1, veb.min);
        testarValor("máximo",1, veb.max);
        testarValor("predecessor", 1, veb.predecessor(2));
        testarValor("predecessor", 1, veb.predecessor(3));
        testarValor("sucessor", null, veb.successor(2));
        testarValor("sucessor", 1, veb.successor(0));
        testarImprimir("Min: 1", veb.imprimir());

        veb.remover(1);
        testarValor("mínimo",null, veb.min);
        testarValor("máximo",null, veb.max);
        testarValor("predecessor", null, veb.predecessor(2));
        testarValor("predecessor", null, veb.predecessor(3));
        testarValor("sucessor", null, veb.successor(2));
        testarValor("sucessor", null, veb.successor(0));
        testarImprimir("", veb.imprimir());

        veb.inserir(2);
        testarValor("mínimo",2, veb.min);
        testarValor("máximo",2, veb.max);
        testarValor("predecessor", null, veb.predecessor(2));
        testarValor("predecessor", 2, veb.predecessor(3));
        testarValor("sucessor", null, veb.successor(2));
        testarValor("sucessor", 2, veb.successor(0));
        testarImprimir("Min: 2", veb.imprimir());
    }

    public static void testar16() {
        System.out.println("---------------Teste de Universo = 16---------------");
        VanEmdeBoas veb = new VanEmdeBoas(16);
        testarResumo(veb.resumo, false, 2);
        testarResumo(veb.resumo.resumo, false, null);
        testarResumo(veb.resumo.resumo.resumo, true, null);
        testarClusters(veb.clusters, 4);
        testarValor("mínimo", null, veb.min);
        testarValor("máximo", null, veb.max);
        testarValor("predecessor", null, veb.predecessor(2));
        testarValor("sucessor", null, veb.successor(2));
        testarImprimir("", veb.imprimir());

        veb.inserir(3);
        testarValor("mínimo",3, veb.min);
        testarValor("máximo",3, veb.max);
        testarValor("predecessor", null, veb.predecessor(2));
        testarValor("predecessor", 3, veb.predecessor(5));
        testarValor("sucessor", 3, veb.successor(2));
        testarValor("sucessor", null, veb.successor(5));
        testarImprimir("Min: 3", veb.imprimir());

        veb.inserir(2);
        testarValor("mínimo",2, veb.min);
        testarValor("máximo",3, veb.max);
        testarValor("predecessor", null, veb.predecessor(2));
        testarValor("predecessor", 3, veb.predecessor(5));
        testarValor("sucessor", 3, veb.successor(2));
        testarValor("sucessor", null, veb.successor(5));
        testarImprimir("Min: 2, C[0]: 3", veb.imprimir());//11 em binário para 3

        veb.inserir(0);
        testarValor("mínimo",0, veb.min);
        testarValor("máximo",3, veb.max);
        testarValor("predecessor", 0, veb.predecessor(2));
        testarValor("predecessor", 3, veb.predecessor(5));
        testarValor("sucessor", 3, veb.successor(2));
        testarValor("sucessor", null, veb.successor(5));
        testarImprimir("Min: 0, C[0]: 2, 3", veb.imprimir());

        veb.inserir(1);
        testarValor("mínimo",0, veb.min);
        testarValor("máximo",3, veb.max);
        testarValor("predecessor", 1, veb.predecessor(2));
        testarValor("predecessor", 2, veb.predecessor(3));
        testarValor("sucessor", 3, veb.successor(2));
        testarValor("sucessor", 1, veb.successor(0));
        testarImprimir("Min: 0, C[0]: 1, 2, 3", veb.imprimir());//01 para 1

        veb.remover(2);
        testarValor("mínimo",0, veb.min);
        testarValor("máximo",3, veb.max);
        testarValor("predecessor", 1, veb.predecessor(2));
        testarValor("predecessor", 1, veb.predecessor(3));
        testarValor("sucessor", 3, veb.successor(2));
        testarValor("sucessor", 1, veb.successor(0));
        testarImprimir("Min: 0, C[0]: 1, 3", veb.imprimir());

        veb.remover(0);
        testarValor("mínimo",1, veb.min);
        testarValor("máximo",3, veb.max);
        testarValor("predecessor", 1, veb.predecessor(2));
        testarValor("predecessor", 1, veb.predecessor(3));
        testarValor("sucessor", 3, veb.successor(2));
        testarValor("sucessor", 1, veb.successor(0));
        testarImprimir("Min: 1, C[0]: 3", veb.imprimir());

        veb.remover(3);
        testarValor("mínimo",1, veb.min);
        testarValor("máximo",1, veb.max);
        testarValor("predecessor", 1, veb.predecessor(2));
        testarValor("predecessor", 1, veb.predecessor(3));
        testarValor("sucessor", null, veb.successor(2));
        testarValor("sucessor", 1, veb.successor(0));
        testarImprimir("Min: 1", veb.imprimir());

        veb.remover(1);
        testarValor("mínimo",null, veb.min);
        testarValor("máximo",null, veb.max);
        testarValor("predecessor", null, veb.predecessor(2));
        testarValor("predecessor", null, veb.predecessor(3));
        testarValor("sucessor", null, veb.successor(2));
        testarValor("sucessor", null, veb.successor(0));
        testarImprimir("", veb.imprimir());

        veb.inserir(2);
        testarValor("mínimo",2, veb.min);
        testarValor("máximo",2, veb.max);
        testarValor("predecessor", null, veb.predecessor(2));
        testarValor("predecessor", 2, veb.predecessor(3));
        testarValor("sucessor", null, veb.successor(2));
        testarValor("sucessor", 2, veb.successor(0));
        testarImprimir("Min: 2", veb.imprimir());
    }

    public static void testarResumo(VanEmdeBoas r, boolean nulo, Integer tamCluster) {
        System.out.println("Teste do resumo.");
        if ((r == null && !nulo) || (r != null && nulo)) {
            dispararErro("Erro no resumo", "nulo == " + nulo, r);
            return;
        } 
        if (r != null) {
            if (r.clusters == null && tamCluster != null)
                dispararErro("Número de clusters não é o esperado", tamCluster, null);
            else if (r.clusters != null && r.clusters.length != tamCluster)
                dispararErro("Número de clusters não é o esperado", tamCluster, r.clusters.length);
            return;
        }
        System.out.println("Resumo testado com sucesso.");    
    }

    public static void testarClusters(VanEmdeBoas[] cl, Integer tam) {
        System.out.println("Teste dos clusters.");
        if ((cl == null && tam != null) || (cl != null && tam == null)) {
            dispararErro("Erro nos clusters. Tamanho esperado nulo ou vetor nulo.", tam, cl);
            return;
        } 
        if (cl != null && cl.length != tam) {
            dispararErro("Número de clusters não é o esperado", tam, cl.length);
            return;
        }
        System.out.println("Clusters testado com sucesso.");    
    }

    public static void testarValor(String atributo, Integer esperado, Integer valor) {
        System.out.println("Teste do " + atributo + ". Esperado " + esperado + ".");
        if ((valor == null && esperado != null) || (valor != null && esperado == null)) {
            dispararErro("Erro no valor " + atributo + ".", esperado, valor);
            return;
        }
        if (esperado != null && !esperado.equals(valor)) {
            dispararErro(atributo + " diferente do esperado.", esperado, valor);
            return;
        }
        System.out.println("Valor " + atributo + " testado com sucesso.");
    }

    public static void testarImprimir(String esperado, String valor) {
        if (!esperado.equals(valor)) {
            dispararErro("Erro na impressão", esperado, valor);
        }
    }

    public static void dispararErro(String mensagem, Object esperado, Object recebido) {
        throw new IllegalArgumentException(mensagem + ". Esperado: " + esperado + ". Recebido: " + recebido);
    }

    private static void insercoes(VanEmdeBoas veb) {
        System.out.println("--- Inserindo valores ---");
        veb.inserir(2);
        veb.inserir(3);
        veb.inserir(4);
        veb.inserir(5);
        veb.inserir(10);
        veb.inserir(11);
        veb.inserir(12);
        veb.inserir(13);
        veb.inserir(0);
        veb.inserir(15);
        veb.inserir(7);        
        veb.inserir(1);
        veb.inserir(6);
        veb.inserir(8);
        veb.inserir(9);
        veb.inserir(14);
        //System.out.println(veb.imprimirCluster(0));
        System.out.println(veb.imprimir());
        //veb.print2();
    }
}