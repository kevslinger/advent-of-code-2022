package advent.of.code.day2;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static advent.of.code.parser_utils.ParserUtils.readIntoStringListUntilEOF;

class Day2Test {
    private List<String> game;
    
    @BeforeEach
    void setup() {
        game = readIntoStringListUntilEOF(Day2Test.class.getResourceAsStream("/day2_test.txt"));
    }

    @Test
    void testPart1() {
        assertEquals(15, Day2.calculateScore(game));
    }

    @Test
    void testPart2() {
        assertEquals(12, Day2.calculateScore(Day2.pickMoves(game)));
    }
}
