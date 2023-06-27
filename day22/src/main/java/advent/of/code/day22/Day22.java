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

    static int getResult(Position finalPosition) {
        return 1000 * (finalPosition.getRow() + 1) + 4 * (finalPosition.getColumn() + 1) + finalPosition.getDirection();
    }
}
