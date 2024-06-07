import edu.princeton.cs.algs4.MinPQ;
import net.sf.saxon.expr.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Class with 2 ways of doing Counting sort, one naive way and one "better" way
 *
 * @author Akhil Batra, Alexander Hwang
 *
 **/
public class CountingSort {
    /**
     * Counting sort on the given int array. Returns a sorted version of the array.
     * Does not touch original array (non-destructive method).
     * DISCLAIMER: this method does not always work, find a case where it fails
     *
     * @param arr int array that will be sorted
     * @return the sorted array
     */
    public static int[] naiveCountingSort(int[] arr) {
        return countingSortHelper(arr, 0);
    }

    /**
     * Counting sort on the given int array, must work even with negative numbers.
     * Note, this code does not need to work for ranges of numbers greater
     * than 2 billion.
     * Does not touch original array (non-destructive method).
     *
     * @param arr int array that will be sorted
     */
    public static int[] betterCountingSort(int[] arr) {
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for (int i : arr) {
            max = Math.max(max, i);
            min = Math.min(min, i);
        }
        int compliment = Math.abs(min);
        for (int i = 0; i < arr.length; i += 1) {
            arr[i] += compliment;
        }
        return countingSortHelper(arr, compliment);
    }

    private static int[] countingSortHelper(int [] arr, int compliment) {
        int max = Integer.MIN_VALUE;
        for (int i : arr) {
            max = Math.max(max, i);
        }
        int[] count = new int[max + 1];
        int[] start = new int[max + 1];
        for (int place : arr) {
            count[place] += 1;
        }

        int pos = 0;
        for (int i = 0; i < count.length; i += 1) {
            start[i] = pos;
            pos += count[i];
        }
        int[] sorted = new int[arr.length];
        for (int item : arr) {
            int position = start[item];
            sorted[position] = item - compliment;
            start[item] += 1;
        }
        return sorted;
    }
}
