package Graphs;

import java.util.*;

public class Vertex {
    private int id;
    private Map<Vertex, Integer> neighbours;

    public Vertex(int _id){
        neighbours = new HashMap<>();
        id = _id;
    }

    public Map<Vertex, Integer> getNeighbours() {
        return neighbours;
    }

    public void RemoveNeighbours(){
        neighbours.clear();
    }

    public void AddNeighbour(Vertex v, Integer weight){
        neighbours.putIfAbsent(v, weight);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return id == vertex.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, neighbours);
    }
}
