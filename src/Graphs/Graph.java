package Graphs;

import Main.Mainframe;
import Views.*;

import java.util.*;
import java.util.concurrent.TimeUnit;


public class Graph {
    private Set<Vertex> vertices;
    private View view;
    private Mainframe frame;

    public View getView() {
        return view;
    }

    public Graph(){
        vertices = new HashSet<>();
        view = new View();
    }

    public void setFrame(Mainframe mf){
        frame = mf;
    }

    public Set<Vertex> getVertices(){
        return vertices;
    }

    public Vertex getElement(int id){
        for(Vertex v: vertices){
            if(v.getId() == id)
                return v;
        }
        return null;
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
        Vertex vertex1 = getElement(id1);
        Vertex vertex2 = getElement(id2);
        if(vertex1 != null)
            vertex1.AddNeighbour(vertex2, weight);

        if(vertex2 != null)
            vertex2.AddNeighbour(vertex1, weight);
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
                //System.out.print(myList[row][col] + "  ");
                Vertex v = new Vertex(myList[row][col]);
                v.X = col;
                v.Y = row;
                vertices.add(v);
                view.AddView(new VertexView(v));
            }
            //System.out.println();
        }

        //adding edges
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < m; col++) {
                if(vertices.contains(new Vertex(myList[row][col])) && vertices.contains(new Vertex(myList[row][col] + 1))){
                    if((myList[row][col] + 1) % m != 0)
                        addEdge(myList[row][col], myList[row][col] + 1, 1);
                }
                if(vertices.contains(new Vertex(myList[row][col])) && vertices.contains(new Vertex(myList[row][col] + m)))
                    addEdge(myList[row][col], myList[row][col] + m, 1);
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

    public List<Vertex> Dijkstra(Vertex source) {
        source.setDistance(0);

        Set<Vertex> settledNodes = new HashSet<>();
        Set<Vertex> unsettledNodes = new HashSet<>();
        List<Vertex> nodes = new ArrayList<>();

        unsettledNodes.add(source);

        while (unsettledNodes.size() != 0) {
            Vertex currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);
            for (Map.Entry<Vertex, Integer> adjacencyPair : currentNode.getNeighbours().entrySet()) {
                Vertex adjacentNode = adjacencyPair.getKey();
                Integer edgeWeight = adjacencyPair.getValue();
                if (!settledNodes.contains(adjacentNode) && !(adjacentNode.getVertexView().getVertexImage() instanceof BlackVertexImage)) {
                    calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            }
            nodes.add(currentNode);
            settledNodes.add(currentNode);
        }
        return nodes;
    }
    private Vertex getLowestDistanceNode(Set <Vertex> unsettledNodes) {
        Vertex lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (Vertex node: unsettledNodes) {
            int nodeDistance = node.getDistance();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }
    private void calculateMinimumDistance(Vertex evaluationNode, Integer edgeWeigh, Vertex sourceNode) {
        Integer sourceDistance = sourceNode.getDistance();
        if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
            evaluationNode.setDistance(sourceDistance + edgeWeigh);
            LinkedList<Vertex> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
        }
    }

    public void clear(){
        for (Vertex v : vertices) {
            v.setVertexImage(new WhiteVertexImage(v));
            v.setShortestPath(new LinkedList<>());
            v.setDistance(Integer.MAX_VALUE);
        }
    }

    public Vertex RandomVertex(){
        Vertex source = null;
        int size = vertices.size();
        Random rand = new Random();
        while(source == null) {
            int item = rand.nextInt(size);
            int i = 0;
            for (Vertex v : vertices) {
                if (i == item && (v.getVertexView().getVertexImage() instanceof WhiteVertexImage))
                    source = v;
                i++;
            }
        }
        return source;
    }

    public Vertex RandomTarget(){
        Vertex target = null;
        while(target == null){
            Vertex curr = RandomVertex();
            if(!(curr.getVertexView().getVertexImage() instanceof StartVertexImage)){
                target = curr;
            }
        }
        target.setVertexImage(new TargetVertexImage(target));
        return target;
    }

    public void RandomWalls(double p){

        for(Vertex v : vertices){
            Random rand = new Random();
            double Poss = rand.nextDouble();
            if(Poss < p){
                v.setVertexImage(new BlackVertexImage(v));
            }
        }
    }

}
