package byog.interactivity.tests;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.interactivity.Player;
import byog.world.World;

public class TestMoveLeft {
    public static void main(String[] args) {
        int WIDTH = 70;
        int HEIGHT = 50;
        World w = new World(888, WIDTH, HEIGHT);
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] world = w.getWorld();
        Player p1 = new Player(world);
        p1.moveLeft();

        ter.renderFrame(world);
    }
}
