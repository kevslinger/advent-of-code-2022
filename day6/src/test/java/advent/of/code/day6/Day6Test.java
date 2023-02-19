package advent.of.code.day6;

import java.io.InputStream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static advent.of.code.parser_utils.ParserUtils.readString;

public class Day6Test {
    String signals;
    InputStream stream;

    @BeforeEach
    public void setup() {
        signals = readString(Day6Test.class.getResourceAsStream("/day6_test.txt"));
    }

    @Test
    public void testPart1() {
        assertEquals(7, Day6.getCharsUntilStart(signals, 4));
    }

    @Test 
    public void testPart2() {
        assertEquals(19, Day6.getCharsUntilStart(signals, 14));
    }
}
