package byog.world.tests;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.world.World;

public class testWorld {
    public static void main(String[] args) {
        final int WIDTH = 80;
        final int HEIGHT = 50;
        World w = new World(650, WIDTH, HEIGHT);
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        ter.renderFrame(w.getWorld());
    }
}
