package advent.of.code.day11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.math.BigInteger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static advent.of.code.parser_utils.ParserUtils.readIntoStringListUntilEOF;

public class Day11Test {
    ArrayList<String> monkeys;
    MonkeyTroop monkeyTroop;

    @BeforeEach
    public void setup() {
        monkeys = readIntoStringListUntilEOF(Day11Test.class.getResourceAsStream("/day11_test.txt"));
        monkeyTroop = Day11.createMonkeyTroop(monkeys);
    }

    @Test
    public void testPart1() {
        int[] inspections = monkeyTroop.simulateMonkeys(20, 3);
        Arrays.sort(inspections);
        assertEquals(10605, inspections[inspections.length - 2] * inspections[inspections.length - 1]);
    }

    @Test 
    public void testPart2() {
        int[] inspections = monkeyTroop.simulateMonkeys(10000, 1);
        Arrays.sort(inspections);
        assertEquals(new BigInteger("2713310158"), BigInteger.valueOf(inspections[inspections.length - 2]).multiply(BigInteger.valueOf(inspections[inspections.length - 1])));
    }
}
