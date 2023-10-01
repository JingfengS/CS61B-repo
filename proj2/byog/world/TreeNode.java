package byog.world;

/**
 * A kind of data structure node that stores the information of a
 * rectangle in the graph
 */
public interface TreeNode {
    /**
     * @return the left Bound of the rectangle
     */
    public int getLeft();

    /**
     * @return the right Bound of the rectangle
     */
    public int getRight();

    /**
     * @return the up Bound of the rectangle
     */
    public int getUp();

    /**
     * @return the down Bound of the rectangle
     */
    public int getDown();

    /**
     * @return the left subnode of the node
     */
    public TreeNode getLeftNode();

    /**
     * @return the right subnode of the node
     */
    public TreeNode getRightNode();

    /**
     * @return true if the node is a leaf
     */
    public boolean isLeaf();

    /**
     * Add the left node and right node so it won't be null
     * This method is used for leaf
     * @param nodeLeft
     */
    public void addNodes(TreeNode nodeLeft, TreeNode nodeRight);

}
