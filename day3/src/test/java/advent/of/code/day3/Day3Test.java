package advent.of.code.day3;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static advent.of.code.parser_utils.ParserUtils.readIntoStringListUntilEOF;

public class Day3Test {
    ArrayList<String> rucksacks;

    @BeforeEach
    void setup() {
        rucksacks = readIntoStringListUntilEOF(Day3Test.class.getResourceAsStream("/day3_test.txt"));
    }

    @Test
    void testPart1() {
        int prioritySum = 0;
        for (String rucksack: rucksacks) {
            prioritySum += Day3.getPriority(Day3.getDuplicate(rucksack));
        }
        assertEquals(157, prioritySum);
    }

    @Test
    void testPart2() {
        int prioritySum = 0;
        String sack1 = null, sack2 = null;
        for (int i = 0; i < rucksacks.size(); i++) {
            if (sack1 == null) {
                sack1 = rucksacks.get(i);
            } else if (sack2 == null) {
                sack2 = rucksacks.get(i);
            } else {
                Character badge = Day3.getBadge(sack1, sack2, rucksacks.get(i));
                prioritySum += Day3.getPriority(badge);
                sack1 = null;
                sack2 = null;
            }
        }
        assertEquals(70, prioritySum);
    }
    
}
