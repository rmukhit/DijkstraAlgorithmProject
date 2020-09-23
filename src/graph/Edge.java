package graph;

/** Edge class represents a link in the linked list of edges for a vertex.
 *  Each Edge stores the id of the "neighbor" (the vertex where this edge is going =
 *  "destination" vertex), the cost and the reference to the next Edge.
 */
class Edge {
    private int neighbor; // id of the neighbor ("destination" vertex of this edge)
	private int cost; // cost of this edge
	private Edge next; // reference to the next "edge" in the linked list


    /** Edge class constructor
     * @param neighbor destination vertex
     * @param cost cost
     */
	public Edge(int neighbor, int cost) {
	    this.neighbor = neighbor;
	    this.cost = cost;
	    this.next = null;
    }

    /** Getter
     * @return int neighbour
     */
    public int getNeighbor() {
	    return this.neighbor;
    }

    /** Getter
     * @return int cost
     */
    public int getCost() {
        return this.cost;
    }

    /** Getter
     * @return Edge next
     */
    public Edge getNext() {
        return this.next;
    }

    /** Setter
     * @param neighbor
     */
    public void setNeighbor(int neighbor) {
        this.neighbor = neighbor;
    }

    /** Setter
     * @param cost
     */
    public void setCost(int cost) {
        this.cost = cost;
    }

    /** Setter
     * @param next
     */
    public void setNext(Edge next) {
        this.next = next;
    }

}