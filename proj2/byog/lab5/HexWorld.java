package byog.lab5;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.collections.keyvalue.TiedMapEntry;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;
    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = new TETile[WIDTH][HEIGHT];

        for (int i = 0; i < WIDTH; i += 1) {
            for (int j = 0; j < HEIGHT; j += 1) {
                world[i][j] = Tileset.NOTHING;
            }
        }
        Hexagon[] hexagon = new Hexagon[19];
        Hexagon hexagon0 = new Hexagon(3, Tileset.FLOWER, 20, 5);
        hexagon[0] = hexagon0;
        hexagon[1] = leftHexagon(hexagon0, Tileset.FLOOR, world);
        hexagon[2] = rightHexagon(hexagon0, Tileset.GRASS, world);
        hexagon[3] = leftHexagon(hexagon[1], Tileset.SAND, world);
        hexagon[4] = rightHexagon(hexagon[1], Tileset.WALL, world);
        hexagon[5] = rightHexagon(hexagon[2], Tileset.FLOWER, world);
        hexagon[6] = rightHexagon(hexagon[3], Tileset.FLOWER, world);
        hexagon[7] = rightHexagon(hexagon[4], Tileset.LOCKED_DOOR, world);
        hexagon[8] = leftHexagon(hexagon[6], Tileset.TREE, world);
        hexagon[9] = rightHexagon(hexagon[6], Tileset.WATER, world);
        hexagon[10] = rightHexagon(hexagon[7], Tileset.MOUNTAIN, world);
        hexagon[11] = rightHexagon(hexagon[8], Tileset.FLOWER, world);
        hexagon[12] = rightHexagon(hexagon[9], Tileset.TREE, world);
        hexagon[13] = leftHexagon(hexagon[11], Tileset.WALL, world);
        hexagon[14] = leftHexagon(hexagon[12], Tileset.TREE, world);
        hexagon[15] = rightHexagon(hexagon[12], Tileset.WATER, world);
        hexagon[16] = rightHexagon(hexagon[13], Tileset.FLOWER, world);
        hexagon[17] = rightHexagon(hexagon[14], Tileset.TREE, world);
        hexagon[18] = leftHexagon(hexagon[17], Tileset.MOUNTAIN, world);


        for (int j = 0; j < 19; j += 1) {
            drawHexagon(hexagon[j], world);
        }

        ter.renderFrame(world);
    }

    public static TETile[][] drawHexagon(Hexagon hex, TETile[][] world) {
        int xStartPoint = hex.getXX() - hex.getPivot();
        int yStartPoint = hex.getYY();
        TETile[][] hexagon = hex.getHexagon();
        for (int x = xStartPoint; x < xStartPoint + hex.widLength(); x += 1) {
            for (int y = yStartPoint; y < yStartPoint + hex.heightLength(); y += 1) {
                if (world[x][y].equals(Tileset.NOTHING)) {
                    world[x][y] = hexagon[y - yStartPoint][x - xStartPoint];
                }
            }
        }
        return world;
    }

    public static Hexagon leftHexagon(Hexagon hex1, TETile obj, TETile[][] world) {
        Hexagon hexagon = new Hexagon(hex1, obj, true);
        return hexagon;
    }
    public static Hexagon rightHexagon(Hexagon hex1, TETile obj, TETile[][] world) {
        Hexagon hexagon = new Hexagon(hex1, obj, false);
        return hexagon;
    }

}

