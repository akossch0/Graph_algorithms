package Graphs;

import java.util.*;


public class Graph {
    private Set<Vertex> vertices;

    public Graph(){
        vertices = new HashSet<>();
    }

    Vertex getElement(Vertex v){
        for (Iterator<Vertex> it = vertices.iterator(); it.hasNext(); ) {
            Vertex vert = it.next();
            if (vert.equals(v))
                return vert;
        }
        return null;
    }

    void addVertex(int _id) {
        vertices.add(new Vertex(_id));
    }

    void removeVertex(int _id) {
        Vertex v = new Vertex(_id);
        for(Vertex vertex : vertices){
            if(v.equals(vertex))
                vertex.RemoveNeighbours();
        }
        vertices.remove(new Vertex(_id));
    }

    void addEdge(int id1, int id2, int weight) {
        Vertex v1 = new Vertex(id1);
        Vertex v2 = new Vertex(id2);
        Vertex vertex1 = getElement(v1);
        if(vertex1 != null)
            vertex1.AddNeighbour(v2, weight);
        Vertex vertex2 = getElement(v2);
        if(vertex2 != null)
            vertex2.AddNeighbour(v1, weight);
    }

    void removeEdge(int id1, int id2) {
        Vertex v1 = new Vertex(id1);
        Vertex v2 = new Vertex(id2);
        Set<Vertex> edges1 = getElement(v1).getNeighbours().keySet();
        Set<Vertex> edges2 = getElement(v2).getNeighbours().keySet();
        if (edges1 != null)
            edges1.remove(v2);
        if (edges2 != null)
            edges2.remove(v1);
    }

    public Map<Vertex, Integer> getNeighbours(int _id) {
        Vertex v = getElement(new Vertex(_id));
        if(v != null){
            return v.getNeighbours();
        }
        return null;
    }


    public void generateGraph(int n, int m) {

        int[][] myList = new int[n][m];
        int id = 0;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < m; col++) {
                myList[row][col] = id++;
                System.out.print(myList[row][col] + "  ");
                vertices.add(new Vertex(myList[row][col]));
            }
            System.out.println();
        }

        //adding edges
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < m; col++) {
                if(vertices.contains(new Vertex(myList[row][col])) && vertices.contains(new Vertex(myList[row][col] + 1)))
                    addEdge(myList[row][col], myList[row][col] + 1, 1);
                if(vertices.contains(new Vertex(myList[row][col])) && vertices.contains(new Vertex(myList[row][col] - 1)))
                    addEdge(myList[row][col], myList[row][col] - 1, 1);
                if(vertices.contains(new Vertex(myList[row][col])) && vertices.contains(new Vertex(myList[row][col] + m)))
                    addEdge(myList[row][col], myList[row][col] + m, 1);
                if(vertices.contains(new Vertex(myList[row][col])) && vertices.contains(new Vertex(myList[row][col] - m)))
                    addEdge(myList[row][col], myList[row][col] - m, 1);
            }
        }
    }

    public void printGraph(){
        for(Vertex v : vertices){
            Integer ID = v.getId();
            System.out.print(ID.toString() + " -> ");
            for(Vertex nbor: v.getNeighbours().keySet()){
                Integer nbId = nbor.getId();
                System.out.print(nbId.toString() + " ");
            }
            System.out.println();
        }
    }
}
