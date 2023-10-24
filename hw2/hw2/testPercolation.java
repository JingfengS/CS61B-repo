package hw2;

import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;

public class testPercolation {
    @Test
    public void testConstructor() {
        Percolation p = new Percolation(2);
        try {
            Percolation p1 = new Percolation(0);
        } catch (Exception e) {
            assertThat(e.getMessage()).isEqualTo("N should be greater than 0!");
        }
    }

    @Test
    public void testOpen() {
        Percolation p = new Percolation(4);
        p.open(1, 3);
        p.open(2, 3);
        assertThat(p.isOpen(1, 3)).isTrue();
        assertThat(p.isOpen(0, 0)).isFalse();
        assertThat(p.isFull(1, 3)).isFalse();

        p.open(0, 2);
        assertThat(p.isOpen(0, 2)).isTrue();
        assertThat(p.isFull(0, 2)).isTrue();
        assertThat(p.isFull(1, 3)).isFalse();

        p.open(0, 3);
        assertThat(p.isFull(2, 3)).isTrue();

        try {
            p.open(100, 100);
        } catch (Exception e) {
            assertThat(e.getMessage()).isEqualTo("Index out of bound!");
        }
    }

    @Test
    public void testPercolate() {
        Percolation p = new Percolation(4);
        p.open(1, 3);
        p.open(2, 3);
        p.open(0, 2);
        p.open(0, 3);
        assertThat(p.percolates()).isFalse();
        p.open(3, 3);
        assertThat(p.percolates()).isTrue();
    }

}
