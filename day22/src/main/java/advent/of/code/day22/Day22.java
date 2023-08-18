package advent.of.code.day22;

import java.util.List;
import java.util.ArrayList;
import java.io.InputStream;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static advent.of.code.parser_utils.ParserUtils.readIntoStringListUntilBlankLine;
import static advent.of.code.parser_utils.ParserUtils.readIntoStringListButSkipFirstSet;

class Day22 {
    final static int[][] DIRECTIONS = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
    final static int RIGHT = 0;
    final static int DOWN = 1;
    final static int LEFT = 2;
    final static int UP = 3;

    final static int BLANK = 0;
    final static int MOVEABLE = 1;
    final static int WALL = 2;

    public static void main(String[] args) {
        InputStream inputStream = Day22.class.getResourceAsStream("/day22.txt");
        ArrayList<String> mapStrings = readIntoStringListUntilBlankLine(inputStream);
        inputStream = Day22.class.getResourceAsStream("/day22.txt");
        String instructionStrings = readIntoStringListButSkipFirstSet(inputStream).get(0);

        int[][] map = null;
        try {
            map = createMap(mapStrings);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        ArrayList<String> instructionList = createInstructionList(instructionStrings);

        Position finalPosition = runSimulation(map, instructionList);

        System.out.println("The answer to part 1 is: " + getResult(finalPosition));

        // PART 2
        Cube cube = createCube(map, 50);
        finalPosition = runSimulationPart2(cube, instructionList);
        System.out.println("The answer to part 2 is: " + getResult(finalPosition, cube));
    }

    static int[][] createMap(List<String> mapStrings) throws IllegalArgumentException {
        int longestLength = 0;
        for (String mapString : mapStrings) {
            longestLength = Math.max(longestLength, mapString.length());
        }
        int[][] map = new int[mapStrings.size()][longestLength];

        for (int i = 0; i < mapStrings.size(); i++) {
            String mapString = mapStrings.get(i);
            for (int j = 0; j < mapString.length(); j++) {
                switch (mapString.charAt(j)) {
                    case ' ' -> { map[i][j] = Day22.BLANK; } // Not part of map
                    case '#' -> { map[i][j] = Day22.WALL; } // Solid Wall
                    case '.' -> { map[i][j] = Day22.MOVEABLE; } // Moveable space
                    default -> { throw new IllegalArgumentException("Unrecognised value in map string."); }
                }
            }
        }

        return map;
    }

    static Cube createCube(int[][] map, int cubeSize) {
        Cube cube = new Cube();
        int cubeSideIdx = 1;
        int i = 0, j = 0;
        while (i < map.length) {
            while (j < map[0].length) {
                // Find the start of the CubeSide
                if (map[i][j] != Day22.BLANK) {
                    // Fill in the cube Side
                    var newCubeSide = new CubeSide(cubeSideIdx++, cubeSize, i, j);
                    cube.addCubeSide(newCubeSide);
                    for (int row = i; row < i + cubeSize; row++) {
                        for (int col = j; col < j + cubeSize; col++) {
                            newCubeSide.addElement(row, col, map[row][col]);
                        }
                    }
                    j += cubeSize;
                    // If there are no more cube sides, jump down 50 rows and reset
                    if (j == map[0].length || map[i][j] == Day22.BLANK) {
                        i += cubeSize;
                        j = 0;
                    }
                } else {
                    j++;
                }
                if (i >= map.length) {
                    break;
                }
            }
            i++;
        }
        return cube;
    }

    static ArrayList<String> createInstructionList(String instructions) {
        var instructionList = new ArrayList<String>();
        String regex = "([0-9]+)(L|R)";
        Matcher matcher = Pattern.compile(regex).matcher(instructions);
        int lastIdx = 0;
        while (matcher.find()) {
            instructionList.add(matcher.group(1));
            instructionList.add(matcher.group(2));
            lastIdx = matcher.end();
        }
        // There will be one integer remaining
        instructionList.add(instructions.substring(lastIdx));
        return instructionList;
    }

    static Position runSimulation(int[][] map, ArrayList<String> instructionList) {
        // First, we need to find the initial position
        Position curPosition = findNextOpenPosition(map, new Position(0, 0, 0));
        // Then, we iterate through all the instructions.
        for (String instruction : instructionList) {
            switch (instruction) {
                case "L" -> { curPosition.rotate(-1); }
                case "R" -> { curPosition.rotate(1); }
                default -> {
                    Integer numMoves = Integer.parseInt(instruction);
                    for (int i = 0; i < numMoves; i++) {
                        Position nextPosition = findNextOpenPosition(map, curPosition);
                        // Run into a wall
                        if (nextPosition.equals(curPosition)) {
                            break;
                        }
                        curPosition = nextPosition;
                    }
                }
            }
        }
        return curPosition;
    }

    // Helper function to find the next open position given a row, column, and direction
    static Position findNextOpenPosition(int[][] map, Position curPosition) {
        int row = curPosition.getRow();
        int column = curPosition.getColumn();
        int direction = curPosition.getDirection();

        do {
            int newRow = row + Day22.DIRECTIONS[direction][0];
            int newCol = column + Day22.DIRECTIONS[direction][1];
            // If we're out of bounds, wrap around
            if (newRow < 0) {
                newRow = map.length - 1;
            } else if (newRow >= map.length) {
                newRow = 0;
            }
            if (newCol < 0) {
                newCol = map[0].length - 1;
            } else if (newCol >= map[0].length) {
                newCol = 0;
            }
            // If we hit a wall, we're stuck
            if (map[newRow][newCol] == Day22.WALL) {
                return curPosition;
            }
            row = newRow;
            column = newCol; 
        } while (map[row][column] != Day22.MOVEABLE);
        return new Position(row, column, direction);
    }

        // TODO: I should use the cube structure for part 1 as well
    static Position runSimulationPart2(Cube cube, ArrayList<String> instructionList) {
        // Find the initial position
        // TODO: A bit hardcoded
        Position curPosition = new Position(0, 0, 0, 1);
        // Then, iterate through all the instructions.
        for (String instruction : instructionList) {
            switch (instruction) {
                case "L" -> { curPosition.rotate(-1); }
                case "R" -> { curPosition.rotate(1); }
                default -> {
                    // Okay so this one is going to be awkward.
                    Integer numMoves = Integer.parseInt(instruction);
                    for (int i = 0; i < numMoves; i++) {
                        Position nextPosition = findNextOpenPositionCube(cube, curPosition);
                        if (nextPosition.equals(curPosition)) {
                            break;
                        }
                        curPosition = nextPosition;
                    }
                }
            }   
        }
        return curPosition;
    }
    // TODO: Duplicate with the original
    static Position findNextOpenPositionCube(Cube cube, Position curPosition) {
        int row = curPosition.getRow();
        int column = curPosition.getColumn();
        int direction = curPosition.getDirection();
        int cubeSideIdx = curPosition.getCubeSide();
        CubeSide curCube = cube.getCubeSide(cubeSideIdx);
        int[][] map = curCube.getMap();

        int newRow = row + Day22.DIRECTIONS[direction][0];
        int newCol = column + Day22.DIRECTIONS[direction][1];
        // If we're out of bounds, wrap around
        if (newRow < 0 || newRow >= map.length || newCol < 0 || newCol >= map[0].length) {
            // TODO: Figure out which cube side to move to
            CubeSide neighbor = cube.getCubeSide(curCube.getNeighbor(direction));
            curCube = neighbor;
            // If the neighbor "matches" then we don't flip
            // Otherwise we do
            if (neighbor.getNeighbor((direction + 2) % 4) != cubeSideIdx) {
                // Flip
                newRow = column;
                newCol = row;
                // We have to change the direction 
            } else {
                if (newRow < 0) {
                    newRow = map.length - 1;
                } else if (newRow >= map.length) {
                    newRow = 0;
                } else if (newCol < 0) {
                    newCol = map.length - 1;
                } else if (newCol >= map.length) {
                    newCol = 0;
                }
            }
            // TODO: Could put direction in the if statement, I think
            direction = (neighbor.findNeighbor(cubeSideIdx) + 2) % 4;
            cubeSideIdx = curCube.getIdx();
        }
        // If we hit a wall, we're stuck
        if (map[newRow][newCol] == Day22.WALL) {
            return curPosition;
        }
        return new Position(newRow, newCol, direction, cubeSideIdx);
    }

    static int getResult(Position finalPosition) {
        return 1000 * (finalPosition.getRow() + 1) + 4 * (finalPosition.getColumn() + 1) + finalPosition.getDirection();
    }

    static int getResult(Position finalPosition, Cube cube) {
        return 1000 * (finalPosition.getRow() + cube.getCubeSide(finalPosition.getCubeSide()).getRowOffset()) + 4 * (finalPosition.getColumn() + cube.getCubeSide(finalPosition.getCubeSide()).getColOffset()) + finalPosition.getDirection();
    }
}


/*
For part 2, we can cheat

The cube structure is 

     1111 2222
     1111 2222
     1111 2222
     1111 2222
     3333
     3333
     3333
     3333
4444 5555
4444 5555
4444 5555
4444 5555
6666
6666
6666
6666

Should we make a class that represents each one of the sides?
Each side would be a 50x50 array
If we go off the edge, figure out which edge and then send it to that neighbour


class CubeSide will have an ID, a 50x50 int grid, and then neighbours north, south, east, and west, which are IDs
If we give it a row and column offset, does that solve our problem for the final part?

Should we have a Cube class that holds all 6 cubesides?
*/