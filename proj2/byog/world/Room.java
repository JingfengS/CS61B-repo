package byog.world;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.algs4.StdRandom;

public class Room {
    private int top;
    private int bottom;
    private int left;
    private int right;

    private Center center;

    private Door leftDoor;
    private Door rightDoor;
    private Door topDoor;
    private Door bottomDoor;

    public Point getCenter() {
        return center;
    }

    /**
     * map the room to world lst
     * @param world the world that contains all the blocks
     * @return world
     */
    public TETile[][] drawRoom(TETile[][] world) {
        for (int yy = bottom; yy <= top; yy += 1) {
            for (int xx = left; xx <= right; xx += 1) {
                if (yy == bottom || yy == top) {
                        world[xx][yy] = Tileset.WALL;
                } else if (xx == left || xx == right) {
                        world[xx][yy] = Tileset.WALL;
                } else {
                    world[xx][yy] = Tileset.FLOOR;
                }
            }
        }
        return world;
    }


    public Room(TreeNode node) {
        // assign the right and left of the room
        int pos1BetweenLeftAndRight = StdRandom.uniform(node.getLeft(), node.getRight());
        int pos2BetweenLeftAndRight = StdRandom.uniform(node.getLeft(), node.getRight());
        while (Math.abs(pos2BetweenLeftAndRight - pos1BetweenLeftAndRight) < 3) {
            pos1BetweenLeftAndRight = StdRandom.uniform(node.getLeft(), node.getRight());
            pos2BetweenLeftAndRight = StdRandom.uniform(node.getLeft(), node.getRight());
        }
        left = Math.min(pos1BetweenLeftAndRight, pos2BetweenLeftAndRight);
        right = Math.max(pos1BetweenLeftAndRight, pos2BetweenLeftAndRight);

        // assign the top and bottom of the room
        int pos1 = StdRandom.uniform(node.getDown(), node.getUp());
        int pos2 = StdRandom.uniform(node.getDown(), node.getUp());
        while (Math.abs(pos1 - pos2) <  3) {
            pos1 = StdRandom.uniform(node.getDown(), node.getUp());
            pos2 = StdRandom.uniform(node.getDown(), node.getUp());
        }
        bottom = Math.min(pos1, pos2);
        top = Math.max(pos1, pos2);
        center = new Center((left + right) / 2, (bottom + top) / 2);
        leftDoor = new Door(left, center.getY(), "left");
        rightDoor = new Door(right, center.getY(), "right");
        bottomDoor = new Door(center.centerX, bottom, "bottom");
        topDoor = new Door(center.centerX, top, "top");
    }

    private class Center implements Point {
        int centerX;
        int centerY;

        public Center(int x, int y) {
            centerX = x;
            centerY = y;
        }

        @Override
        public int getX() {
            return centerX;
        }

        @Override
        public int getY() {
            return centerY;
        }
    }

    private class Door implements Point {

        int x;
        int y;
        String position;

        public Door(int xx, int yy, String posi) {
            x = xx;
            y = yy;
            position = posi;
        }

        @Override
        public int getX() {
            return 0;
        }

        @Override
        public int getY() {
            return 0;
        }

        public String getPosition() {
            return position;
        }
    }
}
