import java.util.Map;
import java.util.Objects;

public class HuffmanDecoder {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("No .huf file input😭");
        }
        if (args.length == 1) {
            System.out.println("I don't know which file to write😭");
        }
        String inputFileName = args[0];
        String outputFileName = args[1];
        System.out.println("Reading the compressed file...");
        ObjectReader or = new ObjectReader(inputFileName);
        BinaryTrie bt = (BinaryTrie) or.readObject();
        int charLength = (int) or.readObject();
        BitSequence decodedSequence = (BitSequence) or.readObject();
        char[] originalSequence = new char[charLength];
        System.out.println("Decoding the file 🚀");
        for (int i = 0; i < charLength; i += 1) {
            Match nextMatch = bt.longestPrefixMatch(decodedSequence);
            decodedSequence = decodedSequence.allButFirstNBits(nextMatch.getSequence().length());
            originalSequence[i] = nextMatch.getSymbol();
        }
        System.out.println("Done! 🎉 The original file is " + outputFileName + " !~~");
        FileUtils.writeCharArray(outputFileName, originalSequence);
    }
}
