package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;

public class Solver {
    private class SearchNode implements Comparable<SearchNode> {
        WorldState word;
        SearchNode parent;
        int steps;
        int priority;

        public SearchNode(WorldState word, int steps, SearchNode parent) {
            this.word = word;
            this.steps = steps;
            this.parent = parent;
            if (cashingEstimatedDistance.containsKey(this.word)) {
                this.priority = steps + cashingEstimatedDistance.get(this.word);
            } else {
                this.priority = steps + word.estimatedDistanceToGoal();
            }
        }

        public Iterable<SearchNode> neighbors() {
            HashSet<SearchNode> neighbors = new HashSet<>();
            for (WorldState w : word.neighbors()) {
                if (parent == null || !w.equals(parent.word)) {
                    SearchNode newNeighbor = new SearchNode(w, steps + 1, this);
                    neighbors.add(newNeighbor);
                }
            }
            return neighbors;
        }

        @Override
        public int compareTo(SearchNode o) {
            return this.priority - o.priority;
        }
    }

    private MinPQ<SearchNode> pq;
    private Deque<WorldState> solution;
    private HashMap<WorldState, Integer> cashingEstimatedDistance;
    private int steps;

    /**
     * Constructor which solves the puzzle, computing
     * everything necessary for moves() and solution() to
     * not have to solve the problem again. Solves the
     * puzzle using the A* algorithm. Assumes a solution exists.
     * @param initial the initial worldState
     */
    public Solver(WorldState initial) {
        pq = new MinPQ<>();
        solution = new ArrayDeque<>();
        cashingEstimatedDistance = new HashMap<>();
        SearchNode initialNode = new SearchNode(initial, 0, null);
        pq.insert(initialNode);
        solve();
    }

    private void solve() {
        SearchNode current = pq.delMin();
        while (!current.word.isGoal()) {
            for (SearchNode neighbor : current.neighbors()) {
                cashingEstimatedDistance.put(neighbor.word, neighbor.priority - neighbor.steps);
                pq.insert(neighbor);
            }
            current = pq.delMin();
        }
        steps = current.steps;
        while (current != null) {
            solution.addFirst(current.word);
            current = current.parent;
        }
    }

    /**
     * Returns the minimum number of moves to solve the puzzle starting
     * at the initial WorldState.
     * @return the minimum number of moves to solve the puzzle
     */
    public int moves() {
        return steps;
    }

    /**
     * Returns a sequence of WorldStates from the initial WorldState
     * to the solution.
     * @return a sequence of WorldStates from the initial WorldState
     */
    public Iterable<WorldState> solution() {
        return solution;
    }
}
