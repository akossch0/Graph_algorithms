package Graphs;

import Main.Mainframe;
import Views.*;

import java.util.*;
import java.util.concurrent.TimeUnit;


public class Graph {
    private Set<Vertex> vertices;
    private View view;
    private Mainframe frame;
    private Vertex src;
    private Vertex trgt;

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
                /*
                if(vertices.contains(new Vertex(myList[row][col])) && vertices.contains(new Vertex(myList[row][col] + m + 1))){
                    if((myList[row][col] + 1) % m != 0)
                        addEdge(myList[row][col], myList[row][col] + m + 1, 1);
                }

                if(vertices.contains(new Vertex(myList[row][col])) && vertices.contains(new Vertex(myList[row][col] + m - 1))){
                    if((myList[row][col] + 1) % m != 0)
                        addEdge(myList[row][col], myList[row][col] + m - 1, 1);
                }
                */

            }
        }
    }
    public void RestoreNeighbours(int id){
        Vertex nbor1 = getElement(id + 1);
        Vertex nbor2 = getElement(id - 1);
        Vertex nbor3 = getElement(id + 24);
        Vertex nbor4 = getElement(id - 24);

        if(nbor1 != null && !(nbor1.getVertexView().getVertexImage() instanceof BlackVertexImage))
            addEdge(nbor1.getId(), id, 1);
        if(nbor2 != null && !(nbor1.getVertexView().getVertexImage() instanceof BlackVertexImage))
            addEdge(nbor2.getId(), id, 1);
        if(nbor3 != null && !(nbor1.getVertexView().getVertexImage() instanceof BlackVertexImage))
            addEdge(nbor3.getId(), id, 1);
        if(nbor4 != null && !(nbor1.getVertexView().getVertexImage() instanceof BlackVertexImage))
            addEdge(nbor4.getId(), id, 1);
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
            boolean ImportantNode =
                    (v.getVertexView().getVertexImage() instanceof StartVertexImage ||
                            v.getVertexView().getVertexImage() instanceof TargetVertexImage);
            //if(v.getVertexView().getVertexImage() instanceof BlackVertexImage)
                //RestoreNeighbours(v.getId());
            if(!ImportantNode)
                v.setVertexImage(new WhiteVertexImage(v));
            v.setShortestPath(new LinkedList<>());
            v.setDistance(Integer.MAX_VALUE);

        }
    }

    public void clearDijkstra(){
        for(Vertex v : vertices){
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

    public Vertex RandomSource(){
        Vertex source = null;
        while(source == null){
            Vertex curr = RandomVertex();
            if(!(curr.getVertexView().getVertexImage() instanceof TargetVertexImage)){
                source = curr;
            }
        }
        source.setVertexImage(new StartVertexImage(source));
        src = source;
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
        trgt = target;
        return target;
    }

    public void RandomWalls(double p){
        for(Vertex v : vertices){
            Random rand = new Random();
            double Poss = rand.nextDouble();
            boolean ImportantNode =
                    (v.getVertexView().getVertexImage() instanceof StartVertexImage ||
                            v.getVertexView().getVertexImage() instanceof TargetVertexImage);

            if(Poss < p && !ImportantNode){
                v.setVertexImage(new BlackVertexImage(v));
                //v.getNeighbours().clear();
            }
        }
    }

    public void EasyMaze(int row, int col){
        for(int i = 0; i < row; i++){
            for(int j = 0; j < col; j++){
                Vertex v = getElement(col * i + j);
                boolean ImportantNode =
                        (v.getVertexView().getVertexImage() instanceof StartVertexImage ||
                                v.getVertexView().getVertexImage() instanceof TargetVertexImage);
                if(i % 2 == 1){
                    if(i % 4 == 1){
                        if(j < col - 1 && !ImportantNode){
                            v.setVertexImage(new BlackVertexImage(v));
                            //v.getNeighbours().clear();
                        }
                    }else if(i % 4 == 3){
                        if(j > 0 && !ImportantNode){
                            v.setVertexImage(new BlackVertexImage(v));
                            //v.getNeighbours().clear();
                        }
                    }
                }
            }
        }
        Random rand = new Random();
        for(Vertex v : vertices){
            double poss = rand.nextDouble();
            if(v.getVertexView().getVertexImage() instanceof BlackVertexImage){
                if(poss > 0.95){
                    v.setVertexImage(new WhiteVertexImage(v));
                    //RestoreNeighbours(v.getId());
                }
            }else if(v.getVertexView().getVertexImage() instanceof WhiteVertexImage){
                if(poss > 0.7){
                    v.setVertexImage(new BlackVertexImage(v));

                    if(!InTheSameComponent(src,trgt) || connectedComponents().size() > 40)
                        v.setVertexImage(new WhiteVertexImage(v));
                }
            }
        }
    }

    public void dfs(Vertex v, Map<Vertex, Boolean> visited, List<Vertex> result){
        visited.put(v, true);
        if(!(v.getVertexView().getVertexImage() instanceof BlackVertexImage)) {
            result.add(v);
        }
        //System.out.print(v.getId()+" ");

        for(Vertex vert : v.getNeighbours().keySet()){
            if(!visited.get(vert) && !(v.getVertexView().getVertexImage() instanceof BlackVertexImage))
                dfs(vert, visited, result);
        }
    }

    public List<List<Vertex>> connectedComponents() {
        Map<Vertex, Boolean> visited = new HashMap<>();
        for(Vertex v : vertices){
            visited.put(v,false);
        }

        List<List<Vertex>> components = new ArrayList<>();
        for(Vertex v : visited.keySet()){
            List<Vertex> comp = new ArrayList<>();
            if(!visited.get(v) && !(v.getVertexView().getVertexImage() instanceof BlackVertexImage)){
                dfs(v,visited,comp);
                components.add(comp);
                //System.out.println();
            }
        }
        return components;
    }

    public boolean InTheSameComponent(Vertex v1, Vertex v2){
        List<List<Vertex>> components = connectedComponents();
        for(List<Vertex> comp : components){
            if(comp.contains(v1) && comp.contains(v2))
                return true;
        }
        return false;
    }


}
