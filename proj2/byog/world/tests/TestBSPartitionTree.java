package byog.world.tests;

import byog.world.BSPartitionTree;
import byog.world.Node;
import byog.world.TreeNode;
import org.junit.Test;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;

public class TestBSPartitionTree {
    @Test
    public void testNode() {
        Node n = new Node(0, 10, 0, 10, null, null);
        assertThat(n.isLeaf()).isTrue();
        assertThat(n.isSplitable()).isTrue();
        Node n1 = new Node(0, 3, 0, 4, null, null);
        assertThat(n1.isLeaf()).isTrue();
        assertThat(n1.isSplitable()).isFalse();
        Node n2 = new Node(0, 100, 0, 100, n, n1);
        assertThat(n2.isLeaf()).isFalse();
        assertThat(n2.isSplitable()).isFalse();
    }
    @Test
    public void testSplit() {
        BSPartitionTree bsp = new BSPartitionTree(10, 10, 100);
        bsp.split(bsp.getRoot());
        BSPartitionTree bsp2 = new BSPartitionTree(4, 4, 100);
        bsp2.split(bsp2.getRoot());
        BSPartitionTree bsp3 = new BSPartitionTree(6, 6, 100);
        bsp3.split(bsp3.getRoot());
        BSPartitionTree bsp4 = new BSPartitionTree(4, 10, 100);
        bsp4.split(bsp4.getRoot());

    }

    @Test
    public void testSplitToEnd() {
        BSPartitionTree bsp1 = new BSPartitionTree(20, 20, 300);
        bsp1.splitToEnd();
        BSPartitionTree bsp2 = new BSPartitionTree(20, 20, 400);
        bsp2.splitToEnd();
        BSPartitionTree bsp3 = new BSPartitionTree(100, 100, 300);
        bsp3.splitToEnd();
        BSPartitionTree bsp4 = new BSPartitionTree(20, 20, 300);
        bsp4.splitToEnd();
    }

    @Test
    public void testGetLeaves() {
        BSPartitionTree bsp1 = new BSPartitionTree(20, 20, 300);
        bsp1.splitToEnd();
        List<TreeNode> leaves = bsp1.getLeaves();
    }
}
