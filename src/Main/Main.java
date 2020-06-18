package Main;

import Graphs.Graph;

public class Main {

    public static void main(String[] args) {
	//beginning
        //Mainframe.Run(args);
        Graph g = new Graph();
        g.generateGraph(3,6);
        g.printGraph();
    }
}
