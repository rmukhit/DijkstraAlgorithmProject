package graph;

/** Custom implementation of a hash table using open hashing, separate chaining.
 *  Each key is a String (name of the city), each value is an integer (node id). */
public class HashTable {

    int sizeOfHashTable;
    HashElement[] elements;

     public HashTable(int size) {
         this.sizeOfHashTable = size;
         this.elements = new HashElement[size];
     }

    /** Private class HashElement */
    private class HashElement {
        String key;
        int value;
        HashElement next;

        public HashElement(String key, int value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    /**
     * gets hash of a key
     *
     * @param key a CityNode to add to the graph
     */
    private int hash(String key) {
        int hashCode = Math.abs(key.hashCode());
        return hashCode % sizeOfHashTable;
    }

    /**
     * Function that inserts key and value to hashtable
     *
     * @param key a CityNode to add to the graph
     * @param value a CityNode to add to the graph
     */
    public void put(String key, int value) {
        HashElement hashElement = new HashElement(key, value);
        int hash = hash(key);
        if(elements[hash] == null) {
            elements[hash] = hashElement;
        } else {
            hashElement.next = elements[hash];
            elements[hash] = hashElement;
        }
    }

    /**
     * A function that returns the value of the key
     *
     * @param key a CityNode to add to the graph
     * @return int the value of key
     */
    public int get(String key) {
        int hash = hash(key);
        if(elements[hash] != null) {
            HashElement temp = elements[hash];
            while (temp != null) {
                if (temp.key.equals(key)) {
                    return temp.value;
                }
                temp = temp.next;
            }
        }

        return -1;
    }


}