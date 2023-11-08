package byog.world.tests;

import byog.TileEngine.TERenderer;
import byog.world.World;

public class TestWorld {
    public static void main(String[] args) {
        final int WIDTH = 80;
        final int HEIGHT = 50;
        World w = new World(850, WIDTH, HEIGHT);
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        ter.renderFrame(w.getWorld());
    }
}
