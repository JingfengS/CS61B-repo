package synthesizer;

import org.checkerframework.checker.units.qual.A;
import org.junit.Test;

import java.util.Iterator;

import static com.google.common.truth.Truth.assertThat;

/** Tests the ArrayRingBuffer class.
 *  @author Jingfeng Sun
 */

public class TestArrayRingBuffer {
    @Test
    public void test() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(10);
        arb.enqueue(1);
        assertThat(arb.peek()).isEqualTo(1);
        arb.enqueue(2);
        arb.enqueue(3);
        arb.enqueue(4);
        assertThat(arb.dequeue()).isEqualTo(1);
        assertThat(arb.peek()).isEqualTo(2);
        arb.dequeue();
        arb.dequeue();
        arb.dequeue();
        try {
            arb.dequeue();
        } catch (Exception e) {
            assertThat(e.getMessage()).isEqualTo("Ring Buffer Underflow");
        }
        try {
            arb.peek();
        } catch (Exception e) {
            assertThat(e.getMessage()).isEqualTo("Buffer Empty");
        }
        for (int i = 0; i < 10; i += 1) {
            arb.enqueue(10);
        }
        try {
            arb.enqueue(100);
        } catch (Exception e) {
            assertThat(e.getMessage()).isEqualTo("Ring Buffer Overflow");
        }
    }

    @Test
    public void testIterator() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(5);
        for (int i = 0; i < 5; i += 1) {
            arb.enqueue(5 - i);
        }
        Iterator<Integer> it = arb.iterator();
        int i = 0;
        while (it.hasNext()) {
            assertThat(it.next()).isEqualTo(5 - 1);
            i += 1;
        }
        try {
            it.next();
        } catch (Exception e) {
            assertThat(e.getMessage()).isEqualTo("Iterator Out of Bounds");
        }
        int t = 0;
        for (int j : arb) {
            assertThat(j).isEqualTo(5 - t);
            t += 1;
        }

    }
}
