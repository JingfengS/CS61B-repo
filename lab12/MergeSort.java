import edu.princeton.cs.algs4.Queue;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class MergeSort {
    /**
     * Removes and returns the smallest item that is in q1 or q2.
     *
     * The method assumes that both q1 and q2 are in sorted order, with the smallest item first. At
     * most one of q1 or q2 can be empty (but both cannot be empty).
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      The smallest item that is in q1 or q2.
     */
    private static <Item extends Comparable> Item getMin(
            Queue<Item> q1, Queue<Item> q2) {
        if (q1.isEmpty()) {
            return q2.dequeue();
        } else if (q2.isEmpty()) {
            return q1.dequeue();
        } else {
            // Peek at the minimum item in each queue (which will be at the front, since the
            // queues are sorted) to determine which is smaller.
            Comparable q1Min = q1.peek();
            Comparable q2Min = q2.peek();
            if (q1Min.compareTo(q2Min) <= 0) {
                // Make sure to call dequeue, so that the minimum item gets removed.
                return q1.dequeue();
            } else {
                return q2.dequeue();
            }
        }
    }

    /** Returns a queue of queues that each contain one item from items. */
    private static <Item extends Comparable> Queue<Queue<Item>>
            makeSingleItemQueues(Queue<Item> items) {
        Queue<Queue<Item>> singleItemQueues = new Queue<>();
        for (Item item : items) {
            Queue<Item> q = new Queue<>();
            q.enqueue(item);
            singleItemQueues.enqueue(q);
        }
        return singleItemQueues;
    }

    /**
     * Returns a new queue that contains the items in q1 and q2 in sorted order.
     *
     * This method should take time linear in the total number of items in q1 and q2.  After
     * running this method, q1 and q2 will be empty, and all of their items will be in the
     * returned queue.
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      A Queue containing all of the q1 and q2 in sorted order, from least to
     *              greatest.
     *
     */
    private static <Item extends Comparable> Queue<Item> mergeSortedQueues(
            Queue<Item> q1, Queue<Item> q2) {
        if (q1.isEmpty() && q2.isEmpty()) {
            throw new IllegalArgumentException("q1 and q2 cannot both be empty!");
        }
        Queue<Item> merged = new Queue<>();
        while (!q1.isEmpty() || !q2.isEmpty()) {
            Item smallestInQ1AndQ2 = getMin(q1, q2);
            merged.enqueue(smallestInQ1AndQ2);
        }
        return merged;
    }

    /** Returns a Queue that contains the given items sorted from least to greatest. */
    public static <Item extends Comparable> Queue<Item> mergeSort(
            Queue<Item> items) {
       Queue<Queue<Item>> singleItemQueues = makeSingleItemQueues(items);
       if (singleItemQueues.isEmpty()) {
           return new Queue<>();
       }
       while (singleItemQueues.size() > 1) {
          Queue<Item> first = singleItemQueues.dequeue();
          Queue<Item> second = singleItemQueues.dequeue();
          singleItemQueues.enqueue(mergeSortedQueues(first, second));
       }
       return singleItemQueues.peek();
    }

//    @Test
//    public void testMakeSingleItemQueues() {
//        Queue<Integer> q = new Queue<>();
//
//        assertThat(makeSingleItemQueues(q)).isEmpty();
//
//        q.enqueue(2);
//        assertThat(makeSingleItemQueues(q).peek().peek()).isEqualTo(2);
//
//        q.enqueue(1);
//        q.enqueue(3);
//        Queue<Queue<Integer>> result = makeSingleItemQueues(q);
//
//        assertThat(result.dequeue().peek()).isEqualTo(2);
//        assertThat(result.dequeue().peek()).isEqualTo(1);
//        assertThat(result.dequeue().peek()).isEqualTo(3);
//        assertThat(result).isEmpty();
//    }
//
//    @Test
//    public void testMergeSortedQueues() {
//        Queue<Integer> q1 = new Queue<>();
//        Queue<Integer> q2 = new Queue<>();
//        q1.enqueue(1);
//
//        assertThat(mergeSortedQueues(q1, q2)).containsExactly(1);
//        q1.enqueue(1);
//        assertThat(mergeSortedQueues(q2, q1)).containsExactly(1);
//
//        q1.enqueue(1);
//        q2.enqueue(2);
//        assertThat(mergeSortedQueues(q1, q2)).containsExactly(1, 2).inOrder();
//        q1.enqueue(1);
//        q1.enqueue(3);
//        q2.enqueue(2);
//        assertThat(mergeSortedQueues(q1, q2)).containsExactly(1, 2, 3).inOrder();
//        q1.enqueue(1);
//        q1.enqueue(3);
//        q2.enqueue(2);
//        q2.enqueue(4);
//        q2.enqueue(5);
//        q1.enqueue(3);
//        q1.enqueue(3);
//        q1.enqueue(8);
//        assertThat(mergeSortedQueues(q2, q1)).containsExactly(1, 2, 3, 3, 3, 4, 5, 8).inOrder();
//    }
//
//    @Test
//    public void testMergeSort() {
//        Queue<Integer> q1 = new Queue<>();
//        Queue<String> students = new Queue<>();
//        Queue<Integer> q2 = new Queue<>();
//
//        assertThat(mergeSort(q1)).isEmpty();
//        q1.enqueue(1);
//        assertThat(mergeSort(q1)).containsExactly(1);
//        q1.enqueue(0);
//        assertThat(mergeSort(q1)).containsExactly(0, 1).inOrder();
//        students.enqueue("Alice");
//        students.enqueue("Vanessa");
//        students.enqueue("Ethan");
//        assertThat(mergeSort(students)).containsExactly("Alice", "Ethan", "Vanessa").inOrder();
//
//        q2.enqueue(5);
//        q2.enqueue(9);
//        q2.enqueue(17);
//        q2.enqueue(88);
//        q2.enqueue(42);
//        q2.enqueue(3);
//        q2.enqueue(32);
//
//        assertThat(mergeSort(q2)).containsExactly(3, 5, 9, 17, 32, 42, 88).inOrder();
//    }
}
