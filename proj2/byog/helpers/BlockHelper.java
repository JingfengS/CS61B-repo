package byog.helpers;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class BlockHelper {
    public static boolean isFloor(int x, int y, TETile[][] world) {
        return world[x][y].equals(Tileset.FLOOR);
    }

    public static boolean isWall(int x, int y, TETile[][] world) {
        return world[x][y].equals(Tileset.WALL);
    }

    public static boolean isStandable(int x, int y, TETile[][] world) {
        return !(world[x][y].equals(Tileset.NOTHING) || world[x][y].equals(Tileset.WALL));
    }

    public static TETile[][] setBlock(int x, int y, TETile[][] world, TETile tile) {
        world[x][y] = tile;
        return world;
    }

    public static TETile getBlock(int x, int y, TETile[][] world) {
        return world[x][y];
    }
}
