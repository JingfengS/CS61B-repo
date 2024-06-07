import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

public class HuffmanEncoderTester {
    @Test
    public void testBuildFrequencyTable() {
        char[] charArray = {'a', 'b', 'b', 'c', 'c', 'c', 'c', 'd', 'd', 'd', 'd', 'e', 'e', 'f'};
        Map<Character, Integer> c2i = HuffmanEncoder.buildFrequencyTable(charArray);
        assertEquals(c2i.get('a'), (Integer)1);
        assertEquals(c2i.get('b'), (Integer)2);
        assertEquals(c2i.get('c'), (Integer)4);
        assertEquals(c2i.get('d'), (Integer)4);
        assertEquals(c2i.get('e'), (Integer)2);
        assertEquals(c2i.get('f'), (Integer)1);
    }
}
