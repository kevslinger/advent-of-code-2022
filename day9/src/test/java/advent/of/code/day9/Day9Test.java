package advent.of.code.day9;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static advent.of.code.parser_utils.ParserUtils.readIntoStringListUntilEOF;

class Day9Test {
    @Test
    void testPart1() {
        ArrayList<String> motions = readIntoStringListUntilEOF(Day9Test.class.getResourceAsStream("/day9_test.txt"));
        assertEquals(13, Day9.getUniquePositions(motions, 2).size());
    }

    @Test 
    void testPart2() {
        ArrayList<String> motions = readIntoStringListUntilEOF(Day9Test.class.getResourceAsStream("/day9_test.txt"));
        assertEquals(1, Day9.getUniquePositions(motions, 10).size());
    }

    @Test
    void testPart2big() {
        ArrayList<String> motions = motions = readIntoStringListUntilEOF(Day9Test.class.getResourceAsStream("/day9_test_big.txt"));
        assertEquals(36, Day9.getUniquePositions(motions, 10).size());
    }
}
