package advent.of.code.day21;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashMap;
import java.math.BigInteger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static advent.of.code.parser_utils.ParserUtils.readIntoStringListUntilEOF;

class Day21Test {
    LinkedList<YellerMonkey> monkeyYells;

    @BeforeEach
    void setup() {
        ArrayList<String> monkeyStrings = readIntoStringListUntilEOF(Day21Test.class.getResourceAsStream("/day21_test.txt"));
        monkeyYells = Day21.convertToMonkeyYells(monkeyStrings);
    }

    @Test
    void testPart1() {
        HashMap<String, BigInteger> monkeyMap = Day21.createMonkeyHashMap(monkeyYells);
        assertEquals(152, monkeyMap.get("root").intValue());
    }
}
