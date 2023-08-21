import org.checkerframework.checker.units.qual.A;
import org.junit.Test;

import javax.security.auth.login.AccountExpiredException;

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
        for (int i = 0; i < 3; i++) {
            A.addLast(i);
        }
        for (int i = 0; i < 10; i++) {
            A.addFirst(i);
        }
        for (int i = 0; i < 9; i++) {
            A.removeLast();
        }
        assertThat(A.get(0)).isEqualTo(9);
        A.removeFirst();
        assertThat(A.get(2)).isEqualTo(6);
    }

}
