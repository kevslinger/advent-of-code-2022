package advent.of.code.day8;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static advent.of.code.parser_utils.ParserUtils.readIntoStringList;

public class Day8Test {
    ArrayList<String> trees;
    int[][] scenic;

    @BeforeEach
    public void setup() {
        trees = readIntoStringList(Day8Test.class.getResourceAsStream("/day8_test.txt"));
        scenic = Day8.initialiseScenic(trees);
    }

    @Test
    public void testPart1() {
        assertEquals(21, Day8.countVisibleTrees(scenic));
    }

    @Test 
    public void testPart2() {
        assertEquals(8, Day8.countTotalScenicScore(scenic));
    }
}
