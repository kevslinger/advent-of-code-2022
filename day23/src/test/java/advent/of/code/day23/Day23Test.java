package advent.of.code.day23;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static advent.of.code.parser_utils.ParserUtils.readIntoStringListUntilEOF;

class Day23Test {
    private Elf[][] elfGrid;
    @BeforeEach
    void setup() {
        ArrayList<String> gridLines = readIntoStringListUntilEOF(Day23Test.class.getResourceAsStream("/day23_test.txt"));
        elfGrid = Day23.convertToElfGrid(gridLines);
    }

    @Test
    void testPart1() {
        assertEquals(110, Day23.computePart1(Day23.runSimulation(elfGrid)));
    }

    // @Test
    // void testPart2() {
    //     assertEquals(20, Day23.computePart2(elfGrid));
    // }
}
