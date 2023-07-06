package advent.of.code.day25;

import java.util.ArrayList;
import java.math.BigInteger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static advent.of.code.parser_utils.ParserUtils.readIntoStringListUntilEOF;

class Day25Test {
    ArrayList<String> snafuStrings;

    @BeforeEach
    void setup() {
        snafuStrings = readIntoStringListUntilEOF(Day25Test.class.getResourceAsStream("/day25_test.txt"));
    }

    @Test
    void testPart1() {
        ArrayList<BigInteger> snafuIntegers = Day25.convertSnafuListToDecimalList(snafuStrings);
        BigInteger snafuSum = BigInteger.ZERO;
        for (BigInteger snafuInteger : snafuIntegers) {
            snafuSum = snafuSum.add(snafuInteger);
        }
        // Check that our SNAFU -> Integer parsing is correct
        assertEquals(4890, snafuSum.intValue());
        // Check that our Integer -> SNAFU parsing is correct
        assertEquals("2=-1=0", Day25.convertDecimalToSnafu(snafuSum));
    }

    // @Test
    // void testPart2() {
    // }
}