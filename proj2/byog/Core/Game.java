package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.interactivity.Player;
import byog.interactivity.Savings;
import byog.interactivity.Savings.Saving;
import byog.world.World;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 60;
    public static final int HEIGHT = 50;
    private boolean gameOver;
    private World world;
    private long seed;
    private final Font title = new Font("Cosmic Sans MS", Font.BOLD, 30);
    private final Font option = new Font("Cosmic Sans MS", Font.BOLD, 20);
    private final Font suggestion = new Font("Cosmic Sans MS", Font.BOLD, 16);
    private final int tileSize = 16;
    private final int MENU_WIDTH = 40;
    private final int MENU_HEIGHT = 40;

    public String worldName;
    private StringBuilder worldNameTem;
    private Savings savings;

    private void Initialize() {
        savings = new Savings();
    }

    private void MainMenu() {
        StdDraw.setCanvasSize(MENU_WIDTH * 16, MENU_HEIGHT * 16);
        StdDraw.setXscale(0, MENU_WIDTH);
        StdDraw.setYscale(0, MENU_HEIGHT);
        StdDraw.enableDoubleBuffering();

        StdDraw.clear(Color.BLACK);
        StdDraw.setFont(title);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text((double) MENU_WIDTH / 2, (double) MENU_HEIGHT * 2 / 3, "CS61B Game");
        StdDraw.setFont(option);
        StdDraw.text((double) MENU_WIDTH / 2, (double) MENU_HEIGHT / 2 - 5 + 2, "New Game (N)");
        StdDraw.text((double) MENU_WIDTH / 2, (double) MENU_HEIGHT / 2 - 5, "Load Game (L)");
        StdDraw.text((double) MENU_WIDTH / 2, (double) MENU_HEIGHT / 2 - 5 - 2, "Quit (Q)");
        StdDraw.show();
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                if (key == 'N') {
                    newGame();
                } else if (key == 'L') {
                    loadGame();
                } else if (key == 'Q') {
                    System.exit(0);
                }
            }
        }
    }

    private void drawWarning(String warning) {
        StdDraw.clear(Color.BLACK);
        StdDraw.text(MENU_WIDTH / 2, MENU_HEIGHT / 2, warning);
        StdDraw.show();
        StdDraw.pause(2000);
    }

    private void drawFrame(String typed, String worldName) {
        StdDraw.clear(Color.BLACK);
        StdDraw.text(MENU_WIDTH / 2, MENU_HEIGHT / 2, "Please Enter Seed: " + typed);
        StdDraw.text(MENU_WIDTH / 2, MENU_HEIGHT / 2 - 2, "Please Enter World Name: " + worldName);
        StdDraw.text(MENU_WIDTH / 2, MENU_HEIGHT / 2 - 4, "End with #");
        StdDraw.show();
    }

    private void newGame() {
        StringBuilder typed = new StringBuilder();
        worldNameTem = new StringBuilder();
        drawFrame(typed.toString(), worldNameTem.toString());
        int line = 2;
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                if (key == '#') {
                    line -= 1;
                }
                if (line == 2) {
                    typed.append(key);
                    drawFrame(typed.toString(), worldNameTem.toString());
                } else if (line == 1) {
                    if (key != '#') {
                        worldNameTem.append(key);
                    }
                    drawFrame(typed.toString(), worldNameTem.toString());
                } else {
                    seed = Long.parseLong(typed.toString());
                    worldName = worldNameTem.toString();
                    if (savings.getSavings().containsKey(worldName)) {
                        drawWarning("This World Already exists!");
                        MainMenu();
                    }
                    world = new World(seed, WIDTH, HEIGHT);
                    playTheGame();
                }
            }
        }
    }

    private void drawLoadMenu(String name) {
        double startHeight = MENU_HEIGHT * 4. / 5.;
        StdDraw.setFont(suggestion);
        StdDraw.clear(Color.BLACK);
        List<String> savingsList = new ArrayList<>(savings.getSavings().keySet());
        for (int i = 0; i < savingsList.size(); i += 1) {
            StdDraw.text((double) MENU_WIDTH / 2, startHeight - i * 1.2, savingsList.get(i));
        }
        StdDraw.setFont(option);
        StdDraw.text((double) MENU_WIDTH / 2, 5., "Enter the world name: " + name);
        StdDraw.text((double) MENU_WIDTH / 2, 3., "End with #");
        StdDraw.show();
    }

    private void loadGame() {
        drawLoadMenu("");
        StringBuilder name = new StringBuilder();
        Map<String, Saving> saves = savings.getSavings();
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                if (key != '#') {
                    name.append(key);
                    drawLoadMenu(name.toString());
                } else {
                    worldName = name.toString();
                    if (saves.containsKey(worldName)) {
                        world = saves.get(worldName).getWorld();
                        playTheGame();
                    } else {
                        drawWarning("The World does not Exist, Unable to Load.");
                        MainMenu();
                    }
                }
            }
        }
    }

    /**
     * Handle the play process of the game
     */
    private void playTheGame() {
        gameOver = false;
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        ter.renderFrame(world.getWorld());
        while (!gameOver) {
            if (StdDraw.hasNextKeyTyped()) {
                char keyPress = StdDraw.nextKeyTyped();
                if (keyPress != 'Q') {
                    Player p = world.getPlayer();
                    p.action(keyPress);
                } else {
                    savings.save(world, worldName);
                    System.exit(0);
                }
            }
            ter.renderFrame(world.getWorld());
        }
    }

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        Initialize();
        MainMenu();
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        long seed;


        TETile[][] finalWorldFrame = null;
        return finalWorldFrame;
    }
}
