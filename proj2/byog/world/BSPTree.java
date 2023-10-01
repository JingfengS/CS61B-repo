package byog.world;

import java.util.List;

public interface BSPTree {
    /**
     * @return A list of Leaf Node that can not be partitioned
     */
    public List<TreeNode> getLeaves();

    /**
     * Split the given node into Nodes that can not be partitioned
     * @return The partitioned Node itself
     */
    public TreeNode splitToEnd();

    /**
     * @return The root of the whole tree
     */
    public TreeNode getRoot();


}
