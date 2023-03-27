package advent.of.code.day12;

import java.util.ArrayList;
import java.util.LinkedList;
import java.lang.Math;


class Contour {
    private int startRow;
    private int startCol;
    private int endRow;
    private int endCol;
    private int[][] map;

    private int[] deltaRow = new int[] {-1, 0, 1, 0};
    private int[] deltaCol = new int[] {0, -1, 0, 1};

    public Contour(int[][] map, int startRow, int startCol, int endRow, int endCol) {
        this.map = map;
        this.startRow = startRow;
        this.startCol = startCol;
        this.endRow = endRow;
        this.endCol = endCol;
    }

    /*
     * Breadth-First search where we start backwards (from [routeEndRow, routeEndCol]) and try to get to [routeStartRow, routeStartCol] as fast as possible.
     * Requires a visited matrix as well as a FIFO queue to store neighbours.
     * Returns the shortest distance  
     */
    public int getShortedDistanceBackwards(FunctionalInterface checker) {
        var visited = new boolean[map.length][map[0].length];
        var queue = new LinkedList<Coordinate>();
        queue.offerLast(new Coordinate(endRow, endCol));
        int dist = 0;
        while (queue.size() > 0) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Coordinate coord = queue.pollFirst();
                if (checker.evaluateGoal(coord.row(), coord.col())) {
                    return dist;
                }
                if (visited[coord.row()][coord.col()]) {
                    continue;
                }
                visited[coord.row()][coord.col()] = true;

                int newRow, newCol;
                for (int j = 0; j < deltaRow.length; j++) {
                    newRow = coord.row() + deltaRow[j];
                    newCol = coord.col() + deltaCol[j];
                    if (checkValidMove(coord.row(), coord.col(), newRow, newCol)) {
                        queue.offerLast(new Coordinate(newRow, newCol));
                    }
                }
            }
            dist += 1;
        }
        return dist;
    }

    private boolean checkValidMove(int oldRow, int oldCol, int newRow, int newCol) {
        return newRow >= 0 && newRow < map.length && newCol >= 0 && newCol < map[newRow].length && map[oldRow][oldCol] - 1 <= map[newRow][newCol];
    }

    public int getStartRow() {
        return startRow;
    }
    public int getStartCol() {
        return startCol;
    }
    public int getEndRow() {
        return endRow;
    }
    public int getEndCol() {
        return endCol;
    }
    public int[][] getMap() {
        return map;
    }
}
