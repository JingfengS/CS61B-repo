package jingfeng.sun.DisjointSets;

public interface DisjointSets {


    /**
     * Connect two Node in the parent
     * @param p The index of the Node
     * @param q The index of another Node
     */
    public void connect(int p, int q);

    /**
     *
     * @param p The Index of one Node
     * @param q The Index of another Node
     * @return True if those two Nodes are connected
     *         false otherwise
     */
    public boolean isConnected(int p, int q);

}
