package byog.lab6;

import edu.princeton.cs.introcs.StdDraw;
import net.sf.saxon.trans.SymbolicName;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private final int NUMOFLETTERS = 26;
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, long seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        //TODO: Initialize random number generator
        rand = new Random(seed);
    }

    public String generateRandomString(int n) {
        //TODO: Generate random string of letters of length n
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < n; i += 1) {
            s.append(CHARACTERS[rand.nextInt(NUMOFLETTERS)]);
        }
        return s.toString();
    }

    public void drawFrame(String s, int round, boolean playerTurn) {
        //TODO: Take the string and display it in the center of the screen
        //TODO: If game is not over, display relevant game information at the top of the screen
        Font topFont = new Font("Monaco", Font.BOLD, 15);
        Font centerFont = new Font("Monaco", Font.BOLD, 30);
        String encourageString = ENCOURAGEMENT[rand.nextInt(ENCOURAGEMENT.length)];
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setFont(topFont);
        StdDraw.textLeft(0., (double) height - 1, "Round: " + round);
        StdDraw.text((double) width / 2, (double) height - 1, playerTurn? "Type!" : "Watch!");
        StdDraw.textRight((double) width, (double) height - 1, encourageString);
        StdDraw.line(0., (double) height - 2, width, (double) height - 2);
        StdDraw.setFont(centerFont);
        StdDraw.text((double) width / 2, (double) height / 2, s);
        StdDraw.show();
    }

    public void flashSequence(String letters, int round, boolean playerTurn) {
        //TODO: Display each character in letters, making sure to blank the screen between letters
        for (int i = 0; i < letters.length(); i += 1) {
            drawFrame(letters.substring(i, i + 1), round, playerTurn);
            StdDraw.pause(1000);
            drawFrame("", round, playerTurn);
            StdDraw.pause(500);
        }
    }

    public String solicitNCharsInput(int n, int round, boolean playerTurn) {
        //TODO: Read n letters of player input
        StringBuilder input = new StringBuilder();
        while (n > 0) {
            if (StdDraw.hasNextKeyTyped()) {
                char inputChar = StdDraw.nextKeyTyped();
                n -= 1;
                input.append(inputChar);
                drawFrame(input.toString(), round, playerTurn);
            }
            continue;
        }
        return input.toString();
    }

    public boolean OneGameRound(int round) {
        playerTurn = false;
        drawFrame("Round: " + round, round, playerTurn);
        String expectedString = generateRandomString(round);
        flashSequence(expectedString, round, playerTurn);
        playerTurn = true;
        String actualInput = solicitNCharsInput(expectedString.length(), round, playerTurn);
        StdDraw.pause(500);
        return !actualInput.equals(expectedString);
    }

    public void startGame() {
        //TODO: Set any relevant variables before the game starts
        round = 1;
        gameOver = false;
        //TODO: Establish Game loop
        while (!gameOver) {
            gameOver = OneGameRound(round);
            round += 1;
        }
        drawFrame("Game Over! You made it to round: " + (round - 1), round - 1, playerTurn);
    }
}
