/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {
    public static int ASCII_TOTAL = 256;
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        String[] arrayToSort = new String[asciis.length];
        for (int i = 0; i < asciis.length; i += 1) {
            arrayToSort[i] = asciis[i];
        }
        int maxLength = 0;
        for (String s : asciis) {
            maxLength = Math.max(maxLength, s.length());
        }
        for (int i = 0; i < maxLength; i += 1) {
            sortHelperLSD(arrayToSort, i, maxLength);
        }
        return arrayToSort;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index, int maxLength) {
        String[] tmpArray = new String[asciis.length];
        for (int i = 0; i < asciis.length; i += 1) {
            tmpArray[i] = asciis[i];
        }
        int[] count = new int[ASCII_TOTAL];
        int[] start = new int[ASCII_TOTAL];
        for (String s : tmpArray) {
            char item = getCharAtIndexLSD(s, index, maxLength);
            count[item] += 1;
        }
        int pos = 0;
        for (int place = 0; place < ASCII_TOTAL; place += 1) {
            start[place] = pos;
            pos += count[place];
        }
        for (int i = 0; i < asciis.length; i += 1) {
            String s = tmpArray[i];
            int indexAtStart = getCharAtIndexLSD(s, index, maxLength);
            int position = start[indexAtStart];
            asciis[position] = s;
            start[indexAtStart]  += 1;
        }
    }
    private static char getCharAtIndexLSD(String s, int index, int maxLength) {
        int indexLSD = maxLength - index - 1;
        if (indexLSD >= s.length()) {
            return 0;
        }
        return s.charAt(indexLSD);
    }

    private static char getCharAtIndexMSD(String s, int index) {
        if (s.length() <= index) {
            return 0;
        }
        return s.charAt(index);
    }


    public static String[] sortMSD(String[] asciis) {
        String[] arrayToSort = new String[asciis.length];
        for (int i = 0; i < asciis.length; i += 1) {
            arrayToSort[i] = asciis[i];
        }
        int maxLength = 0;
        for (String s : arrayToSort) {
            maxLength = Math.max(maxLength, s.length());
        }
        sortHelperMSD(arrayToSort, 0, asciis.length, 0, maxLength);
        return arrayToSort;
    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index, int maxLength) {
        if (index == maxLength) {
            return;
        }
        if (end - start <= 1) {
            return;
        }
        String[] tmpReferenceCopy = new String[end - start];
        for (int i = start; i < end; i += 1) {
            tmpReferenceCopy[i - start] = asciis[i];
        }
        int[] counts = new int[ASCII_TOTAL];
        int[] startPlace = new int[ASCII_TOTAL];
        for (String s : tmpReferenceCopy) {
            char item = getCharAtIndexMSD(s, index);
            counts[item] += 1;
        }
        int pos = 0;
        for (int i = 0; i < ASCII_TOTAL; i += 1) {
            startPlace[i] += pos;
            pos += counts[i];
        }
        for (String s : tmpReferenceCopy) {
            char item = getCharAtIndexMSD(s, index);
            int position = startPlace[item];
            asciis[position + start] = s;
            startPlace[item] += 1;
        }
        int pointer = start;
        for (int count : counts) {
            if (count != 0) {
                sortHelperMSD(asciis, pointer, pointer + count, index + 1, maxLength);
                pointer += count;
            }
        }
    }
}
