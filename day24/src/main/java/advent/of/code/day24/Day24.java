package advent.of.code.day24;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.HashSet;

import static advent.of.code.parser_utils.ParserUtils.readIntoStringListUntilEOF;

class Day24 {
    private static final int WALL  = -1;
    private static final int EMPTY =  0;
    private static final int NORTH =  1;
    private static final int SOUTH =  2;
    private static final int WEST  =  4;
    private static final int EAST  =  8;
    private static final int[] DIRECTIONS = new int[] { Day24.EAST, Day24.WEST, Day24.SOUTH, Day24.NORTH }; // Must go from largest to smallest value

    public static void main(String[] args) {
        InputStream inputStream = Day24.class.getResourceAsStream("/day24.txt");
        ArrayList<String> gridStrings = readIntoStringListUntilEOF(inputStream);
        var blizzardMap = new HashMap<Integer, int[][]>();
        blizzardMap.put(0, createInitialGrid(gridStrings));

        // Find our start and end points
        int[][] initialMap = blizzardMap.get(0);
        int initialPosition = getOpenPosition(initialMap[0]);
        var startCoordinate = new Coordinate(0, initialPosition);
        int goalPosition = getOpenPosition(initialMap[initialMap.length - 1]);
        var goalCoordinate = new Coordinate(initialMap.length - 1, goalPosition);
        // Run simulation for going from the start to the end (PART 1)
        int fastestTime = runSimulation(blizzardMap, 0, startCoordinate, goalCoordinate);
        System.out.println("The answer to part 1 is " + fastestTime);
        // PART 2
        fastestTime = runSimulation(blizzardMap, fastestTime, goalCoordinate, startCoordinate);
        fastestTime = runSimulation(blizzardMap, fastestTime, startCoordinate, goalCoordinate);
        System.out.println("The answer to part 2 is " + fastestTime);
    }

    static int[][] createInitialGrid(ArrayList<String> gridStrings) {
        int[][] grid = new int[gridStrings.size()][gridStrings.get(0).length()];
        for (int i = 0; i < grid.length; i++) {
            String curLine = gridStrings.get(i);
            for (int j = 0; j < curLine.length(); j++) {
                switch (curLine.charAt(j)) {
                    case '#' -> {
                        grid[i][j] = Day24.WALL; // Wall
                    }
                    case '.' -> {
                        grid[i][j] = Day24.EMPTY; // Empty
                    }
                    case '^' -> {
                        grid[i][j] = Day24.NORTH; // North blizzard
                    }
                    case 'v' -> {
                        grid[i][j] = Day24.SOUTH;
                    }
                    case '<' -> {
                        grid[i][j] = Day24.WEST;
                    }
                    case '>' -> {
                        grid[i][j] = Day24.EAST;
                    }
                }
            }
        }
        return grid;
    }

    static int getOpenPosition(int[] row) throws IllegalArgumentException{
        for (int i = 0; i < row.length; i++) {
            if (row[i] == 0) {
                return i;
            }
        }
        throw new IllegalArgumentException("Must have an empty space on the top row of the grid.");
    }

    private static int[][] stepBlizzard(int[][] blizzardGrid) {
        int[][] nextGrid = new int[blizzardGrid.length][blizzardGrid[0].length];
        // Need to replace the walls of the grid.
        for (int i = 0; i < blizzardGrid.length; i++) {
            nextGrid[i][0] = blizzardGrid[i][0];
            nextGrid[i][nextGrid[0].length - 1] = blizzardGrid[i][blizzardGrid[0].length - 1];
        }
        for (int j = 0; j < blizzardGrid[0].length; j++) {
            nextGrid[0][j] = blizzardGrid[0][j];
            nextGrid[nextGrid.length - 1][j] = blizzardGrid[blizzardGrid.length - 1][j];
        }
        for (int i = 0; i < blizzardGrid.length; i++) {
            for (int j = 0; j < blizzardGrid[0].length; j++) {
                int curValue = blizzardGrid[i][j];
                
                for (int direction : Day24.DIRECTIONS) {
                    if (curValue >= direction) {
                        Coordinate newLocation = getNewCoordinate(new Coordinate(i, j), direction, nextGrid);

                        nextGrid[newLocation.x()][newLocation.y()] += direction;
                        curValue -= direction;
                    }
                }
            }
        }
        return nextGrid;
    }

    private static Coordinate getNewCoordinate(Coordinate oldCoord, int direction, int[][] grid) throws IllegalArgumentException{
        int newX, newY;
        switch (direction) {
            case Day24.NORTH -> {
                // Wrap around
                if (oldCoord.x() == 1) {
                    newX = grid.length - 2;
                } else {
                    newX = oldCoord.x() - 1;
                }
                newY = oldCoord.y();
            }
            case Day24.SOUTH -> {
                // Wrap around
                if (oldCoord.x() == grid.length - 2) {
                    newX = 1;
                } else {
                    newX = oldCoord.x() + 1;
                }
                newY = oldCoord.y();
            }
            case Day24.WEST -> {
                // Wrap around
                if (oldCoord.y() == 1) {
                    newY = grid[0].length - 2;
                } else {
                    newY = oldCoord.y() - 1;
                }
                newX = oldCoord.x();
            }
            case Day24.EAST -> {
                // Wrap around
                if (oldCoord.y() == grid[0].length - 2) {
                    newY = 1;
                } else {
                    newY = oldCoord.y() + 1;
                }
                newX = oldCoord.x();
            }
            default -> {
                throw new IllegalArgumentException("Direction must be North, South, West, or East.");
            }
        }
        return new Coordinate(newX, newY);
    }

    // Option 1: Clone original blizzard grid, which allows me to subtract from the grid
    // Option 2: Switch over every possible combination (-1, 0, 1, 2, 3, 4, 5, 6, 7, 8)
    // Option 3: Could subtract and then add it back later
    static int runSimulation(HashMap<Integer, int[][]> blizzardMap, int startingMinutes, Coordinate startCoordinate, Coordinate goalCoordinate) {
        int[][] initialMap = blizzardMap.get(0);
        var stateSet = new HashSet<SimulationState>();
        // Initialise our inital state
        var initialState = new SimulationState(0, startCoordinate);
        var queue = new LinkedList<SimulationState>();
        queue.offer(initialState);

        SimulationState curState;
        Coordinate curLoc;
        int nextMinutes = startingMinutes + 1;
        int[][] nextGrid;
        while (queue.size() > 0) {
            int queueSize = queue.size();
            for (int i = 0; i < queueSize; i++) {
                curState = queue.poll();
                if (stateSet.contains(curState)) {
                    continue;
                }
                stateSet.add(curState);
                curLoc = curState.getLoc();
                if (curLoc.equals(goalCoordinate)) {
                    return nextMinutes - 1;
                }
                // If we haven't seen this timestamp yet, create the blizzard grid for it.
                if (!blizzardMap.containsKey(nextMinutes)) {
                    blizzardMap.put(nextMinutes, stepBlizzard(blizzardMap.get(nextMinutes - 1)));
                }
                
                nextGrid = blizzardMap.get(nextMinutes);

                // Our options are to wait, move north, south, east, west
                if (nextGrid[curLoc.x()][curLoc.y()] == 0) { // Can wait
                    queue.offer(new SimulationState(nextMinutes, curLoc));
                }
                if (curLoc.x() - 1 >= 0 && nextGrid[curLoc.x() - 1][curLoc.y()] == 0) { // Can go north
                    queue.offer(new SimulationState(nextMinutes, new Coordinate(curLoc.x() - 1, curLoc.y())));
                }
                if (curLoc.x() + 1 < nextGrid.length && nextGrid[curLoc.x() + 1][curLoc.y()] == 0) { // Can go south
                    queue.offer(new SimulationState(nextMinutes, new Coordinate(curLoc.x() + 1, curLoc.y())));
                }
                if (curLoc.y() - 1 >= 0 && nextGrid[curLoc.x()][curLoc.y() - 1] == 0) { // Can go west
                    queue.offer(new SimulationState(nextMinutes, new Coordinate(curLoc.x(), curLoc.y() - 1)));
                }
                if (curLoc.y() + 1 < nextGrid[0].length && nextGrid[curLoc.x()][curLoc.y() + 1] == 0) { // Can go east
                    queue.offer(new SimulationState(nextMinutes, new Coordinate(curLoc.x(), curLoc.y() + 1)));
                }
            }
            nextMinutes++;
        }
        // Did not find an exit
        return -1;
    }
}
