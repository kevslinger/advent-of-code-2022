package advent.of.code.day14;

import java.nio.file.FileSystems;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

import advent.of.code.parser_utils.ParserUtils;
import static advent.of.code.parser_utils.ParserUtils.readIntoStringListUntilEOF;

class Day14Test {
    ArrayList<String> pathStrings;
    ArrayList<MovementPath> paths;
    int[] dimensions;

    @BeforeEach
    void setup() {
        pathStrings = readIntoStringListUntilEOF(FileSystems.getDefault().getPath(ParserUtils.TEST_RESOURCES, "day14_test.txt"));
        paths = Day14.parsePaths(pathStrings);
        dimensions = Day14.getMatrixDims(paths);
    }

    @Test
    void testPart1() {
        int[][] maze = Day14.createMaze(dimensions[1] + 1, Math.max(dimensions[0], 500) + 1, paths, new Coordinate(500, 0));
        assertEquals(24, Day14.countSand(maze));
    }

    @Test
    void testPart2() {
        int[] dimensions = Day14.getMatrixDims(paths);
        // dimensions[1] is the max Y of the rocks
        // + 1 for 0-indexed array
        // + 2 for the 2 new rows until the bottom
        // Math.max(dimensions[0], 500) because the sand drops at 500
        // + dimensions[1] because the sand could drop down and right for a max of Y steps
        int y = dimensions[1] + 3;
        int x = Math.max(dimensions[0], 500) + dimensions[1];
        paths.add(new MovementPath("0," + (y - 1) + " -> " + (x - 1) + "," + (y - 1)));
        int[][] maze = Day14.createMaze(y, x, paths, new Coordinate(500, 0));
        assertEquals(93, Day14.countSand(maze));
    }
}
