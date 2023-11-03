package byog.world.tests;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import byog.world.Node;
import byog.world.Room;

import javax.swing.tree.AbstractLayoutCache;

public class testHallway {
    public static void main(String[] args) {
        final int WIDTH = 20;
        final int HEIGHT = 20;
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = new TETile[WIDTH][HEIGHT];

        for (int i = 0; i < WIDTH; i += 1) {
            for (int j = 0; j < HEIGHT; j += 1) {
                world[i][j] = Tileset.NOTHING;
            }
        }
        Node n1 = new Node(0, 8, 0, 8, null, null);
        Node n2 = new Node(10, 20, 10, 20, null, null);
        Room r1 = new Room(n1);
        Room r2 = new Room(n2);
        r1.drawRoom(world);
        r2.drawRoom(world);
        ter.renderFrame(world);
    }
}
