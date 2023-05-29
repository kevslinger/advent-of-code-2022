package advent.of.code.day12;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static advent.of.code.parser_utils.ParserUtils.readIntoStringListUntilEOF;

class Day12Test {
    ArrayList<String> graph;
    Contour contour;

    @BeforeEach
    void setup() {
        graph = readIntoStringListUntilEOF(Day12Test.class.getResourceAsStream("/day12_test.txt"));
        contour = Day12.readGraph(graph);
    }

    @Test
    void testPart1() {
        FunctionalInterface part1Function = (int row, int col) -> { return row == contour.getStartRow() && col == contour.getStartCol(); };
        assertEquals(31, contour.getShortedDistanceBackwards(part1Function));
    }

    @Test 
    void testPart2() {
        FunctionalInterface part2Function = (int row, int col) -> { return contour.getMap()[row][col] == 1; };
        assertEquals(29, contour.getShortedDistanceBackwards(part2Function));
    }
}
