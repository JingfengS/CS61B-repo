package byog.world;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import byog.interactivity.Player;
import edu.princeton.cs.algs4.StdRandom;

import java.io.Serializable;
import java.util.List;

public class World implements Serializable {
    private final long SEED;
    private TETile[][] world;
    private final int WIDTH;
    private final int HEIGHT;
    private Player player;

    public World(long seed, int width, int height) {
        WIDTH = width;
        HEIGHT = height;
        world = new TETile[WIDTH][HEIGHT];
        SEED = seed;
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        List<Room> rooms = new Rooms(WIDTH, HEIGHT, SEED).getRooms();
        for (Room room : rooms) {
            room.drawRoom(world);
        }
        Hallways hs = new Hallways(rooms);
        hs.drawHallways(world);
        int DoorX = StdRandom.uniform(1, WIDTH - 1);
        int DoorY = StdRandom.uniform(1, HEIGHT - 1);
        while (!wallNextToFloor(DoorX, DoorY, world)) {
            DoorX = StdRandom.uniform(1, WIDTH - 1);
            DoorY = StdRandom.uniform(1, HEIGHT - 1);
        }
        world[DoorX][DoorY] = Tileset.LOCKED_DOOR;
        player = new Player(world);
    }

    private static boolean wallNextToFloor(int x, int y, TETile[][] world) {
        return  (world[x][y].equals(Tileset.WALL)) &&
                ((world[x][y + 1].equals(Tileset.FLOOR)) ||
                (world[x][y-1].equals(Tileset.FLOOR)) ||
                (world[x-1][y].equals(Tileset.FLOOR)) ||
                (world[x+1][y].equals(Tileset.FLOOR)));
    }

    public TETile[][] getWorld() {
        return world;
    }
    public Player getPlayer() {
        return player;
    }
}
