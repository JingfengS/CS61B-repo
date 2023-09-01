import org.junit.Test;


import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        assertTrue(palindrome.isPalindrome("a"));
        assertTrue(palindrome.isPalindrome("racecar"));
        assertTrue(palindrome.isPalindrome("noon"));
        assertFalse(palindrome.isPalindrome("horse"));
        assertFalse(palindrome.isPalindrome("rancor"));
        assertTrue(palindrome.isPalindrome(""));
        assertFalse(palindrome.isPalindrome("aaaab"));
        assertTrue(palindrome.isPalindrome("aa"));
        assertFalse(palindrome.isPalindrome("ab"));
        assertTrue(palindrome.isPalindrome("aba"));
        assertFalse(palindrome.isPalindrome("abc"));
        assertFalse(palindrome.isPalindrome("abbc"));
        assertTrue(palindrome.isPalindrome("abccba"));

    }

    @Test
    public void testIsPalindromeV2() {
        CharacterComparator cc = new OffByOne();
        assertTrue(palindrome.isPalindrome("", cc));
        assertTrue(palindrome.isPalindrome("a", cc));
        assertTrue(palindrome.isPalindrome("ab", cc));
        assertFalse(palindrome.isPalindrome("gj", cc));
        assertTrue(palindrome.isPalindrome("amb", cc));
        assertFalse(palindrome.isPalindrome("kmb", cc));
        assertFalse(palindrome.isPalindrome("bmik", cc));
        assertTrue(palindrome.isPalindrome("bmna", cc));
        assertTrue(palindrome.isPalindrome("bcdba", cc));
        assertFalse(palindrome.isPalindrome("abjfs", cc));
    }

}
