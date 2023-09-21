package byog.lab5;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class HexWorldHelper {
    public static boolean[][] hexListHelper(int N) {
        if (N <= 1) {
            return null;
        }
        boolean[][] lst = new boolean[2 * N][3 * N - 2];
        int start = 0;
        int end = 0;
        for (int i = 0; i < lst.length; i += 1) {
            if (i < lst.length / 2) {
                int p = lst.length / 2 - 1 - i;
                start = p;
                end = lst[0].length - 1 - p;
            } else {
                int p = i - lst.length / 2;
                start = p;
                end = lst[0].length - p - 1;
            }
            for (int j = start; j <= end; j += 1) {
                lst[i][j] = true;
            }
        }
        return lst;
    }

    public static TETile[][] fillWithObj(boolean[][] hexgrid, TETile obj) {
        TETile[][] hexagon = new TETile[hexgrid.length][hexgrid[0].length];
        for (int line = 0; line < hexgrid.length; line += 1) {
            for (int i = 0; i < hexgrid[0].length; i += 1) {
                if (hexgrid[line][i]) {
                    hexagon[line][i] = obj;
                } else {
                    hexagon[line][i] = Tileset.NOTHING;
                }
            }
        }
        return hexagon;
    }

}
