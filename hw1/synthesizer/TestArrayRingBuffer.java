package synthesizer;

import org.junit.Test;

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
            assertThat(e.getMessage()).isEqualTo("The buffer is empty!");
        }
        for (int i = 0; i < 10; i += 1) {
            arb.enqueue(10);
        }
        try {
            arb.enqueue(100);
        } catch (Exception e) {
            assertThat(e.getMessage()).isEqualTo("The buffer is full!");
        }
    }
}
