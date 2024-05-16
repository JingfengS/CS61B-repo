package lab11.graphs;

import com.google.common.collect.MinMaxPriorityQueue;
import edu.princeton.cs.algs4.MinPQ;
import net.sf.saxon.expr.Component;
import net.sf.saxon.functions.Minimax;

import java.util.*;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private final int s;
    private final int t;
    private boolean targetFound = false;
    private Maze maze;
    private MinPQ<Vertex> fringe;

    private Set<Integer> visited;
    private static final boolean showDistance = false;

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        edgeTo[s] = s;
        visited = new HashSet<>();
        fringe = new MinPQ<>();
        fringe.insert(new Vertex(s, s, 0));
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        int vX = maze.toX(v);
        int vY = maze.toY(v);
        int tX = maze.toX(t);
        int tY = maze.toY(t);
        return Math.abs(vX - tX) + Math.abs(vY - tY);
    }

    private void setEdge(Vertex v) {
        edgeTo[v.getV()] = v.getParent();
    }

    private void setDistanceToV(Vertex v) {
        distTo[v.getV()] = v.getDistance();
    }


    /** Performs an A star search from vertex s. */
    private void astar() {
        while (!fringe.isEmpty()) {
            Vertex v = fringe.delMin();
            visited.add(v.getV());
            setEdge(v);
            if (showDistance) {
                setDistanceToV(v);
            }
            marked[v.getV()] = true;
            announce();
            if (v.getV() == t) {
                targetFound = true;
            }
            if (targetFound) {
                return;
            }
            for (Vertex n : v.neighbors()) {
                if (!visited.contains(n.getV())) {
                   fringe.insert(n);
                }
            }
        }
        throw new RuntimeException("Cannot find edge to T!");
    }

    @Override
    public void solve() {
        astar();
    }

    private class Vertex implements Comparable<Vertex> {
        private final int v;
        private final int estimatedValueToTarget;
        private final int parent;

        private final int distance;

        public Vertex(int v, int p, int distance) {
            this.v = v;
            this.parent = p;
            this.distance = distance;
            this.estimatedValueToTarget = distance + h(v);
        }

        public int getV() {
            return v;
        }

        public int getParent() {
            return parent;
        }

        public int getDistance() {
            return distance;
        }

        public Iterable<Vertex> neighbors() {
            List<Vertex> adjs = new ArrayList<>();
            for (int neighbor : maze.adj(v)) {
                if (neighbor != parent) {
                    adjs.add(new Vertex(neighbor, v, distance + 1));
                }
            }
            return adjs;
        }

        @Override
        public int compareTo(Vertex o) {
            return estimatedValueToTarget - o.estimatedValueToTarget;
        }
    }
}

