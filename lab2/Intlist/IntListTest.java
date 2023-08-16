import static org.junit.Assert.*;

import org.junit.Test;
import static com.google.common.truth.Truth.assertThat;

public class IntListTest {

    /**
     * Example test that verifies correctness of the IntList.of static
     * method. The main point of this is to convince you that
     * assertEquals knows how to handle IntLists just fine.
     */

    @Test
    public void testList() {
        IntList one = new IntList(1, null);
        IntList twoOne = new IntList(2, one);
        IntList threeTwoOne = new IntList(3, twoOne);

        IntList x = IntList.of(3, 2, 1);
        assertThat(threeTwoOne).isEqualTo(x);
    }

    @Test
    public void testdSquareList() {
        IntList L = IntList.of(1, 2, 3);
        IntList.dSquareList(L);
        assertEquals(IntList.of(1, 4, 9), L);
    }

    /**
     * Do not use the new keyword in your tests. You can create
     * lists using the handy IntList.of method.
     * <p>
     * Make sure to include test cases involving lists of various sizes
     * on both sides of the operation. That includes the empty of, which
     * can be instantiated, for example, with
     * IntList empty = IntList.of().
     * <p>
     * Keep in mind that dcatenate(A, B) is NOT required to leave A untouched.
     * Anything can happen to A.
     */

    @Test
    public void testSquareListRecursive() {
        IntList L = IntList.of(1, 2, 3);
        IntList res = IntList.squareListRecursive(L);
        assertEquals(IntList.of(1, 2, 3), L);
        assertEquals(IntList.of(1, 4, 9), res);
    }

    @Test
    public void testDcatenate() {
        IntList A = IntList.of(1, 2, 3);
        IntList B = IntList.of(4, 5, 6);
        IntList exp = IntList.of(1, 2, 3, 4, 5, 6);
        assertEquals(exp, IntList.dcatenate(A, B));
        assertEquals(IntList.of(1, 2, 3, 4, 5, 6), A);
    }

    @Test
    public void testCatenate() {
        IntList A = IntList.of(1, 2, 3);
        IntList B = IntList.of(4, 5, 6);
        IntList exp = IntList.of(1, 2, 3, 4, 5, 6);
        assertEquals(exp, IntList.catenate(A, B));
        assertEquals(IntList.of(1, 2, 3), A);
    }

    @Test
    public void testGetLastNode() {
        IntList A = IntList.of(1, 2, 4, 5);
        assertThat(A.getLastNode()).isEqualTo(IntList.of(5));
    }

    @Test
    public void testReverseNondestructive() {
        IntList origin = IntList.of(1, 2, 3, 4);
        IntList expected = IntList.of(4, 3, 2, 1);
        IntList actual = IntList.reverseNondestructive(origin);
        assertThat(actual).isEqualTo(expected);
        assertThat(origin).isEqualTo(IntList.of(1, 2, 3, 4));
    }

    @Test
    public void testGetLastSecondNode() {
        IntList A = IntList.of(1, 2, 3, 4);
        IntList expected = IntList.of(3, 4);
        IntList actual = A.getLastSecondNode();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testReverseDestructive() {
        IntList A = IntList.of(1, 2, 3, 4, 5);
        IntList expected = IntList.of(5, 4, 3, 2, 1);
        IntList actual = IntList.reverseDestructive(A);
        assertThat(actual).isEqualTo(expected);
        assertThat(A).isNotEqualTo(IntList.of(1, 2, 3, 4, 5));

        IntList B = IntList.of(1, 2);
        IntList expected2 = IntList.of(2, 1);
        IntList actual2 = IntList.reverseDestructive(B);
        assertThat(actual2).isEqualTo(expected2);
        assertThat(B).isNotEqualTo(IntList.of(1, 2));
    }

    @Test
    public void testEvenOdd() {
        IntList lst = IntList.of(0, 3, 1, 4, 2, 5);
        IntList expected = IntList.of(0, 1, 2, 3, 4, 5);
        IntList.evenOdd(lst);
        assertThat(lst).isEqualTo(expected);

        IntList lst2 = IntList.of(0, 3, 1, 4, 2);
        IntList expected2 = IntList.of(0, 1, 2, 3, 4);
        IntList.evenOdd(lst2);
        assertThat(lst2).isEqualTo(expected2);
    }

    @Test
    public void testSkippify() {
        IntList A = IntList.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        IntList B = IntList.of(9, 8, 7, 6, 5, 4, 3, 2, 1);
        A.skippify();
        B.skippify();
        assertThat(A).isEqualTo(IntList.of(1, 3, 6, 10));
        assertThat(B).isEqualTo(IntList.of(9, 7, 4));
    }

    @Test
    public void testShiftListDestructive() {
        IntList A = IntList.of(5, 4, 9, 1, 2, 3);
        IntList expected = IntList.of(4, 9, 1, 2, 3, 5);
        IntList actual = IntList.shiftListDestructive(A);
        assertThat(actual).isEqualTo(expected);
        assertThat(A).isNotEqualTo(IntList.of(5, 4, 9, 1, 2, 3));
    }
}

