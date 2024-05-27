import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {

    public Rasterer() {
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        Map<String, Object> results = new HashMap<>();
        double ullat = params.get("ullat");
        double lrlat = params.get("lrlat");
        double ullon = params.get("ullon");
        double lrlon = params.get("lrlon");
        double w = params.get("w");
        if (lrlat > ullat || lrlon < ullon) {
            results.put("query_success", false);
            return results;
        }
        int depth = RastererHelper.getDepth(ullon, lrlon, w);
        int ulboxX = RastererHelper.getBoxXX(ullon, depth);
        int ulboxY = RastererHelper.getBoxYY(ullat, depth);
        int lrboxX = RastererHelper.getBoxXX(lrlon, depth);
        int lrBoxY = RastererHelper.getBoxYY(lrlat, depth);
        String[][] render_grid = RastererHelper.createImageMatrix(ulboxX, ulboxY, lrboxX, lrBoxY, depth);
        double raster_ul_lon = RastererHelper.getBoundingULLON(ulboxX, depth);
        double raster_ul_lat = RastererHelper.getBoundingULLAT(ulboxY, depth);
        double raster_lr_lon = RastererHelper.getBoundingLRLON(lrboxX, depth);
        double raster_lr_lat = RastererHelper.getBoundingLRLAT(lrBoxY, depth);
        results.put("render_grid", render_grid);
        results.put("raster_ul_lon", raster_ul_lon);
        results.put("raster_ul_lat", raster_ul_lat);
        results.put("raster_lr_lon", raster_lr_lon);
        results.put("raster_lr_lat", raster_lr_lat);
        results.put("depth", depth);
        results.put("query_success", true);
        return results;
    }
}
