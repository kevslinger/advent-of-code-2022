package advent.of.code.day15;

public record Coordinate(int x, int y) implements Comparable<Coordinate> {
    @Override
    public int compareTo(Coordinate b) {
        if (x == b.x()) {
            if (y() < b.y()) {
                return -1;
            } else if (y == b.y()) {
                return 0;
            } else {
                return 1;
            }
        } else if (x < b.x()) {
            return -1;
        } else {
            return 1;
        }
    }
}
