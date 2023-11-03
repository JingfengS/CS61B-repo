package byog.world;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.algs4.StdRandom;
import org.checkerframework.checker.index.qual.LowerBoundBottom;

public class Hallway {
    Room firstRoom;
    Room secondRoom;

    public Hallway(Room r1, Room r2) {
        firstRoom = r1;
        secondRoom = r2;
    }

    private boolean isHorizonVertical() {
        return StdRandom.bernoulli();
    }

    public TETile[][] drawABlock(TETile[][] world, TETile block, int xx, int yy) {
        if (world[xx][yy] == Tileset.WALL || world[xx][yy] == Tileset.NOTHING) {
            world[xx][yy] = block;
        }
        return world;
    }

    private Point getLeftPoint(Point c1, Point c2) {
        if (c1.getX() < c2.getX()) {
            return c1;
        }
        return c2;
    }

    private  Point getRightPoint(Point c1, Point c2) {
        if (c1.getX() > c2.getX()) {
            return c1;
        }
        return c2;
    }

    private Point getLowPoint(Point c1, Point c2) {
        if (c1.getY() < c2.getY()) {
            return c1;
        }
        return c2;
    }

    private Point getUpPoint(Point c1, Point c2) {
        if (c1.getY() > c2.getY()) {
            return c1;
        }
        return c2;
    }

    public TETile[][] drawHallway(TETile[][] world) {
        Point c1 = firstRoom.getCenter();
        Point c2 = secondRoom.getCenter();
        if (isHorizonVertical()) {
            Point p1 = getLeftPoint(c1, c2);
            Point p2 = getRightPoint(c1, c2);
            int x1 = p1.getX();
            int y1 = p1.getY();
            int x2 = p2.getX();
            int y2 = p2.getY();
            while (x1 < x2) {
                x1 += 1;
                drawABlock(world, Tileset.FLOOR, x1, y1);
                drawABlock(world, Tileset.WALL, x1, y1 + 1);
                drawABlock(world, Tileset.WALL, x1, y1 - 1);
            }
            drawABlock(world, Tileset.FLOOR, x1, y1);
            if (y2 > y1) {
                drawABlock(world, Tileset.WALL, x1, y1 - 1);
                drawABlock(world, Tileset.WALL, x1 + 1, y1);
                drawABlock(world, Tileset.WALL, x1 + 1, y1 - 1);
            } else {
                drawABlock(world, Tileset.WALL, x1, y1 + 1);
                drawABlock(world, Tileset.WALL, x1 + 1, y1);
                drawABlock(world, Tileset.WALL, x1 + 1, y1 + 1);
            }
            int lowY = getLowPoint(c1, c2).getY();
            int upY = getUpPoint(c1, c2).getY();
            while (lowY < upY) {
                lowY += 1;
                drawABlock(world, Tileset.FLOOR, x2, lowY);
                drawABlock(world, Tileset.WALL, x2 + 1, lowY);
                drawABlock(world, Tileset.WALL, x2 - 1, lowY);
            }
        } else {
            Point p1 = getLowPoint(c1, c2);
            Point p2 = getUpPoint(c1, c2);
            int x1 = p1.getX();
            int x2 = p2.getX();
            int y1 = p1.getY();
            int y2 = p2.getY();
            while (y1 < y2) {
                y1 += 1;
                drawABlock(world, Tileset.FLOOR, x1, y1);
                drawABlock(world, Tileset.WALL, x1 - 1, y1);
                drawABlock(world, Tileset.WALL, x1 + 1, y1);
            }
            drawABlock(world, Tileset.FLOOR, x1, y1);
            if (x1 < x2) {
                drawABlock(world, Tileset.WALL, x1 - 1, y1);
                drawABlock(world, Tileset.WALL, x1 -1 , y1 + 1);
                drawABlock(world, Tileset.WALL, x1, y1 + 1);
            } else {
                drawABlock(world, Tileset.WALL, x1 + 1, y1);
                drawABlock(world, Tileset.WALL, x1 + 1, y1 + 1);
                drawABlock(world, Tileset.WALL, x1, y1 + 1);
            }
            int leftX = getLeftPoint(c1, c2).getX();
            int rightX = getRightPoint(c1, c2).getX();
            while (leftX < rightX) {
                leftX += 1;
                drawABlock(world, Tileset.FLOOR, leftX, y1);
                drawABlock(world, Tileset.WALL, leftX, y1 + 1);
                drawABlock(world, Tileset.WALL, leftX, y1 - 1);
            }
        }
        return world;
    }
}
