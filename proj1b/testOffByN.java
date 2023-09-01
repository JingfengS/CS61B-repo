import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class testOffByN {

    @Test
    public void testEqualChars() {
        CharacterComparator cc = new OffByN(5);
        assertThat(cc.equalChars('a', 'f')).isTrue();
        assertThat(cc.equalChars('f', 'a')).isTrue();
        assertThat(cc.equalChars('a', 'z')).isFalse();
        assertThat(cc.equalChars('f', 'h')).isFalse();
    }

}
