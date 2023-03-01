package advent.of.code.day9;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static advent.of.code.parser_utils.ParserUtils.readIntoStringList;

public class Day9Test {
    @Test
    public void testPart1() {
        ArrayList<String> motions = readIntoStringList(Day9Test.class.getResourceAsStream("/day9_test.txt"));
        assertEquals(13, Day9.getUniquePositions(motions, 2).size());
    }

    @Test 
    public void testPart2() {
        ArrayList<String> motions = readIntoStringList(Day9Test.class.getResourceAsStream("/day9_test.txt"));
        assertEquals(1, Day9.getUniquePositions(motions, 10).size());
    }

    @Test
    public void testPart2big() {
        ArrayList<String> motions = motions = readIntoStringList(Day9Test.class.getResourceAsStream("/day9_test_big.txt"));
        assertEquals(36, Day9.getUniquePositions(motions, 10).size());
    }
}
