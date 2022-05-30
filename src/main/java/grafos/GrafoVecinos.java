package grafos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.stream.IntStream;

public class GrafoVecinos {
    private ArrayList<HashSet<Integer>> verticesConVecinos;
    private ArrayList<HashMap<Integer, Integer>> aristas;

    public GrafoVecinos(int vertices) {
        this.verticesConVecinos = new ArrayList<HashSet<Integer>>();
        this.aristas = new ArrayList<HashMap<Integer, Integer>>();
        IntStream _vertices = IntStream.range(0, vertices);
        _vertices.forEach(vertice -> {
            verticesConVecinos.add(new HashSet<Integer>());
            aristas.add(new HashMap<Integer, Integer>());
        });
        /*for (int i = 0; i < vertices; ++i) {
            this.verticesConVecinos.add(new HashSet<Integer>());
            this.aristas.add(new HashMap<Integer, Integer>());
        }*/
    }

    public void agregarVecino(int vertice, int vecino, int peso) {
        verificarVertice(vertice);
        verificarVertice(vecino);
        if (!sonVecinos(vertice, vecino)) {
            this.verticesConVecinos.get(vertice).add(vecino);
            this.verticesConVecinos.get(vecino).add(vertice);
            this.aristas.get(vertice).put(vecino,peso);
            this.aristas.get(vecino).put(vertice,peso);
        }
    }

    public int pesoArista(int vertice, int vecino){
        verificarVertice(vertice);
        verificarVertice(vecino);
        return this.aristas.get(vertice).get(vecino);
    }

    public HashSet<Integer> obtenerVecinos(int vertice) {
        verificarVertice(vertice);
        return this.verticesConVecinos.get(vertice);
    }

    public boolean sonVecinos(int vertice, int vecino) {
        verificarVertice(vertice);
        verificarVertice(vecino);
        return this.verticesConVecinos.get(vertice).contains(vecino);
    }

    public int tamano(){
        return this.verticesConVecinos.size();
    }

     void verificarVertice(int vertice) {
        if (this.verticesConVecinos.size() <= vertice) {
            throw new IllegalArgumentException("Los vertices deben estar entre 0 y " + (this.verticesConVecinos.size() - 1));
        }

        if (vertice < 0) {
            throw new IllegalArgumentException("El vertice no puede ser negativo: " + vertice);
        }
    }
    public ArrayList<HashMap<Integer, Integer>> obtenerAristas(){
        return this.aristas;
    }

    public Integer[] obtenerVertices(){
        return (Integer[]) this.verticesConVecinos.toArray();
    }

    public void eliminarVertice(int verticeActual, Integer vecinoAnterior) {
        this.verticesConVecinos.remove(verticeActual);
        this.verticesConVecinos.remove(vecinoAnterior);
        this.aristas.get(verticeActual).remove(vecinoAnterior);
        this.aristas.get(vecinoAnterior).remove(verticeActual);
    }

    public String[][] toArray2D(){
        //ACA LO INICIAMOS CON [NUMERO DE VERTICES]  [3 FIJO: ESPIA/ESPIA/PESO]
        String[][] grafo = new String[(this.tamano()-1)*2][3];
        final int[] x = {0};
        //INICIAMOS UN FOR QUE RECORRA LOS VERTICES DEL GRAFO.
        IntStream vertices = IntStream.range(0, tamano()).limit(tamano());
        vertices.forEach(vertice -> {
            HashSet<Integer> vecinos = this.obtenerVecinos(vertice);
            Iterator<Integer> iteratorVecinos = vecinos.iterator();
            while (iteratorVecinos.hasNext()){
                int vecinoActual = iteratorVecinos.next();
                grafo[x[0]][0] = String.valueOf(vecinoActual);
                grafo[x[0]][1] = String.valueOf(vertice);
                grafo[x[0]][2] = String.valueOf(this.pesoArista(vertice,vecinoActual));
                x[0]++;
            }
        });
        /*for (int i = 0; i < this.tamano(); i++) {
            HashSet<Integer> vecinos = this.obtenerVecinos(i);
            Iterator<Integer> iteratorVecinos = vecinos.iterator();

            while (iteratorVecinos.hasNext()){
                int vecinoActual = iteratorVecinos.next();
                grafo[x[0]][0] = String.valueOf(vecinoActual);
                grafo[x[0]][1] = String.valueOf(i);
                grafo[x[0]][2] = String.valueOf(this.pesoArista(i,vecinoActual));
                x[0]++;
            }

        }*/
        return grafo;
    }

}