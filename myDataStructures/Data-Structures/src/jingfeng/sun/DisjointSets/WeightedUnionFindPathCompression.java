package jingfeng.sun.DisjointSets;

public class WeightedUnionFindPathCompression<T> implements DisjointSets {

    private class Node<T> implements Connectable<T> {

        /** The index of its upper Node in parent array */
        private int upperNode = -1;

        /** The information that this Node contains */
        private T item;

        /** The constructor of the node */
        Node(T it) {
            item = it;
        }

        /**
         * @return item The information in the node
         */
        @Override
        public T getItem() {
            return item;
        }
    }

    private Node<T>[] parent;


    /**
     * Initialize the Disjoint Sets
     * Note this implementation has path compression builtin
     * which means it is really fast!
     * @param capacity The Node parent contains
     * @param item The information contains in each Node
     */
    public WeightedUnionFindPathCompression(int capacity, T item) {
        parent = new Node[capacity];
        for (int i = 0; i < capacity; i += 1) {
            parent[i] = new Node<T>(item);
        }
    }

    /**
     * @param p The index of the Node in parent
     * @return The root index of the Node p
     */
    private int findHelper(int p) {
        if (parent[p].upperNode < 0) {
            return p;
        }
        return findHelper(parent[p].upperNode);
    }

    /**
     * Return the root index of the Node p
     * And compression all the Nodes to be height of 1 along the path
     * @param p The index of Node p in parent
     * @return The root index of the Node p
     */
    private int find(int p) {
        int root = findHelper(p);
        if (parent[p].upperNode == root || parent[p].upperNode < 0) {
            return root;
        }
        int upper = parent[p].upperNode;
        parent[p].upperNode = root;
        return find(upper);
    }


    @Override
    public void connect(int p, int q) {
        if (isConnected(p, q)) {
            return;
        }
        int p1 = find(p);
        int q1 = find(q);
        Node<T> rootP = parent[p1];
        Node<T> rootQ = parent[q1];
        if (rootP.upperNode >= rootQ.upperNode) {
            rootQ.upperNode += rootP.upperNode;
            rootP.upperNode = q1;
        } else {
            rootP.upperNode += rootQ.upperNode;
            rootQ.upperNode = p1;
        }
    }

    @Override
    public boolean isConnected(int p, int q) {
        return find(p) == find(q);
    }
}
