package graph;

/** A priority queue: implemented using a min heap.
 *  You may not use any Java built-in classes, you should implement
 *  PriorityQueue yourself. You may use/modify the MinHeap code posted
 *  by the instructor under Examples, as long as you understand it. */
public class PriorityQueue {
	QueueElement[] queue;
	int maxsize;
	int size;
	int[] positions;

	/** Constructor for PriorityQueue class
	 *
	 * */
	public PriorityQueue(int max) {
		this.queue = new QueueElement[max];
		this.maxsize = max;
		this.size = 0;
		this.positions = new int[max-1];
		for(int i = 0; i < positions.length; i++) {
			positions[i] = -1;
		}
		this.queue[0] = new QueueElement(-1, Integer.MIN_VALUE);
	}

	/** QueueElement class
	 * */
	public class QueueElement {
		int nodeId;
		int priority;

		/** Simple constructor for QueueElement class
		 * */
		public QueueElement(int nodeId, int priority) {
			this.nodeId = nodeId;
			this.priority = priority;
		}
	}

	/** Return the index of the left child of the element at index pos
	 *
	 * @param pos the index of the element in the heap array
	 * @return the index of the left child
	 */
	private int leftChild(int pos) {
		return 2 * pos;
	}

	/** Return the index of the parent
	 *
	 * @param pos the index of the element in the heap array
	 * @return the index of the parent
	 */
	private int parent(int pos) {
		return pos / 2;
	}

	/** Returns true if the node in a given position is a leaf
	 *
	 * @param pos the index of the element in the heap array
	 * @return true if the node is a leaf, false otherwise
	 */
	private boolean isLeaf(int pos) {
		return ((pos > size / 2) && (pos <= size));
	}

	/** Swap given elements: one at index pos1, another at index pos2
	 *
	 * @param pos1 the index of the first element in the heap
	 * @param pos2 the index of the second element in the heap
	 */
	private void swap(int pos1, int pos2) {
		QueueElement tmp;
		tmp = queue[pos1];
		queue[pos1] = queue[pos2];
		queue[pos2] = tmp;
	}


	/** Insert a new element (nodeId, priority) into the heap.
     *  For this project, the priority is the current "distance"
     *  for this nodeId in Dikstra's algorithm.
	 * @param nodeId id of the city
	 * @param priority cost
	 *  */
	public void insert(int nodeId, int priority) {
		size++;
		queue[size] = new QueueElement(nodeId, priority);
		int current = size;
		while (queue[current].priority < queue[parent(current)].priority) {
			swap(current, parent(current));
			current = parent(current);
		}

	}

    /**
     * Remove the element with the minimum priority
     * from the min heap and return its nodeId.
     * @return nodeId of the element with the smallest priority
     */
	public int removeMin() {
		swap(1, size); // swap the end of the heap into the root
		size--;
		if (size != 0) {
			pushdown(1);
		}
		return queue[size+1].nodeId;
	}


	public void pushdown(int position) {
		int smallestchild;
		while (!isLeaf(position)) {
			smallestchild = leftChild(position); // set the smallest child to left child
			if ((smallestchild < size) && (queue[smallestchild].priority > queue[smallestchild + 1].priority))
				smallestchild = smallestchild + 1; // right child was smaller, so smallest child = right child

			// the value of the smallest child is less than value of current,
			// the heap is already valid
			if (queue[position].priority <= queue[smallestchild].priority) {
				return;
			}
			swap(position, smallestchild);
			position = smallestchild;

		}
	}
    /**
     * Reduce the priority of the element with the given nodeId to newPriority.
     * You may assume newPriority is less or equal to the current priority for this node.
     * @param nodeId id of the node
     * @param newPriority new value of priority
     */
	public void reduceKey(int nodeId, int newPriority) {
		int id = 0;
		// find it's location in queue
		for (int i = 0; i < queue.length; i++) {
			if (queue[i].nodeId == nodeId) {
				id = i;
			}
		}
		queue[id] = new QueueElement(nodeId, newPriority);
		while(queue[id].priority < queue[parent(id)].priority) {
			swap(id, parent(id));
			id = parent(id);
		}

	}

}

