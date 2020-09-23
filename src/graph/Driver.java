package graph;

import java.util.ArrayList;
import java.util.List;

/** The Driver class for project Dijkstra */
public class Driver {
    public static void main(String[] args) {

        // Initialize a graph
        Graph graph = new Graph();
        // Create an instance of the Dijkstra class
        Dijkstra dijkstra = new Dijkstra("USA.txt", graph);
        // Create a graphical user interface and wait for user to click
        // on two cities:
        GUIApp app = new GUIApp(dijkstra, graph);


    }
}
