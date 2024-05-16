package lab11.graphs;

import java.util.LinkedList;
import java.util.Queue;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private final int s;
    private final int t;
    private boolean targetFound;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = 0;
        targetFound = false;
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        // TODO: Your code here. Don't forget to update distTo, edgeTo, and marked, as well as call announce()
        if (s == t) {
            targetFound = true;
        }
        if (targetFound) {
            return;
        }
        Queue<Integer> fringe = new LinkedList<>();
        fringe.add(s);
        mark(s);
        announce();
        while (!fringe.isEmpty()) {
            int current = fringe.remove();
            for (int neighbor : maze.adj(current)) {
                if (!isMarked(neighbor)) {
                    edgeTo[neighbor] = current;
                    distTo[neighbor] = distTo[current] + 1;
                    mark(neighbor);
                    announce();
                    if (neighbor == t) {
                        targetFound = true;
                        return;
                    }
                    fringe.add(neighbor);
                }
            }
        }
    }

    private void mark(int v) {
        marked[v] = true;
    }

    private boolean isMarked(int v) {
        return marked[v];
    }


    @Override
    public void solve() {
         bfs();
    }
}

