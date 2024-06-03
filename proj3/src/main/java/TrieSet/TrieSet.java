package TrieSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is my implementation of the TrieSet data structure
 * This is used for autocomplete in the map
 */
public class TrieSet {
    private class Node {
        boolean isKey;
        Map<Character, Node> map;
        Node() {
            isKey = false;
            map = new HashMap<>();
        }

        Node(boolean isKey) {
            this.isKey = isKey;
            this.map = new HashMap<>();
        }
    }

    Node root;

    /**
     * Return a list of strings that contains the prefix string
     * @param prefix
     * @return a list of strings that contains the prefix string
     */
    public List<String> contains(String prefix) {
       return null;
    }

    private Node addChar(char c, boolean isKey, Node root) {
        if (root.map.containsKey(c)) {
            if (!root.map.get(c).isKey && isKey) {
                root.map.get(c).isKey = true;
            }
            return root.map.get(c);
        }
        root.map.put(c, new Node(isKey));
        return root.map.get(c);
    }

    /**
     * Add the word to the trie
     * @param word the word to add in the trie
     */
    public void addWord(String word) {
        Node n = root;
        for (int i = 0; i < word.length(); i += 1) {
            if (i == word.length() - 1) {
                n = addChar(word.charAt(i), true, n);
            } else {
                n = addChar(word.charAt(i), false, n);
            }
        }
    }

    /**
     * @return all the strings that is in the trie
     */
    public List<String> collect() {
        List<String> wordList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        collectHelper(sb, root, wordList);
        return wordList;
    }

    private void collectHelper(StringBuilder sb, Node root, List<String> wordList) {
        if (root.isKey) {
            wordList.add(sb.toString());
        }
        if (root.map.isEmpty()) {
            return;
        }
        for (char nextChar : root.map.keySet()) {
            collectHelper(sb.append(nextChar), root.map.get(nextChar), wordList);
        }
    }
}
