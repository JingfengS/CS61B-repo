import TrieSet.TrieSet;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
public class TestTrieSet {
    private TrieSet wordTrie;
    private List<String> wordList;
    private static boolean initialized = false;

    @Before
    public void setUp() throws Exception {
        if (initialized) {
            return;
        }
        wordTrie = new TrieSet();
        wordList = new ArrayList<>();
        wordList.add("a");
        wordList.add("awls");
        wordList.add("sad");
        wordList.add("sam");
        wordList.add("same");
        wordList.add("sap");
        System.out.println("The words in the word list are: ");
        System.out.println(wordList.toString());
        for (String word : wordList) {
            wordTrie.addWord(word);
        }
    }

    @Test
    public void testEmpty() {
        TrieSet ts = new TrieSet();
        assertThat(ts.collect()).isEmpty();
    }

    @Test
    public void testAddWord() {
        assertThat(wordTrie.collect()).containsExactly("a", "awls", "sad", "sam", "same", "sap");
    }

    @Test
    public void testContains() {
        assertThat(wordTrie.contains("a")).containsExactly("a", "awls");
        assertThat(wordTrie.contains("sa")).containsExactly("sam", "same", "sad", "sap");
        assertThat(wordTrie.contains("sam")).containsExactly("sam", "same");
        assertThat(wordTrie.contains("sap")).containsExactly("sap");
    }
}
