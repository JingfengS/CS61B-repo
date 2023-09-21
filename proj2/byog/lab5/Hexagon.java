package byog.lab5;

import byog.TileEngine.TETile;
import org.apache.pdfbox.util.Hex;

public class Hexagon {
    private int n;
    private int pivot;
    private TETile[][] hexagon;
    private int xx;
    private int yy;

    public Hexagon(int N, TETile obj) {
        n = N;
        pivot = n - 1;
        boolean[][] hexagonGrid = HexWorldHelper.hexListHelper(N);
        hexagon = HexWorldHelper.fillWithObj(hexagonGrid, obj);
        xx = 0;
        yy = 0;
    }

    public Hexagon(int N, TETile obj, int x, int y) {
        this(N, obj);
        xx = x;
        yy = y;
    }

    public Hexagon(Hexagon hex, TETile obj, boolean left) {
        this(hex.getN(), obj, hex.getXX(), hex.getYY() + hex.getN());
        if (left) {
            this.xx -= hex.getN() + hex.getPivot();
        } else {
            this.xx += hex.getN() + hex.getPivot();
        }
    }



    public TETile[][] getHexagon() {
        return hexagon;
    }

    public int getPivot() {
        return pivot;
    }

    public int heightLength() {
        return 2 * n;
    }

    public int widLength() {
        return 3 * n - 2;
    }

    public int getN() {
        return n;
    }

    public int getXX() {
        return xx;
    }

    public int getYY() {
        return yy;
    }
}
