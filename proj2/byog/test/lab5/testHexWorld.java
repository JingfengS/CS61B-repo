package byog.test.lab5;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import byog.lab5.HexWorldHelper;
import org.apache.pdfbox.util.Hex;
import org.junit.Test;
import static com.google.common.truth.Truth.assertThat;

public class testHexWorld {
    @Test
    public void testListHelper() {
        boolean[][] lst1 = HexWorldHelper.hexListHelper(0);
        assertThat(lst1).isNull();

        boolean[][] lst2 = HexWorldHelper.hexListHelper(-1);
        assertThat(lst2).isNull();

        boolean[][] lst3 = HexWorldHelper.hexListHelper(1);
        assertThat(lst3).isNull();

        boolean[][] lst4 = HexWorldHelper.hexListHelper(2);
        assertThat(lst4.length).isEqualTo(4);
        assertThat(lst4[0].length).isEqualTo(4);
        assertThat(lst4[1].length).isEqualTo(4);
        assertThat(lst4[2].length).isEqualTo(4);
        assertThat(lst4[3].length).isEqualTo(4);
        assertThat(lst4[0][0]).isEqualTo(false);
        assertThat(lst4[0][1]).isEqualTo(true);
        assertThat(lst4[0][2]).isEqualTo(true);
        assertThat(lst4[0][3]).isEqualTo(false);
        assertThat(lst4[1][0]).isEqualTo(true);
        assertThat(lst4[1][1]).isEqualTo(true);
        assertThat(lst4[1][2]).isEqualTo(true);
        assertThat(lst4[1][3]).isEqualTo(true);
        assertThat(lst4[2][0]).isEqualTo(true);
        assertThat(lst4[2][1]).isEqualTo(true);
        assertThat(lst4[2][2]).isEqualTo(true);
        assertThat(lst4[2][3]).isEqualTo(true);
        assertThat(lst4[3][0]).isEqualTo(false);
        assertThat(lst4[3][1]).isEqualTo(true);
        assertThat(lst4[3][2]).isEqualTo(true);
        assertThat(lst4[3][3]).isEqualTo(false);

        boolean[][] lst5 = HexWorldHelper.hexListHelper(3);
    }

    @Test
    public void testFillHelperMethod() {
        boolean[][] lst5 = HexWorldHelper.hexListHelper(3);
        TETile[][] hexagon = HexWorldHelper.fillWithObj(lst5, Tileset.FLOWER);
    }
}
