package advent.of.code.day25;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

import advent.of.code.parser_utils.ParserUtils;
import static advent.of.code.parser_utils.ParserUtils.readIntoStringListUntilEOF;

class Day25Test {
    ArrayList<String> snafuStrings;

    @BeforeEach
    void setup() {
        snafuStrings = readIntoStringListUntilEOF(FileSystems.getDefault().getPath(ParserUtils.TEST_RESOURCES, "day25_test.txt"));
    }

    @Test
    void testPart1() {
        ArrayList<Long> snafuIntegers = Day25.convertSnafuListToDecimalList(snafuStrings);
        long snafuSum = snafuIntegers.stream().mapToLong(i -> i).sum();
        // Check that our SNAFU -> Integer parsing is correct
        assertEquals(4890, snafuSum);//snafuSum.intValue());
        // Check that our Integer -> SNAFU parsing is correct
        assertEquals("2=-1=0", Day25.convertDecimalToSnafu(snafuSum));
    }
}