package lab11.graphs;

/**
 *  @author Josh Hug
 */
public class MazeDepthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private final int s;
    private final int t;
    private boolean targetFound = false;

    private static final  boolean showDistance = false;


    public MazeDepthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        if (showDistance) {
            distTo[s] = 0;
        }
        edgeTo[s] = s;
    }

    private void dfs(int v) {
        marked[v] = true;
        announce();

        if (v == t) {
            targetFound = true;
        }

        if (targetFound) {
            return;
        }

        for (int w : maze.adj(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                if (showDistance) {
                    distTo[w] = distTo[v] + 1;
                }
                announce();
                dfs(w);
                if (targetFound) {
                    return;
                }
            }
        }
    }

    @Override
    public void solve() {
        dfs(s);
    }
}

