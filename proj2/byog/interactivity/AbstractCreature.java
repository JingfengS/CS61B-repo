package byog.interactivity;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import byog.helpers.BlockHelper;

public abstract class AbstractCreature implements Creature {
    protected int health = 1;
    protected TETile[][] world;
    protected int xx;
    protected int yy;
    protected TETile standOn;
    protected TETile self;



    protected char nonKeyPress = '*';

    protected boolean hasWallUp() {
        return world[xx][yy + 1].equals(Tileset.WALL) ||
                world[xx][yy + 1].equals(Tileset.LOCKED_DOOR);
    }

    protected boolean hasWallDown() {
        return world[xx][yy - 1].equals(Tileset.WALL) ||
                world[xx][yy - 1].equals(Tileset.LOCKED_DOOR);
    }

    protected boolean hasWallLeft() {
        return world[xx - 1][yy].equals(Tileset.WALL) ||
                world[xx - 1][yy].equals(Tileset.LOCKED_DOOR);
    }

    protected boolean hasWallRight() {
        return world[xx + 1][yy].equals(Tileset.WALL) ||
                world[xx + 1][yy].equals(Tileset.LOCKED_DOOR);
    }

    @Override
    public TETile[][] initialize() {
        return world;
    }

    @Override
    public TETile getStandOn() {
        return standOn;
    }

    @Override
    public int getX() {
        return xx;
    }

    @Override
    public int getY() {
        return yy;
    }

    @Override
    public TETile[][] moveLeft() {
        if (hasWallLeft()) {
            return world;
        }
        BlockHelper.setBlock(xx, yy, world, standOn);
        xx -= 1;
        standOn = BlockHelper.getBlock(xx, yy, world);
        BlockHelper.setBlock(xx, yy, world, self);
        return world;
    }

    @Override
    public TETile[][] moveRight() {
        if (hasWallRight()) {
            return world;
        }
        BlockHelper.setBlock(xx, yy, world, standOn);
        xx += 1;
        standOn = BlockHelper.getBlock(xx, yy, world);
        BlockHelper.setBlock(xx, yy, world, self);
        return world;
    }

    @Override
    public TETile[][] moveUp() {
        if (hasWallUp()) {
            return world;
        }
        BlockHelper.setBlock(xx, yy, world, standOn);
        yy += 1;
        standOn = BlockHelper.getBlock(xx, yy, world);
        BlockHelper.setBlock(xx, yy, world, self);
        return world;
    }

    @Override
    public TETile[][] moveDown() {
        if (hasWallDown()) {
            return world;
        }
        BlockHelper.setBlock(xx, yy, world, standOn);
        yy -= 1;
        standOn = BlockHelper.getBlock(xx, yy, world);
        BlockHelper.setBlock(xx, yy, world, self);
        return world;
    }
}
