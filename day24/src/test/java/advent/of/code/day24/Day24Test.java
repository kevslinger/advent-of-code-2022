package advent.of.code.day24;

import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

import advent.of.code.parser_utils.ParserUtils;
import static advent.of.code.parser_utils.ParserUtils.readIntoStringListUntilEOF;

class Day24Test {
    HashMap<Integer, int[][]> blizzardMap;
    int[][] initialMap;
    Coordinate startCoordinate;
    Coordinate goalCoordinate;


    @BeforeEach
    void setup() {
        ArrayList<String> gridStrings = readIntoStringListUntilEOF(FileSystems.getDefault().getPath(ParserUtils.TEST_RESOURCES, "day24_test.txt"));
        blizzardMap = new HashMap<Integer, int[][]>();
        blizzardMap.put(0, Day24.createInitialGrid(gridStrings));
        int[][] initialMap = blizzardMap.get(0);
        int initialPosition = Day24.getOpenPosition(initialMap[0]);
        startCoordinate = new Coordinate(0, initialPosition);
        int goalPosition = Day24.getOpenPosition(initialMap[initialMap.length - 1]);
        goalCoordinate = new Coordinate(initialMap.length - 1, goalPosition);
    }

    @Test
    void testPart1() {
        assertEquals(18, Day24.runSimulation(blizzardMap, 0, startCoordinate, goalCoordinate));
    }

    @Test
    void testPart2() {
        int fastestTime = Day24.runSimulation(blizzardMap, 0, startCoordinate, goalCoordinate);
        fastestTime = Day24.runSimulation(blizzardMap, fastestTime, goalCoordinate, startCoordinate);
        assertEquals(54, Day24.runSimulation(blizzardMap, fastestTime, startCoordinate, goalCoordinate));
    }
}
