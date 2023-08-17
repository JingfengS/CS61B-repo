import org.junit.Test;
import static com.google.common.truth.Truth.assertThat;
public class LinkedListDequeMyTest {
    @Test
    public void testConstructor() {
        LinkedListDeque<Integer> A = new LinkedListDeque<>();
        LinkedListDeque<Double> B = new LinkedListDeque<>(1.0);
    }

    @Test
    public void testIsEmpty() {
        LinkedListDeque<Integer> A = new LinkedListDeque<>();
        LinkedListDeque<Double> B = new LinkedListDeque<>(1.0);
        assertThat(A.isEmpty()).isEqualTo(true);
        assertThat(B.isEmpty()).isEqualTo(false);
    }

    @Test
    public void testAddFirst() {
        LinkedListDeque<Integer> A = new LinkedListDeque<>(1);
        LinkedListDeque<Integer> B = new LinkedListDeque<>();
        A.addFirst(0);
        B.addFirst(0);
    }

    @Test
    public void testAddLast() {
        LinkedListDeque<Integer> A = new LinkedListDeque<>(1);
        LinkedListDeque<Integer> B = new LinkedListDeque<>();
        A.addLast(2);
        B.addFirst(1);
        B.addLast(10);
        assertThat(A.size()).isEqualTo(2);
        assertThat(B.size()).isEqualTo((2));
    }

    @Test
    public void testRemoveFirst() {
        LinkedListDeque<Integer> A = new LinkedListDeque<>(1);
        LinkedListDeque<Integer> B = new LinkedListDeque<>();
        A.addLast(2);
        B.addLast(10);
        A.removeFirst();
        B.removeFirst();
    }

    @Test
    public void testRemoveLast() {
        LinkedListDeque<Integer> A = new LinkedListDeque<>(1);
        LinkedListDeque<Integer> B = new LinkedListDeque<>();
        A.addLast(2);
        B.addLast(10);
        A.removeLast();
        B.removeLast();
    }

    @Test
    public void testGet() {
        LinkedListDeque<Integer> A = new LinkedListDeque<>(1);
        LinkedListDeque<Integer> B = new LinkedListDeque<>();
        A.addLast(2);
        B.addLast(10);
        assertThat(A.get(1)).isEqualTo(2);
        assertThat(B.get(0)).isEqualTo(10);

        LinkedListDeque<Integer> C = new LinkedListDeque<>();
        assertThat(C.get(0)).isEqualTo(null);
        assertThat(C.get(100)).isEqualTo(null);
    }

    @Test
    public void testPrintDeque() {
        LinkedListDeque<Integer> A = new LinkedListDeque<>(1);
        LinkedListDeque<Integer> B = new LinkedListDeque<>();
        A.addLast(2);
        A.addFirst(100);
        A.addLast(99);
        A.printDeque();
        B.printDeque();
    }

    @Test
    public void testGetRecursive() {
        LinkedListDeque<Integer> A = new LinkedListDeque<>(1);
        LinkedListDeque<Integer> B = new LinkedListDeque<>();
        A.addLast(2);
        B.addLast(10);
        assertThat(A.getRecursive(1)).isEqualTo(2);
        assertThat(B.getRecursive(0)).isEqualTo(10);

        LinkedListDeque<Integer> C = new LinkedListDeque<>();
        assertThat(C.getRecursive(0)).isEqualTo(null);
        assertThat(C.getRecursive(100)).isEqualTo(null);
    }

    @Test
    public void testSize() {
        LinkedListDeque<Integer> A = new LinkedListDeque<>(1);
        LinkedListDeque<Integer> B = new LinkedListDeque<>();
        A.addLast(2);
        B.addLast(10);
        A.addLast(100);
        B.addLast(10000);
        assertThat(A.size()).isEqualTo(3);
        assertThat(B.size()).isEqualTo((2));
        A.addFirst(2);
        A.addLast(20);
        assertThat(A.size()).isEqualTo(5);
        A.removeFirst();
        assertThat(A.size()).isEqualTo(4);
        A.removeLast();
        assertThat(A.size()).isEqualTo(3);
    }
}
