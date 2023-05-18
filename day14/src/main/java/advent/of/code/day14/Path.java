package advent.of.code.day14;

import java.util.ArrayList;

public class Path {
    private ArrayList<Coordinate> coordinates;

    public Path(String pathString) {
        coordinates = new ArrayList<Coordinate>();
        // Format of a path string: X1,Y1 -> X2,Y2 -> ...
        String[] toks = pathString.split(" -> ");
        for (String tok: toks) {
            String[] t = tok.split(",");
            coordinates.add(new Coordinate(Integer.parseInt(t[0]), Integer.parseInt(t[1])));
        }
    }

    public ArrayList<Coordinate> getCoordinates() {
        return coordinates;
    }
}
