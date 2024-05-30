import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Graph for storing all of the intersection (vertex) and road (edge) information.
 * Uses your GraphBuildingHandler to convert the XML files into a graph. Your
 * code must include the vertices, adjacent, distance, closest, lat, and lon * methods. You'll also need to include instance variables and methods for
 * modifying the graph (e.g. addNode and addEdge).
 *
 * @author Alan Yao, Josh Hug
 */
public class GraphDB {
    /**
     * This is the underlying representation of the graph object.
     */
    private HashMap<Long, HashSet<Long>> graph;

    /**
     * This maps the Long id to Node to gain more information from the node id.
     */
    private HashMap<Long, Node> id2Node;

    /**
     * This maps the long id to Way to gain more information about the way
     */
    private HashMap<Long, Way> id2Way;

    /**
     * This is a map that contains every possible name in the whole berkeley map
     * and map the name to its ids
     * Note that a location with name may map to multi ids
     */
    private HashMap<String, HashSet<Long>> name2Id;
    /**
     * The Way abstract class to represent the whole information of the way
     * @id the id of the way/road
     * @nodes a list of linear nodes along the way
     * @maxSpeed the speed limit of the way (Note that this information won't be uesd in this project)
     * @name the name of the road/way
     * @validFlag shows if the way is valid
     */
    public class Way {
        private long id;
        private List<Long> nodes;
        private String maxSpeed;
        private String name;
        private  boolean validFlag = false;
        public Way(long id, List<Long> nodes) {
            this.id = id;
            this.nodes = nodes;
            name = "unknown road";
            maxSpeed = "";
        }

        public long getId() {
            return id;
        }

        public boolean containsNode(long nodeId) {
            return nodes.contains(nodeId);
        }

        public void addNode(long nodeId) {
            nodes.add(nodeId);
        }

        public List<Long> getNodes() {
            return nodes;
        }

        public void setName(String name) {
            this.name = name;
        }
        public void setMaxSpeed(String speedLimit) {
            maxSpeed = speedLimit;
        }

        public String getName() {
            return name;
        }

        public String getMaxSpeed() {
            return maxSpeed;
        }

        public void setValidFlag(boolean validFlag) {
            this.validFlag = validFlag;
        }

        public boolean isValid() {
            return validFlag;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || other.getClass() != this.getClass()) {
                return false;
            }
            Way node = (Way) other;
            return node.id == id;
        }

        @Override
        public int hashCode() {
            return (int) id;
        }
    }

    /**
     * This is the representation of the node,
     * it contains information about the
     * @id id of the node
     * @longitude longitude of the node
     * @latittude latitude of the node
     */
    public class Node {
        private long id;
        private double lon;
        private double lat;
        private String name;

        private Set<Long> waysLocated;

        public Node(long id, double lon, double lat) {
            this.id = id;
            this.lon = lon;
            this.lat = lat;
            name = "";
            waysLocated = new HashSet<>();
        }

        public long getId() {
            return id;
        }

        public double getLon() {
            return lon;
        }

        public double getLat() {
            return lat;
        }

        public void setName(String n) {
            name = n;
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || other.getClass() != this.getClass()) {
                return false;
            }
            Node node = (Node) other;
            return node.id == id;
        }

        public void addWay(long wayId) {
            waysLocated.add(wayId);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Node: ");
            sb.append(name.toString());
            sb.append(", id: ");
            sb.append(id);
            return sb.toString();
        }

        @Override
        public int hashCode() {
           return (int) id;
        }
    }

    /** Your instance variables for storing the graph. You should consider
     * creating helper classes, e.g. Node, Edge, etc. */

    /**
     * Example constructor shows how to create and start an XML parser.
     * You do not need to modify this constructor, but you're welcome to do so.
     * @param dbPath Path to the XML file to be parsed.
     */
    public GraphDB(String dbPath) {
        try {
            graph = new HashMap<>();
            id2Way = new HashMap<>();
            id2Node = new HashMap<>();
            name2Id = new HashMap<>();

            File inputFile = new File(dbPath);
            FileInputStream inputStream = new FileInputStream(inputFile);
            // GZIPInputStream stream = new GZIPInputStream(inputStream);

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            GraphBuildingHandler gbh = new GraphBuildingHandler(this);
            saxParser.parse(inputStream, gbh);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        clean();
        setName2Id();
    }

    /**
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

    public Set<Long> getIdsByName(String name) {
        return name2Id.get(name);
    }

    /**
     *  Remove nodes with no connections from the graph.
     *  While this does not guarantee that any two nodes in the remaining graph are connected,
     *  we can reasonably assume this since typically roads are connected.
     */
    private void clean() {
        Set<Long> independentNode = new HashSet<>();
        for (long id : vertices()) {
            if (graph.get(id).isEmpty()) {
                independentNode.add(id);
            }
        }
        for (long id : independentNode) {
            deleteVertex(id);
        }
    }

    /**
     * Returns an iterable of all vertex IDs in the graph.
     * @return An iterable of id's of all vertices in the graph.
     */
    Iterable<Long> vertices() {
        return graph.keySet();
    }

    /**
     * Returns ids of all vertices adjacent to v.
     * @param v The id of the vertex we are looking adjacent to.
     * @return An iterable of the ids of the neighbors of v.
     */
    Iterable<Long> adjacent(long v) {
        return graph.get(v);
    }

    /**
     * Returns the great-circle distance between vertices v and w in miles.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The great-circle distance between the two locations from the graph.
     */
    double distance(long v, long w) {
        return distance(lon(v), lat(v), lon(w), lat(w));
    }

    static double distance(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double dphi = Math.toRadians(latW - latV);
        double dlambda = Math.toRadians(lonW - lonV);

        double a = Math.sin(dphi / 2.0) * Math.sin(dphi / 2.0);
        a += Math.cos(phi1) * Math.cos(phi2) * Math.sin(dlambda / 2.0) * Math.sin(dlambda / 2.0);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return 3963 * c;
    }

    /**
     * Returns the initial bearing (angle) between vertices v and w in degrees.
     * The initial bearing is the angle that, if followed in a straight line
     * along a great-circle arc from the starting point, would take you to the
     * end point.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The initial bearing between the vertices.
     */
    double bearing(long v, long w) {
        return bearing(lon(v), lat(v), lon(w), lat(w));
    }

    static double bearing(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double lambda1 = Math.toRadians(lonV);
        double lambda2 = Math.toRadians(lonW);

        double y = Math.sin(lambda2 - lambda1) * Math.cos(phi2);
        double x = Math.cos(phi1) * Math.sin(phi2);
        x -= Math.sin(phi1) * Math.cos(phi2) * Math.cos(lambda2 - lambda1);
        return Math.toDegrees(Math.atan2(y, x));
    }

    private void setName2Id() {
        for (long nodeId : vertices()) {
            String name = getNodeById(nodeId).name;
            if (!name.isEmpty()) {
                if (!name2Id.containsKey(name)) {
                    name2Id.put(name, new HashSet<>());
                }
                name2Id.get(name).add(nodeId);
            }
        }
        for (long wayId : id2Way.keySet()) {
            String name = getWayById(wayId).name;
            if (!name.isEmpty()) {
                if (!name2Id.containsKey(name)) {
                    name2Id.put(name, new HashSet<>());
                }
                name2Id.get(name).add(wayId);
            }
        }
    }

    private Node getNodeById(long id) {
        return id2Node.get(id);
    }


    private Way getWayById(long id) {
        return id2Way.get(id);
    }

    /**
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    long closest(double lon, double lat) {
        double closestDistance = Double.POSITIVE_INFINITY;
        long closestId = -1;
        for (long id : vertices()) {
            Node vertex = getNodeById(id);
            double currentDistance = distance(lon, lat, vertex.getLon(), vertex.getLat());
            if (currentDistance < closestDistance) {
                closestDistance = currentDistance;
                closestId = id;
            }
        }
        return closestId;
    }

    /**
     * Gets the longitude of a vertex.
     * @param v The id of the vertex.
     * @return The longitude of the vertex.
     */
    double lon(long v) {
        return getNodeById(v).lon;
    }

    /**
     * Gets the latitude of a vertex.
     * @param v The id of the vertex.
     * @return The latitude of the vertex.
     */
    double lat(long v) {
        return getNodeById(v).lat;
    }

    public void addVertex(long id, double lon, double lat) {
        if (graph.containsKey(id) || id2Node.containsKey(id)) {
            throw new IllegalArgumentException("Vertex " + id + " has been added!");
        }
        graph.put(id, new HashSet<>());
        id2Node.put(id, new Node(id, lon, lat));
    }

    public void addVertex(Node node) {
        addVertex(node.id, node.lon, node.lat);
    }

    public void addEdge(long v, long w) {
        if (!graph.containsKey(v) || !graph.containsKey(w)) {
            throw new IllegalArgumentException("Vertex " + v + " or Vertex " + w + " haven't been added to the Vertex Set!");
        }
        graph.get(v).add(w);
        graph.get(w).add(v);
    }

    public void deleteVertex(long id) {
        if (!graph.containsKey(id) || !graph.get(id).isEmpty()) {
            throw new IllegalArgumentException("The graph doesn't contain such a Vertex!");
        }
        graph.remove(id);
        id2Node.remove(id);
    }

    public void addWay(Way way) {
        if (!way.validFlag) {
            throw new IllegalArgumentException("This way is not valid!");
        }
        long previousNode = -1;
        for (long currentNode : way.getNodes()) {
            if (previousNode != -1) {
                addEdge(previousNode, currentNode);
            }
            getNodeById(currentNode).addWay(way.getId());
            previousNode = currentNode;
        }
        id2Way.put(way.getId(), way);
    }
}
