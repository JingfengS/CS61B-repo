import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class FilkTest {
    @Test
    public void testFile() {
        Integer a = 128;
        Integer b = 128;
        assertThat(a == b).isEqualTo(true);
        assertThat(Flik.isSameNumber(a, b)).isEqualTo(true);
    }
}
