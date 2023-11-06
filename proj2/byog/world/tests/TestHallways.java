package byog.world.tests;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import byog.world.Hallway;
import byog.world.Hallways;
import byog.world.Room;
import byog.world.Rooms;

import java.util.List;

public class TestHallways {
    public static void main(String[] args) {
        final int WIDTH = 60;
        final int HEIGHT = 50;
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = new TETile[WIDTH][HEIGHT];

        for (int i = 0; i < WIDTH; i += 1) {
            for (int j = 0; j < HEIGHT; j += 1) {
                world[i][j] = Tileset.NOTHING;
            }
        }
        List<Room> rooms = new Rooms(WIDTH, HEIGHT, 200).getRooms();
        for (Room room : rooms) {
            room.drawRoom(world);
        }
        Hallways hs = new Hallways(rooms);
        hs.drawHallways(world);
        ter.renderFrame(world);
    }
}
