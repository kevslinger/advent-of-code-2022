package advent.of.code.day6;

import java.nio.file.FileSystems;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

import advent.of.code.parser_utils.ParserUtils;
import static advent.of.code.parser_utils.ParserUtils.readString;

public class Day6Test {
    String signals;

    @BeforeEach
    public void setup() {
        signals = readString(FileSystems.getDefault().getPath(ParserUtils.TEST_RESOURCES, "day6_test.txt"));
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
