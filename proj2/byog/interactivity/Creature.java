package byog.interactivity;

import byog.TileEngine.TETile;

import java.io.Serializable;

public interface Creature extends Serializable {
    /**
     * Initialize the Creature in the world
     * @return the world after mutation
     */
    public TETile[][] initialize();

    /**
     * Make the creature position from (x, y) to (x + 1, y)
     * @return the world
     */
    public TETile[][] moveRight();

    /**
     * Make the creature position from (x, y) to (x - 1, y);
     * @return the world
     */
    public TETile[][] moveLeft();

    /**
     * Make the creature position from (x, y) to (x, y + 1)
     * @return teh world
     */
    public TETile[][] moveUp();

    /**
     * Make the creature position from (x, y) to (x, y - 1)
     * @return the world
     */
    public TETile[][] moveDown();

    /**
     * Make the creature act
     * @return the world
     */
    public TETile[][] action(char keyPress);

    /**
     * @return X position of the creature
     */
    public int getX();

    /**
     * @return Y position of the creature
     */
    public int getY();

    /**
     * @return the TETile block the creature is standing on
     */
    public TETile getStandOn();
}
