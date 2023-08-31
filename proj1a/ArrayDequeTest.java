import org.checkerframework.checker.units.qual.A;
import org.junit.Test;


import static com.google.common.truth.Truth.assertThat;

public class ArrayDequeTest {
    @Test
    public void testAddLast() {
        ArrayDeque<Integer> A = new ArrayDeque<>();
        for (int i = 0; i < 8; i++) {
            A.addLast(1);
        }
        assertThat(A.size()).isEqualTo(8);
    }

    @Test
    public void testAddFirst() {
        ArrayDeque<Integer> A = new ArrayDeque<>();
        for (int i = 0; i < 3; i++) {
            A.addLast(1);
        }
        for (int i = 0; i < 10; i++) {
            A.addFirst(8);
        }
        assertThat(A.size()).isEqualTo(13);
        for (int i = 0; i < 3; i++) {
            A.addLast(1);
        }
        assertThat(A.size()).isEqualTo(16);
    }

    @Test
    public void testRemoveLast() {
        ArrayDeque<Integer> A = new ArrayDeque<>();
        for (int i = 0; i < 3; i++) {
            A.addLast(1);
        }
        for (int i = 0; i < 10; i++) {
            A.addFirst(8);
        }
        for (int i = 0; i < 13; i++) {
            A.removeLast();
        }
        assertThat(A.size()).isEqualTo(0);
    }

    @Test
    public void testRemoveFirst() {
        ArrayDeque<Integer> A = new ArrayDeque<>();
        for (int i = 0; i < 3; i++) {
            A.addLast(1);
        }
        for (int i = 0; i < 10; i++) {
            A.addFirst(8);
        }
        for (int i = 0; i < 8; i++) {
            A.removeLast();
        }
        for (int i = 0; i < 5; i++) {
            A.removeFirst();
        }
        assertThat(A.size()).isEqualTo(0);
    }

    @Test
    public void testGet() {
        ArrayDeque<Integer> A = new ArrayDeque<>();
        assertThat(A.removeLast()).isNull();
        assertThat(A.removeFirst()).isNull();
        for (int i = 0; i < 3; i++) {
            A.addLast(i);
        }
        for (int i = 0; i < 10; i++) {
            A.addFirst(i);
        }
        assertThat(A.removeLast()).isEqualTo(2);
        for (int i = 0; i < 8; i++) {
            A.removeLast();

        }
        assertThat(A.get(0)).isEqualTo(9);
        A.removeFirst();
        assertThat(A.get(2)).isEqualTo(6);
        assertThat(A.removeFirst()).isEqualTo(8);
        assertThat(A.removeLast()).isEqualTo(6);

    }


    @Test
    public void testPrintDeque() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        ad.printDeque();
        for (int i = 0; i < 10; i += 1) {
            ad.addLast(i);
        }
        ad.printDeque();
    }

    @Test
    public void testIsEmpty() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        assertThat(ad.isEmpty()).isTrue();
        ad.addLast(0);
        assertThat(ad.isEmpty()).isFalse();
        ad.removeLast();
        assertThat(ad.isEmpty()).isTrue();
    }
}
