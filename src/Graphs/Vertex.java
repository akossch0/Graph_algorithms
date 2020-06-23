package Graphs;

import Views.VertexImage;
import Views.VertexView;

import java.util.*;

public class Vertex {
    private int id;
    private Map<Vertex, Integer> neighbours;
    public int X,Y;
    private VertexView vertexView;
    private List<Vertex> shortestPath = new LinkedList<>();
    private Integer distance = Integer.MAX_VALUE;

    public Vertex(int _id){
        neighbours = new HashMap<>();
        id = _id;
    }

    public VertexView getVertexView() {
        return vertexView;
    }

    public void setVertexView(VertexView vertexView) {
        this.vertexView = vertexView;
    }

    public void setVertexImage(VertexImage vi){
        vertexView.setVertexImage(vi);
    }


    public Integer getDistance(){
        return distance;
    }

    public void setDistance(Integer val){
        distance = val;
    }

    public List<Vertex> getShortestPath(){
        return shortestPath;
    }

    public void setShortestPath(List<Vertex> path){
        shortestPath = path;
    }

    public Map<Vertex, Integer> getNeighbours() {
        return neighbours;
    }

    public int getId(){ return id; }

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
        return Objects.hash(id);
    }
}
