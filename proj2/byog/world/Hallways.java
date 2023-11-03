package byog.world;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.List;

public class Hallways {
    private List<Room> rooms;
    public Hallways(List<Room> rs) {
        rooms = rs;
    }

    public TETile[][] drawHallways(TETile[][] world) {
        for (int i = 0; i < rooms.size() - 1; i += 1) {
            Hallway hallway = new Hallway(rooms.get(i), rooms.get(i + 1));
            hallway.drawHallway(world);
        }
        return world;
    }
}
