package tests;

import jingfeng.sun.DisjointSets.WeightedQuickUnion;
import jingfeng.sun.DisjointSets.WeightedUnionFindPathCompression;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class testWeightedUnionFindPathCompression {
    @Test
    public void testBasicFunction() {
        WeightedUnionFindPathCompression<Integer> wufpc = new WeightedUnionFindPathCompression<>(10, null);

        assertThat(wufpc.isConnected(0, 1)).isFalse();
        wufpc.connect(0, 1);
        assertThat(wufpc.isConnected(0, 1)).isTrue();
        wufpc.connect(0, 2);
        assertThat(wufpc.isConnected(2, 1)).isTrue();
        wufpc.connect(3, 4);
        wufpc.connect(4, 5);
        assertThat(wufpc.isConnected(0, 5)).isFalse();
        wufpc.connect(7, 8);
        wufpc.connect(8, 9);
        wufpc.connect(7, 0);
        assertThat(wufpc.isConnected(2, 8)).isTrue();
        assertThat(wufpc.isConnected(4, 9)).isFalse();
        wufpc.connect(4, 9);
        assertThat(wufpc.isConnected(2, 3)).isTrue();
    }

    @Test
    public void testCompression() {
        WeightedUnionFindPathCompression<Integer> wufpc = new WeightedUnionFindPathCompression<>(10, null);
        assertThat(wufpc.isConnected(0, 1)).isFalse();
        wufpc.connect(0, 1);
        wufpc.connect(1, 2);
        wufpc.connect(3, 4);
        wufpc.connect(2, 3);
        wufpc.connect(5, 6);
        wufpc.connect(7, 8);
        wufpc.connect(7, 6);
        wufpc.connect(7, 0);
        wufpc.connect(6, 0);
        assertThat(wufpc.isConnected(5, 0)).isTrue();
        assertThat(wufpc.isConnected(0, 7)).isTrue();
        assertThat(wufpc.isConnected(0, 9)).isFalse();
        assertThat(wufpc.isConnected(8, 0)).isTrue();
        assertThat(wufpc.isConnected(1, 3)).isTrue();

    }
}
