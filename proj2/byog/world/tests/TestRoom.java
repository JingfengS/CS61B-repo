package byog.world.tests;


import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import byog.world.Node;
import byog.world.Room;
import org.junit.Test;
import static com.google.common.truth.Truth.assertThat;

public class TestRoom {
//    @Test
//    public void testRoom() {
//    Node n = new Node(0, 5, 0, 5, null, null);
//    Room r = new Room(n);
//    }

    public static void main(String[] args) {
        final int WIDTH = 10;
        final int HEIGHT = 10;
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = new TETile[WIDTH][HEIGHT];

        for (int i = 0; i < WIDTH; i += 1) {
            for (int j = 0; j < HEIGHT; j += 1) {
                world[i][j] = Tileset.NOTHING;
            }
        }
        Node n = new Node(0, 8, 0, 8, null, null);
        Room r = new Room(n);
        r.drawRoom(world);
        ter.renderFrame(world);
    }
}
