package graph;

/** Class Dijkstra. Implementation of Dijkstra's
 *  algorithm on the graph for finding the shortest path.
 *  Fill in code. You may add additional helper methods or classes.
 */

import java.util.*;
import java.awt.Point;

public class Dijkstra {
	private Graph graph; // stores the graph of CityNode-s and edges connecting them
    private List<Integer> shortestPath = null; // nodes that are part of the shortest path

    /** Constructor
	 *
	 * @param filename name of the file that contains info about nodes and edges
     * @param graph graph
	 */
	public Dijkstra(String filename, Graph graph) {
	    this.graph = graph;
		graph.loadGraph(filename);
	}

	/**
	 * Returns the shortest path between the origin vertex and the destination vertex.
	 * The result is stored in shortestPathEdges.
	 * This function is called from GUIApp, when the user clicks on two cities.
	 * @param origin source node
	 * @param destination destination node
     * @return the ArrayList of nodeIds (of nodes on the shortest path)
	 */
	public List<Integer> computeShortestPath(CityNode origin, CityNode destination) {

		PriorityQueue queue = new PriorityQueue(graph.numNodes()+1);

		// initialize dijkstra table
		int[] path = new int[graph.numNodes()];
		int[] costDijkstra = new int[graph.numNodes()];

		for (int i = 0; i < costDijkstra.length; i++) {
			costDijkstra[i] = Integer.MAX_VALUE;
		}

		// insert all cities to queue with max cost
		for (int i = 0; i < graph.numNodes(); i++) {
			queue.insert(i, Integer.MAX_VALUE);
		}

		int current = graph.getId(origin);
		int originId = graph.getId(origin);

		// change cost to origin City to 0
		queue.reduceKey(current, 0);
		costDijkstra[current] = 0;
		// set it's path to -1
		path[current] = -1;

		Edge min = graph.getAdjacencyList()[current];
		int removed = 0;
		while(removed != graph.numNodes()+1) {
			while (min != null) {
				int costToVertex;
				if (path[current] == -1) {
					costToVertex = 0;
				} else {
					costToVertex = costDijkstra[current];
				}
				if (costToVertex + min.getCost() < costDijkstra[min.getNeighbor()]) {
					path[min.getNeighbor()] = current;
					costDijkstra[min.getNeighbor()] = costToVertex + min.getCost();
					queue.reduceKey(min.getNeighbor(), costDijkstra[min.getNeighbor()]);
				}
				min = min.getNext();
			}
			current = queue.removeMin();
			min = graph.getAdjacencyList()[current];
			removed++;
		}

		//initialize shortestPath and add path of Nodes from path[] array
		shortestPath = new ArrayList<>();

		int connectVertex = graph.getId(destination);
		shortestPath.add(connectVertex);
		while (connectVertex != originId) {
			shortestPath.add(path[connectVertex]);
			connectVertex = path[connectVertex];
		}

	    return shortestPath;
    }

    /**
     * Return the shortest path as a 2D array of Points.
     * Each element in the array is another array that has 2 Points:
     * these two points define the beginning and end of a line segment.
     * @return 2D array of points
     */
    public Point[][] getPath() {
        if (shortestPath == null)
            return null;
        return graph.getPath(shortestPath); // delegating this task to the Graph class
    }

    /** Set the shortestPath to null.
     *  Called when the user presses Reset button.
     */
    public void resetPath() {

    	shortestPath = null;
    }

}