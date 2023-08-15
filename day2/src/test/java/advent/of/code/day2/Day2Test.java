package advent.of.code.day2;

import java.nio.file.FileSystems;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

import advent.of.code.parser_utils.ParserUtils;
import static advent.of.code.parser_utils.ParserUtils.readIntoStringListUntilEOF;

class Day2Test {
    private List<String> game;
    
    @BeforeEach
    void setup() {
        game = readIntoStringListUntilEOF(FileSystems.getDefault().getPath(ParserUtils.TEST_RESOURCES, "day2_test.txt"));
    }

    @Test
    void testPart1() {
        assertEquals(15, Day2.calculateGameScore(game));
    }

    @Test
    void testPart2() {
        assertEquals(12, Day2.calculateGameScore(Day2.pickMoves(game)));
    }
}
