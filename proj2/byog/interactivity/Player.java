package byog.interactivity;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import byog.helpers.BlockHelper;
import edu.princeton.cs.algs4.StdRandom;

public class Player extends AbstractCreature {
    public Player(TETile[][] wor) {
        world = wor;
        int width = world.length;
        int height = world[0].length;
        xx = StdRandom.uniform(width);
        yy = StdRandom.uniform(height);
        while (!BlockHelper.isFloor(xx, yy, world)) {
            xx = StdRandom.uniform(width);
            yy = StdRandom.uniform(height);
        }
        standOn = world[xx][yy];
        health = 3;
        self = Tileset.PLAYER;
        world[xx][yy] = self;
    }
    @Override
    public TETile[][] action(char keyPress) {
        if (keyPress == 'w') {
            world = moveUp();
        } else if (keyPress == 'a') {
            world = moveLeft();
        } else if (keyPress == 's') {
            world = moveDown();
        } else if (keyPress == 'd') {
            world = moveRight();
        }
        return world;
    }
}
