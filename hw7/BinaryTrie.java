import org.apache.hc.core5.http.Header;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class BinaryTrie implements Serializable {
    private static byte CHILD_LENGTH = 2;
    private static byte LEFT = 0;
    private static byte RIGHT = 1;
    private interface TrieNode {
        boolean isLeaf();
        int getFrequency();
        char getValue();

        boolean hasChild(int child);

        TrieNode getChild(int child);
    }

    private class Branch implements TrieNode, Comparable<TrieNode> {
        int frequency;
        TrieNode child[];
        public Branch(TrieNode leftChild, TrieNode rightChild) {
            frequency = leftChild.getFrequency() + rightChild.getFrequency();
            child = new TrieNode[CHILD_LENGTH];
            child[LEFT] = leftChild;
            child[RIGHT] = rightChild;
        }
        @Override
        public boolean isLeaf() {
            return false;
        }

        @Override
        public int getFrequency() {
            return frequency;
        }

        @Override
        public char getValue() {
            throw new RuntimeException("Cannot get value of a Branch.");
        }

        @Override
        public boolean hasChild(int child) {
            return this.child[child] != null;
        }

        @Override
        public TrieNode getChild(int child) {
            if (!hasChild(child)) {
                throw new IllegalArgumentException("This trienode does not have any child!");
            }
            return this.child[child];
        }


        @Override
        public int compareTo(TrieNode o) {
            return Integer.compare(this.frequency, o.getFrequency());
        }
    }

    private class Leaf implements TrieNode, Comparable<TrieNode> {
        int frequency;
        char value;

        public Leaf(int frequency, char value) {
            this.frequency = frequency;
            this.value = value;
        }

        @Override
        public boolean isLeaf() {
            return true;
        }

        @Override
        public int getFrequency() {
            return frequency;
        }

        @Override
        public char getValue() {
            return value;
        }

        @Override
        public int compareTo(TrieNode o) {
            return Integer.compare(this.frequency, o.getFrequency());
        }

        @Override
        public boolean hasChild(int child) {
            return false;
        }

        @Override
        public TrieNode getChild(int child) {
                throw new IllegalArgumentException("Leaf cannot have any child!");
        }
    }
    private PriorityQueue<TrieNode> heap;
    private TrieNode root;

    private boolean isEmpty() {
        return root == null;
    }

    /**
     * Given a frequency table with maps symbols of type V to their relative
     * frequencies, the constructor should build a Huffman decoding trie according
     * to the procedure discussed in class.
     * @param frequencyTable maps symbols of type V to their relative frequencies
     */
    public BinaryTrie(Map<Character, Integer> frequencyTable) {
        heap = new PriorityQueue<>();
        for (char c : frequencyTable.keySet()) {
            heap.add(new Leaf(frequencyTable.get(c), c));
        }
        if (heap.isEmpty()) {
            return;
        }
        while (heap.size() > 1) {
            TrieNode leftNode = heap.remove();
            TrieNode rightNode = heap.remove();
            heap.add(new Branch(leftNode, rightNode));
        }
        root = heap.remove();
    }

    /**
     * Finds the longest prefix that matches the given querySequence and returns a Match object for
     * that match.
     * @param querySequence bitSequence
     * @return a Match
     */
    public Match longestPrefixMatch(BitSequence querySequence) {
        TrieNode p = root;
        for (int i = 0; i < querySequence.length(); i += 1) {
            int nextBit = querySequence.bitAt(i);
            if (p.hasChild(nextBit)) {
                p = p.getChild(nextBit);
            } else {
                throw new IllegalArgumentException("No matches for the given querySequence");
            }
            if (p.isLeaf()) {
                return new Match(querySequence.firstNBits(i + 1), p.getValue());
            }
        }
        throw new IllegalArgumentException("No matches for the given querySequence");
    }

    /**
     * @return the inverse of the coding trie.
     */
    public Map<Character, BitSequence> buildLookupTable() {
        Map<Character, BitSequence> lookupTable = new HashMap<>();
        buildLookupTableHelper(lookupTable, root, new StringBuilder());
        return lookupTable;
    }

    private void buildLookupTableHelper(Map<Character, BitSequence> buildingTable, TrieNode node, StringBuilder sb) {
        if (node.isLeaf()) {
            buildingTable.put(node.getValue(), new BitSequence(sb.toString()));
            return;
        }
        StringBuilder leftBuilder = sb;
        StringBuilder rightBuilder = new StringBuilder(sb);
        buildLookupTableHelper(buildingTable, node.getChild(LEFT), leftBuilder.append(LEFT));
        buildLookupTableHelper(buildingTable, node.getChild(RIGHT), rightBuilder.append(RIGHT));
    }
}
