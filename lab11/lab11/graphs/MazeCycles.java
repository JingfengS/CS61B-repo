package lab11.graphs;

import java.util.HashSet;
import java.util.Set;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private final int s;
    private boolean hasCycle;
    private int circleV;
    private int[] edgeToCopy;

    public MazeCycles(Maze m) {
        super(m);
        hasCycle = false;
        s = 0;
        edgeTo[s] = s;
        circleV = -1;
        edgeToCopy = edgeTo.clone();
    }

    private boolean isMarked(int v) {
        return marked[v];
    }

    private void mark(int v) {
        marked[v] = true;
    }

    private boolean hasCycleDfs(int v, int parent) {
        if (isMarked(v)) {
            return true;
        }
        mark(v);
        announce();
        for (int neighbor : maze.adj(v)) {
            if (neighbor != parent) {
                edgeToCopy[neighbor] = v;
                boolean detectCycle = hasCycleDfs(neighbor, v);
                if (detectCycle) {
                    if (circleV == -1) {
                        circleV = neighbor;
                    }
                    return true;
                }
            }
        }
        return false;
    }
    private void displayCircle() {
        if (!hasCycle) {
            return;
        }
        int prev;
        int curr;
        for (curr = circleV; previousVertex(curr) != circleV; curr = previousVertex(curr)) {
            prev = previousVertex(curr);
            connect(curr, prev);
        }
        prev = previousVertex(curr);
        connect(curr, prev);
        announce();
    }

    private int previousVertex(int v) {
        return edgeToCopy[v];
    }

    private void connect(int curr, int prev) {
        edgeTo[curr] = prev;
    }

    @Override
    public void solve() {
        hasCycle = hasCycleDfs(s, -1);
        if (hasCycle) {
            displayCircle();
            throw new RuntimeException("This Graph now has cycles");
        }
    }
    // Helper methods go here
}

