package byog.world;

import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BSPartitionTree implements BSPTree {
    private Node root;
    private final Random Random;
    private List<TreeNode> leaves;

    public BSPartitionTree(int WIDTH, int HEIGHT, long seed) {
        Random = new Random(seed);
        StdRandom.setSeed(seed);
        root = new Node(0, WIDTH - 1, 0, HEIGHT - 1, null, null);
        leaves = new ArrayList<>();
    }

    private boolean isVertical() {
        return Random.nextBoolean();
    }

    private boolean isAcceptable(int lower, int pivot, int upper) {
        return pivot - lower >= 4 && upper - pivot >= 5;
    }


    /**
     * Todo make this method private after testing
     */
    public TreeNode split(TreeNode node) {
        if (!node.isLeaf()) {
            throw new RuntimeException("This node is not a leaf!");
        }
        Node nodeLeft;
        Node nodeRight;
        int maxLoopTimes = 50;
        if (isVertical()) {
            int pivot = StdRandom.uniform(node.getDown(), node.getUp() + 1);
            int tries = 0;
            while (!isAcceptable(node.getDown(), pivot, node.getUp()) && tries < maxLoopTimes) {
                pivot = StdRandom.uniform(node.getDown(), node.getUp() + 1);
                tries += 1;
            }
            if (isAcceptable(node.getDown(), pivot, node.getUp())) {
                nodeLeft = new Node(node.getLeft(), node.getRight(), node.getDown(), pivot, null, null);
                nodeRight = new Node(node.getLeft(), node.getRight(), pivot + 1, node.getUp(), null, null);
            } else {
                nodeLeft = null;
                nodeRight = null;
            }
        } else {
            int pivot = StdRandom.uniform(node.getLeft(), node.getRight() + 1);
            int tries = 0;
            while (!isAcceptable(node.getLeft(), pivot, node.getRight()) && tries < maxLoopTimes) {
                pivot = StdRandom.uniform(node.getLeft(), node.getRight() + 1);
                tries += 1;
            }
            if (isAcceptable(node.getLeft(), pivot, node.getRight())) {
                nodeLeft = new Node(node.getLeft(), pivot, node.getDown(), node.getUp(), null, null);
                nodeRight = new Node(pivot + 1, node.getRight(), node.getDown(), node.getUp(), null, null);
            } else {
                nodeLeft = null;
                nodeRight = null;
            }
        }
        node.addNodes(nodeLeft, nodeRight);
        return node;
    }


    private void getLeafHelper(Node node, List<TreeNode> leavesAll) {
        if (node.isLeaf()) {
            leavesAll.add(node);
            return;
        }
        getLeafHelper(node.getLeftNode(), leavesAll);
        getLeafHelper(node.getRightNode(), leavesAll);
    }
    @Override
    public List<TreeNode> getLeaves() {
        getLeafHelper(root, leaves);
        return leaves;
    }

    private Node splitToEndHelper(Node node) {
        if (!node.isSplitable()) {
            return node;
        }
        split(node);
        if (node.getLeftNode() != null && node.getRightNode() != null) {
            splitToEndHelper(node.getLeftNode());
            splitToEndHelper(node.getRightNode());
        }
        return node;
    }

    @Override
    public TreeNode splitToEnd() {
        return splitToEndHelper(root);
    }

    @Override
    public TreeNode getRoot() {
        return root;
    }
}
