package jingfeng.sun.DisjointSets;

public class WeightedQuickUnion<T> implements DisjointSets {
    private class Node<T> implements Connectable<T> {

        /**
         * The index of its upper Node in parent array
         */
        private int upperNode = -1;

        /**
         * The information that this Node contains
         */
        private T item;

        /**
         * The constructor of the node
         */
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
     * Initialize the WeightedQuickUnion Disjoint Sets
     * the information each Node contains is item
     *
     * @param capacity The number of Nodes in the parent
     * @param item     The information that each Node contains
     */
    public WeightedQuickUnion(int capacity, T item) {
        parent = new Node[capacity];
        for (int i = 0; i < capacity; i += 1) {
            parent[i] = new Node<T>(item);
        }
    }

    /**
     * Return the root of the Node
     *
     * @param p The index of Node in parent
     * @return The root index of the Node
     */
    private int find(int p) {
        if (parent[p].upperNode < 0) {
            return p;
        }
        return find(parent[p].upperNode);
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

