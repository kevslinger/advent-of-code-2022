package advent.of.code.day21;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashMap;
import java.math.BigInteger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

import advent.of.code.parser_utils.ParserUtils;
import static advent.of.code.parser_utils.ParserUtils.readIntoStringListUntilEOF;

class Day21Test {
    LinkedList<YellerMonkey> monkeyYells;

    @BeforeEach
    void setup() {
        ArrayList<String> monkeyStrings = readIntoStringListUntilEOF(FileSystems.getDefault().getPath(ParserUtils.TEST_RESOURCES, "day21_test.txt"));
        monkeyYells = Day21.convertToMonkeyYells(monkeyStrings);
    }

    @Test
    void testPart1() {
        HashMap<String, BigInteger> monkeyMap = Day21.createMonkeyHashMap(monkeyYells);
        assertEquals(152, monkeyMap.get("root").intValue());
    }

    @Test
    void testPart2() {
        HashMap<String, BigInteger> monkeyResultMap = Day21.createMonkeyHashMapPart2(monkeyYells);
        assertEquals(301, monkeyResultMap.get(Day21.OURNAME).intValue());
    }
}
