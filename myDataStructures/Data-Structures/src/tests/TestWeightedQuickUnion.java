package tests;

import edu.princeton.cs.algs4.In;
import jingfeng.sun.DisjointSets.WeightedQuickUnion;
import org.junit.Test;
import static com.google.common.truth.Truth.assertThat;

public class TestWeightedQuickUnion {

    @Test
    public void testConstructor() {
        WeightedQuickUnion<Integer> wqu = new WeightedQuickUnion<>(10, null);
    }

    @Test
    public void testConnect() {
        WeightedQuickUnion<Integer> wqu = new WeightedQuickUnion<>(10, null);
        wqu.connect(0, 1);
        wqu.connect(0, 9);
        wqu.connect(2, 3);
        wqu.connect(9, 2);
        wqu.connect(5, 6);
        wqu.connect(5, 7);
        wqu.connect(5, 8);
        wqu.connect(5, 0);

    }

    @Test
    public void testIsConnect() {
        WeightedQuickUnion<Integer> wqu = new WeightedQuickUnion<>(10, null);
        assertThat(wqu.isConnected(0, 1)).isFalse();
        wqu.connect(0, 1);
        assertThat(wqu.isConnected(0, 1)).isTrue();
        wqu.connect(0, 2);
        assertThat(wqu.isConnected(2, 1)).isTrue();
        wqu.connect(3, 4);
        wqu.connect(4, 5);
        assertThat(wqu.isConnected(0, 5)).isFalse();
        wqu.connect(7, 8);
        wqu.connect(8, 9);
        wqu.connect(7, 0);
        assertThat(wqu.isConnected(2, 8)).isTrue();
        assertThat(wqu.isConnected(4, 9)).isFalse();
        wqu.connect(4, 9);
        assertThat(wqu.isConnected(2, 3)).isTrue();
    }
}
