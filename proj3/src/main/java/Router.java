import org.checkerframework.checker.units.qual.A;

import javax.print.attribute.standard.MediaSize;
import javax.sound.midi.MidiSystem;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class provides a shortestPath method for finding routes between two points
 * on the map. Start by using Dijkstra's, and if your code isn't fast enough for your
 * satisfaction (or the autograder), upgrade your implementation by switching it to A*.
 * Your code will probably not be fast enough to pass the autograder unless you use A*.
 * The difference between A* and Dijkstra's is only a couple of lines of code, and boils
 * down to the priority you use to order your vertices.
 */
public class Router {
    private static final long SENTINEL = -1;
    public static class AStarNode implements Comparable<AStarNode> {
        private long id;
        private double estimatedDistanceTo;
        public AStarNode(long id, double estimatedDistanceTo) {
            this.id = id;
            this.estimatedDistanceTo = estimatedDistanceTo;
        }

        public long getId() {
            return id;
        }

        @Override
        public int compareTo(AStarNode o) {
            return Double.compare(this.estimatedDistanceTo, o.estimatedDistanceTo);
        }
    }
    /**
     * Return a List of longs representing the shortest path from the node
     * closest to a start location and the node closest to the destination
     * location.
     * @param g The graph to use.
     * @param stlon The longitude of the start location.
     * @param stlat The latitude of the start location.
     * @param destlon The longitude of the destination location.
     * @param destlat The latitude of the destination location.
     * @return A list of node id's in the order visited on the shortest path.
     */
    public static List<Long> shortestPath(GraphDB g, double stlon, double stlat,
                                          double destlon, double destlat) {
        long start = g.closest(stlon, stlat);
        long destination = g.closest(destlon, destlat);
        Set<Long> marked = new HashSet<>();
        Map<Long, Double> bestDistFromStart = new HashMap<>();
        Map<Long, Long> edgeTo = new HashMap<>();
        PriorityQueue<AStarNode> fringe = new PriorityQueue<>();
        fringe.add(new AStarNode(start, g.distance(start, destination)));
        bestDistFromStart.put(start, 0.);
        edgeTo.put(start, SENTINEL);
        while (!fringe.isEmpty()) {
            long v = fringe.remove().getId();
            marked.add(v);
            if (v == destination) {
                List<Long> shortestPath = new ArrayList<>();
                for (long vertex = v; vertex != -1; vertex = edgeTo.get(vertex)) {
                    shortestPath.add(vertex);
                }
                Collections.reverse(shortestPath);
                return shortestPath;
            }
            for (long neighbor : g.adjacent(v)) {
                if (marked.contains(neighbor)) {
                    continue;
                }
                if (!bestDistFromStart.containsKey(neighbor)) {
                    bestDistFromStart.put(neighbor, Double.POSITIVE_INFINITY);
                }
                double currentDistance = bestDistFromStart.get(v) + g.distance(v, neighbor);
                if (currentDistance < bestDistFromStart.get(neighbor)) {
                    bestDistFromStart.put(neighbor, currentDistance);
                    edgeTo.put(neighbor, v);
                    fringe.add(new AStarNode(neighbor, currentDistance + g.distance(neighbor, destination)));
                }
            }
        }
        throw new RuntimeException("Unexpected no result!");
    }

    /**
     * Create the list of directions corresponding to a route on the graph.
     * @param g The graph to use.
     * @param route The route to translate into directions. Each element
     *              corresponds to a node from the graph in the route.
     * @return A list of NavigatiionDirection objects corresponding to the input
     * route.
     */
    public static List<NavigationDirection> routeDirections(GraphDB g, List<Long> route) {
        List<NavigationDirection> navigations = new ArrayList<>();
        return  navigations;
    }

    private static int direction(GraphDB g, double currentBearing, double previousBearing) {
        double deltaBearing = currentBearing - previousBearing;
         if (deltaBearing > 180) {
            deltaBearing -= 360;
        } else if (deltaBearing < -180) {
            deltaBearing += 360;
        }

        if (deltaBearing <= 15 && deltaBearing >= -15) {
            return NavigationDirection.STRAIGHT;
        }
        if (deltaBearing > 15 && deltaBearing <= 30) {
            return NavigationDirection.SLIGHT_RIGHT;
        }
        if (deltaBearing < -15 && deltaBearing >= -30) {
            return NavigationDirection.SLIGHT_LEFT;
        }
        if (deltaBearing > 30 && deltaBearing <= 100) {
            return NavigationDirection.RIGHT;
        }
        if (deltaBearing < -30 && deltaBearing >= -100) {
            return NavigationDirection.LEFT;
        }
        if (deltaBearing > 100) {
            return NavigationDirection.SHARP_RIGHT;
        }
        if (deltaBearing < -100) {
            return NavigationDirection.SHARP_LEFT;
        }
        return -1;
    }

    private static class NavigationDirectionHelper {
        int nextNavigationDirection;
        String way;
        double distance;
        double tmpDistance;
        public NavigationDirectionHelper(int d, String w, double dist, double t) {
            nextNavigationDirection = d;
            way = w;
            distance = dist;
            tmpDistance = t;
        }
    }


    /**
     * Class to represent a navigation direction, which consists of 3 attributes:
     * a direction to go, a way, and the distance to travel for.
     */
    public static class NavigationDirection {

        /** Integer constants representing directions. */
        public static final int START = 0;
        public static final int STRAIGHT = 1;
        public static final int SLIGHT_LEFT = 2;
        public static final int SLIGHT_RIGHT = 3;
        public static final int RIGHT = 4;
        public static final int LEFT = 5;
        public static final int SHARP_LEFT = 6;
        public static final int SHARP_RIGHT = 7;

        /** Number of directions supported. */
        public static final int NUM_DIRECTIONS = 8;

        /** A mapping of integer values to directions.*/
        public static final String[] DIRECTIONS = new String[NUM_DIRECTIONS];

        /** Default name for an unknown way.
         * TODO:*/
        public static final String UNKNOWN_ROAD = "";
        
        /** Static initializer. */
        static {
            DIRECTIONS[START] = "Start";
            DIRECTIONS[STRAIGHT] = "Go straight";
            DIRECTIONS[SLIGHT_LEFT] = "Slight left";
            DIRECTIONS[SLIGHT_RIGHT] = "Slight right";
            DIRECTIONS[LEFT] = "Turn left";
            DIRECTIONS[RIGHT] = "Turn right";
            DIRECTIONS[SHARP_LEFT] = "Sharp left";
            DIRECTIONS[SHARP_RIGHT] = "Sharp right";
        }

        /** The direction a given NavigationDirection represents.*/
        int direction;
        /** The name of the way I represent. */
        String way;
        /** The distance along this way I represent. */
        double distance;

        /**
         * Create a default, anonymous NavigationDirection.
         */
        public NavigationDirection() {
            this.direction = STRAIGHT;
            this.way = UNKNOWN_ROAD;
            this.distance = 0.0;
        }

        public NavigationDirection(int direction, String way, double distance) {
            this.direction = direction;
            if (way.isEmpty()) {
                this.way = UNKNOWN_ROAD;
            } else {
                this.way = way;
            }
            this.distance = distance;
        }

        public String toString() {
            return String.format("%s on %s and continue for %.3f miles.",
                    DIRECTIONS[direction], way, distance);
        }

        /**
         * Takes the string representation of a navigation direction and converts it into
         * a Navigation Direction object.
         * @param dirAsString The string representation of the NavigationDirection.
         * @return A NavigationDirection object representing the input string.
         */
        public static NavigationDirection fromString(String dirAsString) {
            String regex = "([a-zA-Z\\s]+) on ([\\w\\s]*) and continue for ([0-9\\.]+) miles\\.";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(dirAsString);
            NavigationDirection nd = new NavigationDirection();
            if (m.matches()) {
                String direction = m.group(1);
                if (direction.equals("Start")) {
                    nd.direction = NavigationDirection.START;
                } else if (direction.equals("Go straight")) {
                    nd.direction = NavigationDirection.STRAIGHT;
                } else if (direction.equals("Slight left")) {
                    nd.direction = NavigationDirection.SLIGHT_LEFT;
                } else if (direction.equals("Slight right")) {
                    nd.direction = NavigationDirection.SLIGHT_RIGHT;
                } else if (direction.equals("Turn right")) {
                    nd.direction = NavigationDirection.RIGHT;
                } else if (direction.equals("Turn left")) {
                    nd.direction = NavigationDirection.LEFT;
                } else if (direction.equals("Sharp left")) {
                    nd.direction = NavigationDirection.SHARP_LEFT;
                } else if (direction.equals("Sharp right")) {
                    nd.direction = NavigationDirection.SHARP_RIGHT;
                } else {
                    return null;
                }

                nd.way = m.group(2);
                try {
                    nd.distance = Double.parseDouble(m.group(3));
                } catch (NumberFormatException e) {
                    return null;
                }
                return nd;
            } else {
                // not a valid nd
                return null;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof NavigationDirection) {
                return direction == ((NavigationDirection) o).direction
                    && way.equals(((NavigationDirection) o).way)
                    && distance == ((NavigationDirection) o).distance;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(direction, way, distance);
        }
    }
}
