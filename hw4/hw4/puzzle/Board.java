package hw4.puzzle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;


public class Board implements WorldState {
    private final int [][] tiles;
    private static final int BLANK = 0;
    private final int [][] goal;

    /**
     * Constructs a board from an N-by-N array of tiles where
     * tiles[i][j] = tile at row i, column j
     * @param tiles the construct block
     */
    public Board(int [][] tiles) {
        this.tiles = new int[tiles.length][tiles.length];
        for (int i = 0; i < tiles.length; i += 1) {
            for (int j = 0; j < tiles.length; j += 1) {
               this.tiles[i][j] =  tiles[i][j];
            }
        }

        goal = new int[tiles.length][tiles.length];
        int expectValue = 1;
        for (int i = 0; i < tiles.length; i += 1) {
            for (int j = 0; j < tiles.length; j += 1) {
                goal[i][j] = expectValue;
                expectValue += 1;
            }
        }
        goal[tiles.length - 1][tiles.length - 1] = BLANK;
    }

    /**
     * Returns value of tile at row i, column j (or 0 if blank)
     * @param i row i
     * @param j column j
     * @return value of tile at row i, column j
     */
    public int tileAt(int i, int j) {
        if (i >= size() || j >= size()) {
            throw new IndexOutOfBoundsException("row or column index cannot be larger than N-1");
        }
        return tiles[i][j];
    }

    private int tileAtGoal(int i, int j) {
        if (i >= size() || j >= size()) {
            throw new IndexOutOfBoundsException("row or column index cannot be larger than N-1");
        }
        return goal[i][j];
    }

    /**
     * Return the board size N
     * @return the board size
     */
    public int size() {
        return tiles.length;
    }

    /**
     * Hamming estimate described below
     * @return the estimated steps to goal
     */
    public int hamming() {
        int elimatedSteps = 0;
        for (int i = 0; i < size(); i += 1) {
            for (int j = 0; j < size(); j += 1) {
                if (tileAt(i, j) != tileAtGoal(i, j) && tileAtGoal(i, j) != BLANK) {
                    elimatedSteps += 1;
                }
            }
        }
        return elimatedSteps;
    }

    private class Position {
        private int rowX;
        private int columnY;
        public Position(int x, int y) {
            if (x > size() || y > size()) {
                throw new IndexOutOfBoundsException("row or column index cannot be larger than N-1");
            }
            rowX = x;
            columnY = y;
        }

        public int getRowX() {
            return rowX;
        }

        public int getColumnY() {
            return columnY;
        }
    }

    private Position tilesSearch(int expectValue) {
        for (int i = 0; i < size(); i += 1) {
            for (int j = 0; j < size(); j += 1) {
                if (expectValue == tileAt(i, j)) {
                    return new Position(i, j);
                }
            }
        }
        throw new IllegalArgumentException("The expected value is not in tiles");
    }

    /**
     * Manhattan estimate described below
     * @return the estimated steps to goal
     */
    public int manhattan() {
        int elimatedSteps = 0;
        for (int i = 0; i < size(); i += 1) {
            for (int j = 0; j < size(); j += 1) {
                if (tileAtGoal(i, j) != BLANK) {
                    Position tilePosition = tilesSearch(tileAtGoal(i, j));
                    elimatedSteps += Math.abs(tilePosition.getRowX() - i);
                    elimatedSteps += Math.abs(tilePosition.getColumnY() - j);
                }
            }
        }
        return elimatedSteps;
    }

    /**
     * Return true if this board's tile values are the same position as y's
     * @param y other board
     * @return true if they are equal, false otherwise
     */
//    @Override
//    public boolean equals(Object y) {
//        if (this == y) {
//            return true;
//        }
//        if (y instanceof Board otherBoard) {
//            if (otherBoard.size() != size()) {
//                return false;
//            }
//            for (int i = 0; i < size(); i++) {
//                for (int j = 0; j < size(); j++) {
//                    if (tileAt(i, j) != otherBoard.tileAt(i, j)) {
//                        return false;
//                    }
//                }
//            }
//            return true;
//        }
//        return false;
//    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        Board board1 = (Board) other;
        if (size() != board1.size()) {
            return false;
        }
        for (int i = 0; i < size(); i += 1) {
            for (int j = 0; j < size(); j += 1) {
                if (tileAt(i, j) != board1.tileAt(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }


    @Override
    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    /**
     * Returns neighbors of this board.
     * SPOILERZ: This is the answer.
     * @source https://joshh.ug/neighbors.html
     */
    @Override
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == BLANK) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = BLANK;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = BLANK;
                }
            }
        }
        return neighbors;
    }

    /** Returns the string representation of the board.*/
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
