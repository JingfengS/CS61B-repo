import edu.princeton.cs.algs4.Queue;
import org.junit.Test;

import java.awt.desktop.QuitResponse;

import static com.google.common.truth.Truth.assertThat;

public class QuickSort {
    /**
     * Returns a new queue that contains the given queues catenated together.
     *
     * The items in q2 will be catenated after all of the items in q1.
     */
    private static <Item extends Comparable> Queue<Item> catenate(Queue<Item> q1, Queue<Item> q2) {
        Queue<Item> catenated = new Queue<Item>();
        for (Item item : q1) {
            catenated.enqueue(item);
        }
        for (Item item: q2) {
            catenated.enqueue(item);
        }
        return catenated;
    }

    /** Returns a random item from the given queue. */
    private static <Item extends Comparable> Item getRandomItem(Queue<Item> items) {
        int pivotIndex = (int) (Math.random() * items.size());
        Item pivot = null;
        // Walk through the queue to find the item at the given index.
        for (Item item : items) {
            if (pivotIndex == 0) {
                pivot = item;
                break;
            }
            pivotIndex--;
        }
        return pivot;
    }

    /**
     * Partitions the given unsorted queue by pivoting on the given item.
     *
     * @param unsorted  A Queue of unsorted items
     * @param pivot     The item to pivot on
     * @param less      An empty Queue. When the function completes, this queue will contain
     *                  all of the items in unsorted that are less than the given pivot.
     * @param equal     An empty Queue. When the function completes, this queue will contain
     *                  all of the items in unsorted that are equal to the given pivot.
     * @param greater   An empty Queue. When the function completes, this queue will contain
     *                  all of the items in unsorted that are greater than the given pivot.
     */
    private static <Item extends Comparable> void partition(
            Queue<Item> unsorted, Item pivot,
            Queue<Item> less, Queue<Item> equal, Queue<Item> greater) {
        for (Item item : unsorted) {
            int cmp = item.compareTo(pivot);
            if (cmp > 0) {
                greater.enqueue(item);
            } else if (cmp < 0) {
                less.enqueue(item);
            } else {
                equal.enqueue(item);
            }
        }
    }

    /** Returns a Queue that contains the given items sorted from least to greatest. */
    public static <Item extends Comparable> Queue<Item> quickSort(
            Queue<Item> items) {
        if (items.isEmpty()) {
            return items;
        }
        Queue<Item> greater = new Queue<>();
        Queue<Item> equal = new Queue<>();
        Queue<Item> less = new Queue<>();
        partition(items, getRandomItem(items), less, equal, greater);
        return catenate(quickSort(less), catenate(equal, quickSort(greater)));
    }
    @Test
    public void testQuickSort() {
        Queue<Integer> q1 = new Queue<>();
        Queue<String> students = new Queue<>();
        Queue<Integer> q2 = new Queue<>();

        assertThat(quickSort(q1)).isEmpty();
        q1.enqueue(1);
        assertThat(quickSort(q1)).containsExactly(1);
        q1.enqueue(0);
        assertThat(quickSort(q1)).containsExactly(0, 1).inOrder();
        students.enqueue("Alice");
        students.enqueue("Vanessa");
        students.enqueue("Ethan");
        assertThat(quickSort(students)).containsExactly("Alice", "Ethan", "Vanessa").inOrder();

        q2.enqueue(5);
        q2.enqueue(9);
        q2.enqueue(17);
        q2.enqueue(88);
        q2.enqueue(42);
        q2.enqueue(3);
        q2.enqueue(32);

        assertThat(quickSort(q2)).containsExactly(3, 5, 9, 17, 32, 42, 88).inOrder();
    }
}
