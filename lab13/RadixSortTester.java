import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;
public class RadixSortTester {

    @Test
    public void testOfSameLength() {
        String[] unsorted = {"dat", "cat", "dog", "bat", "hat", "cak", "dig", "dad"};
        String[] sorted = {"bat", "cak", "cat", "dad", "dat", "dig", "dog", "hat"};

        String[] result = RadixSort.sort(unsorted);
        assertThat(result).asList().containsExactlyElementsIn(sorted).inOrder();
    }

    @Test
    public void testRadixSort() {
        String[] unsorted = {"cat", "bat", "dog", "hat", "apple", "bear", "pear", "app"};
        String[] sorted = {"app", "apple", "bat", "bear", "cat", "dog", "hat", "pear"};

        String[] result = RadixSort.sort(unsorted);
        assertThat(result).asList().containsExactlyElementsIn(sorted).inOrder();
    }
    @Test
    public void testOfSameLengthMSD() {
        String[] unsorted = {"cat", "dog", "bat", "hat", "cak", "dig", "dad"};
        String[] sorted = {"bat", "cak", "cat", "dad", "dig", "dog", "hat"};

        String[] result = RadixSort.sortMSD(unsorted);
        assertThat(result).asList().containsExactlyElementsIn(sorted).inOrder();
    }

    @Test
    public void testRadixSortMSD() {
        String[] unsorted = {"cat", "bat", "dog", "hat", "apple", "bear", "pear", "app"};
        String[] sorted = {"app", "apple", "bat", "bear", "cat", "dog", "hat", "pear"};

        String[] result = RadixSort.sortMSD(unsorted);
        assertThat(result).asList().containsExactlyElementsIn(sorted).inOrder();
    }

    @Test
    public void externalTest() {
        String[] unsorted = {"#=","E","#"};
        String[] sorted = {"#", "#=", "E"};

        String[] result = RadixSort.sortMSD(unsorted);
        assertThat(result).asList().containsExactlyElementsIn(sorted).inOrder();
    }
}
