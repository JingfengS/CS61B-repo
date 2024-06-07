import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HuffmanEncoder {
    /**
     * Map characters to their counts.
     * @param inputSymbols
     * @return
     */
    public static Map<Character, Integer> buildFrequencyTable(char[] inputSymbols) {
        Map<Character, Integer> frequencyTable = new HashMap<>();
        for (char c : inputSymbols) {
            if (!frequencyTable.containsKey(c)) {
                frequencyTable.put(c, 0);
            }
            frequencyTable.put(c, frequencyTable.get(c) + 1);
        }
        return frequencyTable;
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("No input filename 😭");
            return;
        }
        System.out.println("Reading the file...");
        String inputFileName = args[0];
        char[] inputStream = FileUtils.readFile(inputFileName);
        Map<Character, Integer> frequencyTable = buildFrequencyTable(inputStream);
        System.out.println("Building Binary Trie...");
        BinaryTrie bt = new BinaryTrie(frequencyTable);
        ObjectWriter ow = new ObjectWriter(inputFileName + ".huf");
        Map<Character, BitSequence> lookupTable = bt.buildLookupTable();
        System.out.println("Encoding the text...");
        List<BitSequence> bitSequences = new ArrayList<>();
        for (char c : inputStream) {
            bitSequences.add(lookupTable.get(c));
        }
        BitSequence fullFileBitStream = BitSequence.assemble(bitSequences);
        System.out.println("Writing the zipped file...");
        ow.writeObject(bt);
        ow.writeObject(inputStream.length);
        ow.writeObject(fullFileBitStream);
        System.out.println("Done!🎉 The new zipped file is " + inputFileName + ".huf !~");
    }
}
