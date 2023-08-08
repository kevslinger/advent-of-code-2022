package advent.of.code.day8;

import java.nio.file.FileSystems;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

import advent.of.code.parser_utils.ParserUtils;
import static advent.of.code.parser_utils.ParserUtils.readIntoStringListUntilEOF;

class Day8Test {
    ArrayList<String> trees;
    int[][] scenic;

    @BeforeEach
    void setup() {
        trees = readIntoStringListUntilEOF(FileSystems.getDefault().getPath(ParserUtils.TEST_RESOURCES, "day8_test.txt"));
        scenic = Day8.initialiseScenic(trees);
    }

    @Test
    void testPart1() {
        assertEquals(21, Day8.countVisibleTrees(scenic));
    }

    @Test 
    void testPart2() {
        assertEquals(8, Day8.countTotalScenicScore(scenic));
    }
}
