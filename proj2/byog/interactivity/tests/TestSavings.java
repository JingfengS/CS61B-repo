package byog.interactivity.tests;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.interactivity.Player;
import byog.interactivity.Savings;
import byog.world.World;

public class TestSavings {
    public static void main(String[] args) {
        int WIDTH = 70;
        int HEIGHT = 50;
        World w = new World(800, WIDTH, HEIGHT);
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        Savings saves = new Savings();
        saves.save(w, "hello");
        TETile[][] wo = saves.getSavings().get("hello").getWorld().getWorld();
        Player p = saves.getSavings().get("hello").getWorld().getPlayer();
        ter.renderFrame(wo);
    }
}
