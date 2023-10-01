package byog.world;

public class Node implements TreeNode {
    private int left;
    private int right;
    private int up;
    private int down;
    private Node leftNode;
    private Node rightNode;

    public Node(int le, int ri, int dow, int u, Node leftN, Node rightN) {
        left = le;
        right = ri;
        up = u;
        down = dow;
        leftNode = leftN;
        rightNode = rightN;
    }

    @Override
    public int getLeft() {
        return left;
    }

    @Override
    public int getRight() {
        return right;
    }

    @Override
    public int getUp() {
        return up;
    }

    @Override
    public int getDown() {
        return down;
    }

    @Override
    public Node getLeftNode() {
        return leftNode;
    }

    @Override
    public Node getRightNode() {
        return rightNode;
    }

    @Override
    public boolean isLeaf() {
        return leftNode == null && rightNode == null;
    }

    @Override
    public void addNodes(TreeNode nodeLeft, TreeNode nodeRight) {
        if (!isLeaf()) {
            throw new RuntimeException("This node is not a leaf!");
        }
        leftNode = (Node) nodeLeft;
        rightNode = (Node) nodeRight;
    }


    /**
     * @return if this node is splitable
     */
    public boolean isSplitable() {
        return getRight() - getLeft() >= 4 && getUp() - getDown() >= 4 && isLeaf();
    }
}
