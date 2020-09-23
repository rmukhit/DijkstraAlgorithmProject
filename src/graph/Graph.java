package graph;

/**
 * A class that represents a graph where nodes are cities (of type CityNode)
 * and edges connect them and the cost of each edge is the distance between
 * the cities.
 * Fill in code in this class. You may add additional methods and variables.
 * You are required to implement a HashTable and a PriorityQueue from scratch.
 */
import java.util.*;
import java.io.*;
import java.awt.Point;

public class Graph {
    public final int EPS_DIST = 5;

    private CityNode[] nodes; // nodes of the graph
	private int numNodes;     // total number of nodes
	private int numEdges; // total number of edges
	private Edge[] adjacencyList; // adjacency list; for each vertex stores a linked list of edges
	private HashTable table;
	// Your HashTable that maps city names to node ids should probably be here as well

	/**
	 * Read graph info from the given file, and create nodes and edges of
	 * the graph.
	 *
	 * @param filename name of the file that has nodes and edges
	 */
	public void loadGraph(String filename) {

		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
			br.readLine();
			int numberOfNodes = Integer.parseInt(br.readLine());
			table = new HashTable(numberOfNodes * 2);
			nodes = new CityNode[numberOfNodes];
			adjacencyList = new Edge[numberOfNodes];
			numNodes = 0;
			numEdges = 0;
			int count = 0;
			while (count != numberOfNodes) {
				String string = br.readLine();
				String[] temp = string.split(" ");
				CityNode newCity = new CityNode(temp[0], Double.parseDouble(temp[1]), Double.parseDouble(temp[2]));
				table.put(temp[0], count);
				addNode(newCity);
				count++;
			}

			br.readLine();
			String string;
			while ((string = br.readLine()) != null) {
				String[] temp = string.split(" ");
				Edge firstEdge = new Edge(table.get(temp[0]), Integer.parseInt(temp[2]));
				addEdge(table.get(temp[1]),firstEdge);
				Edge secondEdge = new Edge(table.get(temp[1]), Integer.parseInt(temp[2]));
				addEdge(table.get(temp[0]),secondEdge);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Add a node to the array of nodes.
	 * Increment numNodes variable.
     * Called from loadGraph.
	 *
	 * @param node a CityNode to add to the graph
	 */
	public void addNode(CityNode node) {
		nodes[numNodes] = node;
		numNodes++;
	}

	/**
	 * Return the number of nodes in the graph
	 * @return number of nodes
	 */
	public int numNodes() {
		return numNodes;
	}

	public int numEdges() {
		return numEdges;
	}
	/**
	 * Adds the edge to the linked list for the given nodeId
	 * Called from loadGraph.
     *
	 * @param nodeId id of the node
	 * @param edge edge to add
	 */
	public void addEdge(int nodeId, Edge edge) {

		if (adjacencyList[nodeId] == null) {
			adjacencyList[nodeId] = edge;
		} else {
			Edge current = adjacencyList[nodeId];
			if (current.getNeighbor() > edge.getNeighbor()) {
				edge.setNext(current);
				adjacencyList[nodeId] = edge;
			} else {
				while (current.getNext() != null && current.getNext().getNeighbor() < edge.getNeighbor()) {
					current = current.getNext();

				}
				edge.setNext(current.getNext());
				current.setNext(edge);
			}

		}
		numEdges++;

	}


	/**
	 * Returns an integer id of the given city node
	 * @param city node of the graph
	 * @return its integer id
	 */
	public int getId(CityNode city) {
		return table.get(city.getCity());
    }

	/**
	 * Return the edges of the graph as a 2D array of points.
	 * Called from GUIApp to display the edges of the graph.
	 *
	 * @return a 2D array of Points.
	 * For each edge, we store an array of two Points, v1 and v2.
	 * v1 is the source vertex for this edge, v2 is the destination vertex.
	 * This info can be obtained from the adjacency list
	 */
	public Point[][] getEdges() {
		int i = 0;
		Point[][] edges2D = new Point[numEdges][2];
		for (int j = 0; j < numNodes; j++) {
				Edge temp = adjacencyList[j];
				while(temp != null) {
					edges2D[i][0] = nodes[j].getLocation();
					edges2D[i][1] = nodes[temp.getNeighbor()].getLocation();
					temp = temp.getNext();
					i++;
				}
		}


		return edges2D;
	}

	/**
	 * Get the nodes of the graph as a 1D array of Points.
	 * Used in GUIApp to display the nodes of the graph.
	 * @return a list of Points that correspond to nodes of the graph.
	 */
	public Point[] getNodes() {
	    if (this.nodes == null) {
            System.out.println("Graph has no nodes. Write loadGraph method first. ");
            return null;
        }
		Point[] pnodes = new Point[this.nodes.length];
		for (int i = 0; i < nodes.length; i++) {
			pnodes[i] = nodes[i].getLocation();
		}

		return pnodes;
	}

	/**
	 * Used in GUIApp to display the names of the airports.
	 * @return the list that contains the names of cities (that correspond
	 * to the nodes of the graph)
	 */
	public String[] getCities() {
        if (this.nodes == null) {
            System.out.println("Graph has no nodes. Write loadGraph method first. ");
            return null;
        }
		String[] labels = new String[nodes.length];
		for(int i = 0; i < nodes.length; i++) {
			labels[i] = nodes[i].getCity();
		}
		return labels;

	}

	/** Take a list of node ids on the path and return an array where each
	 * element contains two points (an edge between two consecutive nodes)
	 * @param pathOfNodes A list of node ids on the path
	 * @return array where each element is an array of 2 points
	 */
	public Point[][] getPath(List<Integer> pathOfNodes) {
		int i = 0;
		Point[][] edges2D = new Point[pathOfNodes.size() - 1][2];

		for (int row = 0; row < pathOfNodes.size() - 1; row++) {
			edges2D[row][0] = nodes[pathOfNodes.get(i)].getLocation();
			edges2D[row][1] = nodes[pathOfNodes.get(i + 1)].getLocation();
			i++;
		}

		return edges2D;

	}

	/**
	 * Return the CityNode for the given nodeId
	 * @param nodeId id of the node
	 * @return CityNode
	 */
	public CityNode getNode(int nodeId) {
		return nodes[nodeId];
	}

	/**
	 * Return the adjacency list
	 * @return adjacencyList
	 */
	public Edge[] getAdjacencyList() {
		return this.adjacencyList;
	}

	/**
	 * Take the location of the mouse click as a parameter, and return the node
	 * of the graph at this location. Needed in GUIApp class.
	 * @param loc the location of the mouse click
	 * @return reference to the corresponding CityNode
	 */
	public CityNode getNode(Point loc) {
		for (CityNode v : nodes) {
			Point p = v.getLocation();
			if ((Math.abs(loc.x - p.x) < EPS_DIST) && (Math.abs(loc.y - p.y) < EPS_DIST))
				return v;
		}
		return null;
	}
}