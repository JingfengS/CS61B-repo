import static org.junit.Assert.*;

import org.junit.Test;

public class TestArrayDequeGold {

    @Test
    public void compareStudentArrayAndSolution() {

        /** @source StudentArrayDequeLauncher */
        StudentArrayDeque<Integer> stuAD = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> ads = new ArrayDequeSolution<>();
        for (Integer i = 0; i < 32; i += 1) {
            double numberBetweenZeroAndOne = StdRandom.uniform();
            if (numberBetweenZeroAndOne < 0.5) {
                ads.addFirst(i);
                stuAD.addFirst(i);
            } else {
                stuAD.addLast(i);
                ads.addLast(i);
            }
        }
        for (int i = 0; i < 16; i += 1) {
            double numBetweenZeroAndOne = StdRandom.uniform();
            if (numBetweenZeroAndOne < 0.5) {
                assertEquals(ads.removeFirst(), stuAD.removeFirst());
            } else {
                assertEquals(ads.removeLast(), stuAD.removeLast());
            }
        }
    }
}
