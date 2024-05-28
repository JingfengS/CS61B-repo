import org.junit.Test;
import static com.google.common.truth.Truth.assertThat;

import java.util.Map;

public class RastererHelper {
    private static final double DOUBLE_THRESHOLD = 0.000000001;
    /**
     * The root upper left/lower right longitudes and latitudes represent the bounding box of
     * the root tile, as the images in the img/ folder are scraped.
     * Longitude == x-axis; latitude == y-axis.
     */
    public static final double ROOT_ULLAT = 37.892195547244356, ROOT_ULLON = -122.2998046875,
            ROOT_LRLAT = 37.82280243352756, ROOT_LRLON = -122.2119140625;

    /** Each tile is 256x256 pixels. */
    public static final int TILE_SIZE = 256;

    /** The LongDPP of depth 0 map. This will be used as a reference of depth calculation. */
    public static final double depthZeroDPP = (ROOT_LRLON - ROOT_ULLON) / TILE_SIZE;

    /**
     * Given the upper left longitude and lower right longitude, return the depth of the map
     * @param ullon upper left longitude
     * @param lrlon lower right longitude
     * @param w width of the map
     * @return depth of the map
     */
    public static int getDepth(double ullon, double lrlon, double w) {
        assert (lrlon - ullon ) > 0;
        double longDPP = (lrlon - ullon) / w;
        int depth = (int) Math.ceil(Math.log(longDPP / depthZeroDPP) / Math.log(1./2));
        if (depth < 0) {
            return 0;
        } else {
            return Math.min(depth, 7);
        }
    }

    /**
     * Given the longitude of the point and the depth of the map, return its box X position
     * @param longitude longitude of the point
     * @param depth depth of the map
     * @return box X position
     */
    public static int getBoxXX(double longitude, int depth) {
        if (longitude < ROOT_ULLON) {
            return 0;
        }
        int D = 1 << depth;
        double step = (ROOT_LRLON - ROOT_ULLON) / D;
        int boxXX = (int) Math.floor((longitude - ROOT_ULLON) / step);
        return Math.min(boxXX, D - 1);
    }

    /**
     * Given the latitude and depth of the map
     * @param latitude latitude of the point
     * @param depth depth of the map
     * @return the box Y position
     */
    public static int getBoxYY(double latitude, int depth) {
        if (latitude > ROOT_ULLAT) {
            return 0;
        }
        int D = 1 << depth;
        double step = (ROOT_ULLAT - ROOT_LRLAT) / D;
        int boxYY = (int) Math.floor((ROOT_ULLAT - latitude) / step);
        return Math.min(boxYY, D - 1);
    }

    /**
     * Given the box X position, return the longitude of its upper left longitude
     * @param boxX the X position of the box
     * @param depth the depth of the map
     * @return its upper left longitude
     */
    public static double getBoundingULLON(int boxX, int depth) {
        int D = 1 << depth;
        double step = (ROOT_LRLON - ROOT_ULLON) / D;
        return ROOT_ULLON + step * boxX;
    }

    /**
     * Given the box X position, return the longitude of its lower right longitude
     * @param boxX the X position of the box
     * @param depth the depth of the map
     * @return its lower right longitude
     */
    public static double getBoundingLRLON(int boxX, int depth) {
        int D = 1 << depth;
        double step = (ROOT_LRLON - ROOT_ULLON) / D;
        return ROOT_ULLON + (boxX + 1) * step;
    }

    /**
     * Given the box X position, return the latitude of its upper left latitude
     * @param boxY the Y position of the box
     * @param depth the depth of the map
     * @return its upper left latitude
     */
    public static double getBoundingULLAT(int boxY, int depth) {
        int D = 1 << depth;
        double step = (ROOT_ULLAT - ROOT_LRLAT) / D;
        return ROOT_ULLAT - boxY * step;
    }

    /**
     * Given the box X position, return the latitude of its lower right latitude
     * @param boxY the Y position of the box
     * @param depth the depth of the map
     * @return its lower right latitude
     */
    public static double getBoundingLRLAT(int boxY, int depth) {
        int D = 1 << depth;
        double step = (ROOT_ULLAT - ROOT_LRLAT) / D;
        return ROOT_ULLAT - (boxY + 1) * step;
    }


    /**
     * Create a string represent the image from x, y and depth
     * @param x the x position of the box
     * @param y the y position of the box
     * @param depth the depth of the map
     * @return a string represent the box
     */
    public static String xY2ImageString(int x, int y, int depth) {
        return "d" + depth + "_x" + x + "_y" + y + ".png";
    }

    /**
     * Create a string-image matrix to represent the whole map
     * @param upperLeftX the upper left box X position in the map
     * @param upperLeftY the upper left box Y position in the map
     * @param lowerRightX the lower right box X position in the map
     * @param lowerRightY the lower right box Y position in the map
     * @param depth the depth of the map
     * @return a string-image matrix to represent the whole map
     */
    public static String[][] createImageMatrix(int upperLeftX, int upperLeftY, int lowerRightX, int lowerRightY, int depth) {
        String[][] imageMatrix = new String[lowerRightY - upperLeftY + 1][lowerRightX - upperLeftX + 1];
        for (int x = upperLeftX; x <= lowerRightX; x += 1) {
            for (int y = upperLeftY; y <= lowerRightY; y += 1) {
                imageMatrix[y - upperLeftY][x - upperLeftX] = xY2ImageString(x, y, depth);
            }
        }
        return imageMatrix;
    }
    @Test
    public void testGetDepth() {
        assertThat(getDepth(2., 3., 1.)).isEqualTo(0);
        assertThat(getDepth(-122.30410170759153, -122.2104604264636, 1085.)).isEqualTo(2);
        assertThat(getDepth(-122.24163047377972, -122.24053369025242, 892.0)).isEqualTo(7);
        assertThat(getDepth(-122.3027284165759, -122.20908713544797, 305.0)).isEqualTo(1);
        assertThat(getDepth(-122.30410170759153, -122.2104604264636, 1091.0)).isEqualTo(2);
        assertThat(getDepth(0, 0.001, 30000.)).isEqualTo(7);
    }

    @Test
    public void testGetBoxXX() {
        assertThat(getBoxXX(-122.24163047377972, 7)).isEqualTo(84);
        assertThat(getBoxXX(-122.24053369025242, 7)).isEqualTo(86);
        assertThat(getBoxXX(-122.3027284165759, 1)).isEqualTo(0);
        assertThat(getBoxXX(-122.20908713544797, 1)).isEqualTo(1);
        assertThat(getBoxXX(-122.30410170759153, 2)).isEqualTo(0);
        assertThat(getBoxXX(-122.2104604264636, 2)).isEqualTo(3);
    }

    @Test
    public void testGetBoxYY() {
        assertThat(getBoxYY(37.87655856892288, 7)).isEqualTo(28);
        assertThat(getBoxYY(37.87548268822065, 7)).isEqualTo(30);
        assertThat(getBoxYY(37.88708748276975, 1)).isEqualTo(0);
        assertThat(getBoxYY(37.848731523430196, 1)).isEqualTo(1);
        assertThat(getBoxYY(37.870213571328854, 2)).isEqualTo(1);
        assertThat(getBoxYY(37.8318576119893, 2)).isEqualTo(3);
    }

    @Test
    public void testGetBoundULLON() {
        assertThat(Math.abs(getBoundingULLON(84, 7) - (-122.24212646484375)) < DOUBLE_THRESHOLD).isTrue();
        assertThat(Math.abs(getBoundingULLON(0, 1) - (-122.2998046875)) < DOUBLE_THRESHOLD).isTrue();
        assertThat(Math.abs(getBoundingULLON(0, 2) - (-122.2998046875)) < DOUBLE_THRESHOLD).isTrue();
    }

    @Test
    public void testGetBoundLRLON() {
        assertThat(Math.abs(getBoundingLRLON(86, 7) - (-122.24006652832031)) < DOUBLE_THRESHOLD).isTrue();
        assertThat(Math.abs(getBoundingLRLON(1, 1) - (-122.2119140625)) < DOUBLE_THRESHOLD).isTrue();
        assertThat(Math.abs(getBoundingLRLON(3, 2) - (-122.2119140625)) < DOUBLE_THRESHOLD).isTrue();
    }

    @Test
    public void testGetBoundULLAT() {
        assertThat(Math.abs(getBoundingULLAT(1, 2) - (37.87484726881516)) < DOUBLE_THRESHOLD).isTrue();
        assertThat(Math.abs(getBoundingULLAT(0, 1) - (37.892195547244356)) < DOUBLE_THRESHOLD).isTrue();
        assertThat(Math.abs(getBoundingULLAT(28, 7) - (37.87701580361881)) < DOUBLE_THRESHOLD).isTrue();
    }

    @Test
    public void testGetBoundLRLAT() {
        assertThat(Math.abs(getBoundingLRLAT(3, 2) - (37.82280243352756)) < DOUBLE_THRESHOLD).isTrue();
        assertThat(Math.abs(getBoundingLRLAT(1, 1) - (37.82280243352756)) < DOUBLE_THRESHOLD).isTrue();
        assertThat(Math.abs(getBoundingLRLAT(30, 7) - (37.87538940251607)) < DOUBLE_THRESHOLD).isTrue();
    }

    @Test
    public void testXY2ImageString() {
        assertThat(xY2ImageString(0, 1, 2)).isEqualTo("d2_x0_y1.png");
    }

    @Test
    public void testImageMatrix() {
        String[][] im = createImageMatrix(84, 28, 86, 30, 7);
        for (int y = 0; y < 3; y += 1) {
            for (int x = 0; x < 3; x += 1) {
                System.out.println(im[y][x]);
            }
        }
    }
}
