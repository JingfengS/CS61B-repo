package byog.world;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

public class Rooms {
    private final int WIDTH;
    private final int HEIGHT;
    private List<Room> rooms;
    private long seed;

    public Rooms(int width, int height, long se) {
        WIDTH = width;
        HEIGHT = height;
        rooms = new ArrayList<>();
        seed = se;
        List<TreeNode> nodes;
        BSPartitionTree bsp = new BSPartitionTree(WIDTH, HEIGHT, seed);
        bsp.splitToEnd();
        nodes = bsp.getLeaves();
        for (TreeNode node : nodes) {
            rooms.add(new Room(node));
        }
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public long getSeed() {
        return seed;
    }
}
