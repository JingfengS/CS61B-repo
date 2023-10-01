package byog.world;

public class Node implements TreeNode {
    private int left;
    private int right;
    private int up;
    private int down;
    private Node leftNode;
    private Node rightNode;

    public Node(int le, int ri, int u, int dow, Node leftN, Node rightN) {
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
    public TreeNode getLeftNode() {
        return leftNode;
    }

    @Override
    public TreeNode getRightNode() {
        return rightNode;
    }
}
