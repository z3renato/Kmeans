/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kmeans;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import javax.swing.JPanel;

/**
 *
 * @author tribit
 */
public class Kmeans {

    public static class Dados {

        /**
         *
         * @throws FileNotFoundException
         * @throws IOException
         */
        List<List<Double>> dados = new ArrayList<>();

        public Dados() throws IOException {
            carregarDados();

        }

        public void carregarDados() throws FileNotFoundException, IOException {
            File file = new File("entrada.txt");
            BufferedReader arq = new BufferedReader(new FileReader(file));
            String linha;

            String[] split;
            int numLinha = 0;
            while ((linha = arq.readLine()) != null) {
                dados.add(new ArrayList<>());
                linha = linha.replaceAll(",", ".");
                split = linha.split("\t");
                for (int i = 0; i < split.length; i++) {
                    dados.get(numLinha).add(Double.parseDouble(split[i]));

                }
                numLinha++;
            }
            arq.close();

            System.out.println("Dados Carregados:");
            String result = "";
            for (int i = 0; i < dados.size(); i++) {
                for (int j = 0; j < dados.get(i).size(); j++) {
                    result += dados.get(i).get(j) + " ";
                }
                System.out.println(result);
                result = "";
            }
            System.out.println("-----------");
        }

        public List<List<Double>> getDados() {
            return this.dados;
        }
    }

    List<List<Double>> centroides = new ArrayList<>();

    public double distanciaEuclidiana(List<Double> a, List<Double> b) {
        double distancia = 0;

        for (int i = 0; i < a.size(); i++) {
            distancia += Math.abs((a.get(i)) - (b.get(i)));
        }

        return distancia;
    }

    public static double gerarNumero(int minimo, int maximo) {
        return Math.random() * (maximo - minimo) + minimo;
    }

    public void run(int k) throws IOException {
        Dados dados = new Dados();
        List<List<Double>> elementos = dados.getDados();
        int[] indicesSorteados = new int[k];
        for (int i = 0; i < k; i++) {
            int num = (int) gerarNumero(0, elementos.size());

            centroides.add(elementos.get(num));
            indicesSorteados[i] = num;

        }

        System.out.println("-------------");
        System.out.println("Tamanho do vetor de centroides: " + centroides.size());
        System.out.println("Centroides:");
        for (int i = 0; i < centroides.size(); i++) {
            System.out.println(centroides.get(i));
        }
        List<List<Integer>> particoes = new ArrayList<>();
        for (int i = 0; i < centroides.size(); i++) {
            particoes.add(new ArrayList<>());
        }
        int cont=0;
        while (cont<25) {

            int indMenor = 0;
            double menor = 526736567;
            double d;
            for (int a = 0; a < centroides.size(); a++) {

                for (int i = 0; i < elementos.size(); i++) {
                    for (int j = 0; j < indicesSorteados.length; j++) {
                        if (indicesSorteados[j] == i) {
                            continue;
                        }
                    }
                    if (existeValor(i, particoes)) {
                        continue;
                    }
                    d = distanciaEuclidiana(centroides.get(a), elementos.get(i));
                    if (menor > d) {
                        menor = d;
                        indMenor = i;
                    }
                    particoes.get(a).add(indMenor);

                }
            }
            if(totalElementos(particoes)==85){
                break;
            }
            cont++;
        }
        for(List<Integer> p : particoes){
            for (int i = 0; i < p.size(); i++) {
                System.out.println(p.get(i)+", ");
            }
        }
    }
    public int totalElementos(List<List<Integer>> valores){
        int total = 0;
        for(List<Integer> v : valores){
            for (int i = 0; i < v.size(); i++) {
                total++;
            }
        }
        return total;
    }

    public boolean existeValor(int valor, List<List<Integer>> valores) {
        if (valores.isEmpty()) {
            return false;
        }
        for (List<Integer> v : valores) {
            for (int i = 0; i < v.size(); i++) {
                if (v.get(i) == valor) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) throws IOException {
        Kmeans k = new Kmeans();
        k.run(4);
    }
}
