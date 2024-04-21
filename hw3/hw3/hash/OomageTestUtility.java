package hw3.hash;

import java.util.*;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        Map<Integer, Integer> bucketNum2oomages = new HashMap<>();
        for (Oomage ooa : oomages) {
            int bucketNum = (ooa.hashCode() & 0x7FFFFFFF) % M;
            if (!bucketNum2oomages.containsKey(bucketNum)) {
                bucketNum2oomages.put(bucketNum, 0);
            }
            bucketNum2oomages.put(bucketNum, bucketNum2oomages.get(bucketNum) + 1);
        }
        boolean crowded = false;
        boolean sparse = false;
        for (int bucketContentsNum : bucketNum2oomages.values()) {
            if (bucketContentsNum > (double) oomages.size() / 2.5) {
                crowded = true;
            }
            if (bucketContentsNum < (double) oomages.size() / 50) {
                sparse = true;
            }
        }
        return !(crowded || sparse);
    }
}
